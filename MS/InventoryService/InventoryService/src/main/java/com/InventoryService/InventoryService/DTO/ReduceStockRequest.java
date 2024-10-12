package com.InventoryService.InventoryService.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReduceStockRequest {
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima a reducir es 1")
    private Integer quantity;

    // Constructor vacío (necesario para deserialización)
    public ReduceStockRequest() {}

    // Constructor con parámetros
    public ReduceStockRequest(Integer quantity) {
        this.quantity = quantity;
    }

    // Getter
    public Integer getQuantity() {
        return quantity;
    }

    // Setter
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
