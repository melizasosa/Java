package OrderService.service;

import OrderService.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Orders getOrderById(Long id);

    Orders createOrder(Orders order);

    Page<Orders> getAllOrders(Pageable pageable);

    Optional<Orders> updateOrderStatus(Long orderId, String status);
}
