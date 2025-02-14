package com.javanik.inventoryservice.repo;

import com.javanik.inventoryservice.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepo extends JpaRepository<InventoryItem, Long> {
    InventoryItem findByProduct(String product);
}
