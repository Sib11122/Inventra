package com.Inventra.Inventory_service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LowStockListener {

    @EventListener
    public void onLowStock(LowStockEvent e) {
        // Later: call notification-service via RestClient/WebClient
        log.warn("LOW STOCK ALERT -> productId={}, qty={}, reorderLevel={}",
                e.getProductId(), e.getCurrentQty(), e.getReorderLevel());
    }
}
