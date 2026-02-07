package com.Inventra.Inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockUpdateRequest {
    @NotNull
    private Long productId;

    @NotNull @Min(0)
    private Integer quantity;

    @NotNull @Min(0)
    private Integer reorderLevel;
}

