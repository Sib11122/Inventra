package com.Inventra.Inventory_service.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockResponse {
    private Long productId;
    private Integer quantity;
    private Integer reorderLevel;
    private boolean lowStock;
}
