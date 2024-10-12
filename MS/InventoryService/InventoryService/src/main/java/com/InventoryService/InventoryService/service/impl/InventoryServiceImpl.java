package com.InventoryService.InventoryService.service.impl;

import com.InventoryService.InventoryService.entity.Product;
import com.InventoryService.InventoryService.exception.ResourceNotFoundException;
import com.InventoryService.InventoryService.redis.RedisService;
import com.InventoryService.InventoryService.repository.ProductRepository;
import com.InventoryService.InventoryService.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private ProductRepository productRepository;
    private final RedisService redisService;

    // Consultar stock de un producto
    public int checkStock(Long productId) {
        String redisKey = "inventory_id_" + productId;
        String redisInfo = redisService.getDataDesdeRedis(redisKey);

        if (redisInfo != null) {
            // Si el stock está en caché, devolver el valor almacenado en Redis
            return Integer.parseInt(redisInfo);
        }
        // Si no está en Redis, consultar la base de datos
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        int stock = product.getStock();
        // Almacenar el stock en Redis para futuras consultas
        redisService.guardarEnRedis(redisKey, String.valueOf(stock), 1);
        return stock;
    }

    // Reducir stock de un producto
    public void reduceStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product ID: " + productId);
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null); // Retorna el producto si está presente, o null
    }
}
