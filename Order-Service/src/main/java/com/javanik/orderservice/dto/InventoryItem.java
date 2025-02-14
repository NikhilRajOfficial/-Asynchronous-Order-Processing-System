package com.javanik.orderservice.dto;

import lombok.Data;

@Data
public class InventoryItem {
    private Long id;
    private String product;
    private int quantity;
    private String productId;
}
