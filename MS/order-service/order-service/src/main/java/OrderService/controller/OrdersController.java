package OrderService.controller;

import OrderService.entity.Orders;
import OrderService.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private final OrderServiceImpl orderServiceImpl;

    // Endpoint para obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = orderServiceImpl.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Listar pedidos (con paginación)
    @GetMapping
    public Page<Orders> listOrders(Pageable pageable) {
        return orderServiceImpl.getAllOrders(pageable);
    }

    // Crear pedido
    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
        Orders createdOrder = orderServiceImpl.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // Actualizar estado del pedido a "Procesado"
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Optional<Orders> updatedOrder = orderServiceImpl.updateOrderStatus(id, status);

            // Si el pedido fue encontrado y actualizado, devolver 200 OK
            if (updatedOrder.isPresent()) {
                return new ResponseEntity<>(updatedOrder.get(), HttpStatus.OK);
            } else {
                // Si el pedido no fue encontrado, devolver 404 Not Found
                return new ResponseEntity<>("Pedido no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            // Manejar el caso de estado inválido (devuelve 400 Bad Request)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
