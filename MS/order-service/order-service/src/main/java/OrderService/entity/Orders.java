package OrderService.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customers customer;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    private Long productId;
    private int quantity;

    // Relación uno a muchos con OrderItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();

    // Método para agregar ítems al pedido
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;


}
