package com.javanik.orderservice.service;


import com.javanik.orderservice.dto.InventoryItem;
import com.javanik.orderservice.entity.OrderEntity;
import com.javanik.orderservice.repo.OrderRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;



@Service
public class OrderService {

      private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

      @Autowired
      private OrderRepo orderRepository;

      @Autowired
      private KafkaTemplate<String, String> kafkaTemplate;

      @Value("${kafka.topic.order}")
      private String orderTopic;

      @Value("${inventory.service.url}")
      private String inventoryServiceUrl;

      @Autowired
      private RestTemplate restTemplate;

      @Cacheable(value = "orders", key = "#customerId")
      public OrderEntity createOrder(String customerId, double totalAmount, String productId, int quantity) {
            logger.info("Creating order for customer: {}", customerId);

            // 1. Check Inventory
            boolean hasSufficientInventory = checkInventory(productId, quantity);
            if (!hasSufficientInventory) {
                  logger.warn("Insufficient inventory for product: {}", productId);
                  throw new IllegalStateException("Insufficient inventory for product: " + productId);
            }

            OrderEntity order = new OrderEntity();
            order.setCustomerId(customerId);
            order.setTotalAmount(totalAmount);
            order.setStatus("CREATED");
            order.setProductId(productId);
            order.setQuantity(quantity);
            order = orderRepository.save(order);
            logger.info("Order created with ID: {}", order.getId());

            // 2. Update Inventory (Assuming order creation is successful)
            updateInventory(productId, quantity * -1); // Subtract quantity

            // Publish order creation event to Kafka
            publishOrderEvent(order.getId(), "Order Created",productId,quantity);

            return order;
      }

      private boolean checkInventory(String productId, int quantity) {
            logger.info("Checking inventory for product: {}", productId);
            // Call Inventory Service to check stock
            String inventoryCheckUrl = inventoryServiceUrl + "/api/inventory/" + productId;
            InventoryItem inventoryItem = restTemplate.getForObject(inventoryCheckUrl, InventoryItem.class);

            boolean hasStock = inventoryItem != null && inventoryItem.getQuantity() >= quantity;
            logger.info("Inventory check result: {}", hasStock);
            return hasStock;
      }

      private void updateInventory(String productId, int quantityChange) {
            logger.info("Updating inventory for product: {}, quantityChange: {}", productId, quantityChange);
            // Call Inventory Service to update stock
            String inventoryUpdateUrl = inventoryServiceUrl + "/api/inventory/" + productId + "/quantity?quantityChange=" + quantityChange;
            restTemplate.put(inventoryUpdateUrl, null);  // Use PUT to update
            logger.info("Inventory updated successfully");
      }

      private void publishOrderEvent(UUID orderId, String eventType,String productId, int quantity) {
            logger.info("Publishing order event to Kafka, orderId: {}", orderId);
            kafkaTemplate.send(orderTopic, orderId.toString(), eventType+":"+productId+":"+quantity);
            logger.info("Order event published successfully");
      }

      @CircuitBreaker(name = "orderServiceCircuitBreaker", fallbackMethod = "fallbackAfterFailure")
      public OrderEntity getOrder(UUID orderId) {
            logger.info("Getting order with ID: {}", orderId);
            // Simulate a potential failure (e.g., database connection issue)
            if (Math.random() < 0.3) {
                  logger.error("Simulated failure occurred");
                  throw new RuntimeException("Simulated failure");
            }
            OrderEntity order = orderRepository.findById(orderId).orElse(null);
            logger.info("Order retrieved successfully: {}", order);
            return order;
      }

      private OrderEntity fallbackAfterFailure(UUID orderId, Throwable t) {
            logger.warn("Circuit Breaker triggered for orderId: {}, returning default order", orderId);
            // Provide a fallback order or return null
            OrderEntity defaultOrder = new OrderEntity();
            defaultOrder.setCustomerId("default_customer");
            defaultOrder.setTotalAmount(0.0);
            defaultOrder.setStatus("FAILED");
            logger.info("Returning default order");
            return defaultOrder; // Or return null, depending on your logic
      }

      public void updateOrderStatus(UUID orderId, String newStatus) {
            logger.info("Updating order status for orderId: {}, newStatus: {}", orderId, newStatus);
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

            order.setStatus(newStatus);
            orderRepository.save(order);
            logger.info("Order status updated successfully");
      }
}