package OrderService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReduceStockRequest {
    private Long productId;
    private Integer quantity;
}
