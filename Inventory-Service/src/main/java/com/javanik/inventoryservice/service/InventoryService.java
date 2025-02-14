package com.javanik.inventoryservice.service;

import com.javanik.inventoryservice.entity.InventoryItem;
import com.javanik.inventoryservice.repo.InventoryRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryRepo inventoryRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // For publishing inventory updates to Kafka

    @Cacheable(value = "inventoryItems")
    public List<InventoryItem> getAllItems() {
        logger.info("Getting all inventory items");
        return inventoryRepository.findAll();
    }

    @Cacheable(value = "inventoryItems", key = "#product")
    public InventoryItem getItemByProduct(String product) {
        logger.info("Getting inventory item by product name: {}", product);
        return inventoryRepository.findByProduct(product);
    }

    public InventoryItem createItem(InventoryItem item) {
        logger.info("Creating inventory item: {}", item);
        return inventoryRepository.save(item);
    }

    @CacheEvict(value = "inventoryItems", key = "#id")
    public void deleteItem(Long id) {
        logger.info("Deleting inventory item with ID: {}", id);
        inventoryRepository.deleteById(id);
    }

    @CircuitBreaker(name = "inventoryServiceCircuitBreaker", fallbackMethod = "fallbackUpdateQuantity")
    public void updateQuantity(String product, int quantityChange) {
        logger.info("Updating quantity for product: {}, quantityChange: {}", product, quantityChange);
        InventoryItem item = inventoryRepository.findByProduct(product);

        if (item == null) {
            logger.warn("Item not found with product name: {}", product);
            throw new IllegalArgumentException("Item not found with product name: " + product);
        }

        item.setQuantity(item.getQuantity() + quantityChange);
        inventoryRepository.save(item);

        // Publish an event to Kafka about the inventory update.
        kafkaTemplate.send("inventory-updates", product + ": " + quantityChange);

        logger.info("Quantity updated successfully for product: {}", product);
    }

    public void fallbackUpdateQuantity(String product, int quantityChange, Throwable t) {
        logger.error("Failed to update quantity for product {} due to error: {}", product, t.getMessage());
    }
}
