package OrderService.client;

import OrderService.DTO.Product;
import OrderService.DTO.ReduceStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-inventory") // Reemplaza con la URL de tu Inventory Service
public interface InventoryClient {
    @PostMapping("/inventory/{id}/reduce")
    void reduceStock(@PathVariable("id") Long id, @RequestBody ReduceStockRequest request);
    @GetMapping("/inventory/product/{id}")
    Product getProductById(@PathVariable("id") Long productId);

}
