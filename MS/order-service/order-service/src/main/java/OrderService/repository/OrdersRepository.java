package OrderService.repository;

import OrderService.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    //List<Orders> findAllByIsEnabledTrue();
    Optional<Orders> findById(Long id);
}
