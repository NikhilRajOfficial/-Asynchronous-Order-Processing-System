package com.javanik.orderservice.controller;


import com.javanik.orderservice.entity.OrderEntity;
import com.javanik.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<OrderEntity> createOrder(@RequestParam String customerId, @RequestParam double totalAmount, @RequestParam String productId, @RequestParam int quantity) {
        logger.info("Received create order request for customer: {}", customerId);
        try {
            OrderEntity order = orderService.createOrder(customerId, totalAmount, productId, quantity);
            logger.info("Order created successfully with ID: {}", order.getId());
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.warn("Insufficient inventory requested");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Insufficient Inventory
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable UUID orderId) {
        logger.info("Received request to get order with ID: {}", orderId);
        OrderEntity order = orderService.getOrder(orderId);
        if (order != null) {
            logger.info("Order found, returning: {}", order);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            logger.warn("Order not found with ID: {}", orderId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{orderId}/status")  // Added PUT endpoint
    public ResponseEntity<String> updateOrderStatus(@PathVariable UUID orderId, @RequestBody String newStatus) {
        logger.info("Received request to update order status for orderId: {}, newStatus: {}", orderId, newStatus);
        try {
            orderService.updateOrderStatus(orderId, newStatus);
            logger.info("Order status updated successfully");
            return new ResponseEntity<>("Order status updated to " + newStatus, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error updating order status", e);
            return new ResponseEntity<>("Error updating order status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
