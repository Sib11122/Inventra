package com.Inventra.Inventory_service.service;

import com.Inventra.Inventory_service.event.LowStockEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishLowStock(LowStockEvent event) {
        publisher.publishEvent(event);
    }
}

