package com.javanik.inventoryservice.controller;

import com.javanik.inventoryservice.entity.InventoryItem;
import com.javanik.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/allStock")
    public List<InventoryItem> getAllItems() {
        logger.info("Received request to get all inventory items");
        return inventoryService.getAllItems();
    }

    @GetMapping("/{product}")
    public InventoryItem getItemByProduct(@PathVariable String product) {
        logger.info("Received request to get inventory item by product name: {}", product);
        return inventoryService.getItemByProduct(product);
    }

    @PostMapping("/stock")
    public InventoryItem createItem(@RequestBody InventoryItem item) {
        logger.info("Received request to create inventory item: {}", item);
        return inventoryService.createItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        logger.info("Received request to delete inventory item with ID: {}", id);
        inventoryService.deleteItem(id);
    }

    @PutMapping("/{product}/quantity")
    public void updateQuantity(@PathVariable String product, @RequestParam int quantityChange) {
        logger.info("Received request to update quantity for product name: {}, quantityChange: {}", product, quantityChange);
        inventoryService.updateQuantity(product, quantityChange);
    }
}
