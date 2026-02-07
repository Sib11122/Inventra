package com.Inventra.Inventory_service.controller;

import com.Inventra.Inventory_service.common.ApiResponse;
import com.Inventra.Inventory_service.dto.StockAdjustRequest;
import com.Inventra.Inventory_service.dto.StockUpdateRequest;
import com.Inventra.Inventory_service.exception.ConflictException;
import com.Inventra.Inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Check Current Stock
    @GetMapping("/stock/{productId}")
    public ApiResponse<?> get(@PathVariable("productId") Long productId) {
        return ApiResponse.ok("stock fetched", inventoryService.getByProductId(productId));
    }

    // Create/Update stock record (admin setup)
    @PostMapping("/stock")
    public ApiResponse<?> upsert(@Valid @RequestBody StockUpdateRequest req) {
        return ApiResponse.ok("stock upserted", inventoryService.upsert(req));
    }

    // Update stock levels (sale, purchase, manual)
    @PostMapping("/stock/adjust")
    public ApiResponse<?> adjust(@Valid @RequestBody StockAdjustRequest req) throws ConflictException {
        return ApiResponse.ok("stock adjusted", inventoryService.adjust(req));
    }
}
