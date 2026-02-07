package com.Inventra.Inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockAdjustRequest {
    @NotNull
    private Long productId;

    @NotNull
    private Integer delta; // + / -

    @NotBlank
    private String reason;
}

