package com.InventoryService.InventoryService.controller;

import com.InventoryService.InventoryService.DTO.ReduceStockRequest;
import com.InventoryService.InventoryService.entity.Product;
import com.InventoryService.InventoryService.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    // Consultar stock de un producto
    @GetMapping("/{id}")
    public ResponseEntity<Integer> getStock(@PathVariable Long id) {
        int stock = inventoryService.checkStock(id);
        return ResponseEntity.ok(stock);
    }

    // Reducir stock de un producto
    @PostMapping("/{id}/reduce")
    public ResponseEntity<Void> reduceStock(@PathVariable Long id, @RequestBody ReduceStockRequest request) {
        inventoryService.reduceStock(id, request.getQuantity());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        Product newProduct = inventoryService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = inventoryService.findById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
}
