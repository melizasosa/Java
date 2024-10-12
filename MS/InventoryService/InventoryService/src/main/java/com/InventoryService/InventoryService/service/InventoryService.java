package com.InventoryService.InventoryService.service;

import com.InventoryService.InventoryService.entity.Product;



public interface InventoryService {
    Product findById(Long id);
    int checkStock(Long productId);
    void reduceStock(Long productId, int quantity);
    Product createProduct(Product product);
}
