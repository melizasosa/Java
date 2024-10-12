package OrderService.service.impl;

import OrderService.DTO.Product;
import OrderService.DTO.ReduceStockRequest;
import OrderService.client.InventoryClient;
import OrderService.entity.OrderItem;
import OrderService.entity.Orders;
import OrderService.redis.RedisService;
import OrderService.repository.OrderItemRepository;
import OrderService.repository.OrdersRepository;
import OrderService.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import OrderService.util.Util;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    private final RedisService redisService;
    @Autowired
    private InventoryClient inventoryClient;
    public Page<Orders> getAllOrders(Pageable pageable) {
        String redisKey = "orders_page_" + pageable.getPageNumber() + "_" + pageable.getPageSize();
        String redisInfo = redisService.getDataDesdeRedis(redisKey);

        if (Objects.nonNull(redisInfo)) {
            // Convertimos la información obtenida desde Redis a un objeto Page<Orders>
            return Util.convertirDesdeString(redisInfo, Page.class);
        } else {
            // Si no está en Redis, buscamos en la base de datos
            Page<Orders> ordersPage = orderRepository.findAll(pageable);

            // Convertimos la página de órdenes a String para almacenarla en Redis
            String dataForRedis = Util.convertirAString(ordersPage);
            redisService.guardarEnRedis(redisKey, dataForRedis, 1);

            return ordersPage; // Retornamos el objeto Page<Orders>
        }
    }

    @Override
    public Orders getOrderById(Long id) {
        String redisKey = "orders_id_" + id;
        String redisInfo = redisService.getDataDesdeRedis(redisKey);
        if(Objects.nonNull(redisInfo)){
            return Util.convertirDesdeString(redisInfo, Orders.class);
        } else {
            // Si no está en Redis, buscamos en la base de datos
            Orders order = orderRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("No se encontró un pedido con ID: " + id));

            // Convertimos el pedido a String para almacenarlo en Redis
            String dataForRedis = Util.convertirAString(order);
            redisService.guardarEnRedis(redisKey, dataForRedis, 1);

            return order;
        }
    }

    @Override
    @Transactional
    public Orders createOrder(Orders order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La orden debe contener al menos un ítem.");
        }

        Orders savedOrder = orderRepository.save(order);

        for (OrderItem item : order.getItems()) {
            Product product = inventoryClient.getProductById(item.getProductId());
            if (product == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + item.getProductId());
            }

            if (item.getQuantity() > product.getStock()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Stock insuficiente para el producto: " + product.getName());
            }

            ReduceStockRequest reduceStockRequest = new ReduceStockRequest(item.getProductId(), item.getQuantity());
            inventoryClient.reduceStock(item.getProductId(), reduceStockRequest);

            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return savedOrder;
    }

    @Override
    public Optional<Orders> updateOrderStatus(Long orderId, String status) {
        if (!validStatuses.contains(status)) {
            throw new IllegalArgumentException("Estado inválido. Los estados válidos son: " + validStatuses);
        }

        Optional<Orders> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();
            order.setStatus(status);
            orderRepository.save(order);
            return Optional.of(order);
        }

        return Optional.empty();
    }

    private static final List<String> validStatuses = Arrays.asList("Pending", "Processed");

}
