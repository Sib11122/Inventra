package com.Inventra.Inventory_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LowStockEvent {
    private Long productId;
    private Integer currentQty;
    private Integer reorderLevel;
}
