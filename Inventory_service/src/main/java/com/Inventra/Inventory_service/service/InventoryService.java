package com.Inventra.Inventory_service.service;

import com.Inventra.Inventory_service.dto.StockAdjustRequest;
import com.Inventra.Inventory_service.dto.StockResponse;
import com.Inventra.Inventory_service.dto.StockUpdateRequest;
import com.Inventra.Inventory_service.entity.Stock;
import com.Inventra.Inventory_service.entity.StockTransaction;
import com.Inventra.Inventory_service.event.LowStockEvent;
import com.Inventra.Inventory_service.exception.BadRequestException;
import com.Inventra.Inventory_service.exception.ConflictException;
import com.Inventra.Inventory_service.exception.ResourceNotFoundException;
import com.Inventra.Inventory_service.repository.StockRepository;
import com.Inventra.Inventory_service.repository.StockTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StockRepository stockRepo;
    private final StockTransactionRepository txRepo;
    private final InventoryEventPublisher eventPublisher;

    public StockResponse getByProductId(Long productId) {
        if (productId == null) throw new BadRequestException("productId must not be null");

        Stock stock = (Stock) stockRepo.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Stock not found for productId: " + productId
                ));

        return toResponse(stock);
    }

    @Transactional
    public StockResponse upsert(StockUpdateRequest req) {
        if (req == null) throw new BadRequestException("Request body missing");
        if (req.getProductId() == null) throw new BadRequestException("productId must not be null");
        if (req.getQuantity() < 0) throw new BadRequestException("quantity must be >= 0");
        if (req.getReorderLevel() < 0) throw new BadRequestException("reorderLevel must be >= 0");

        Stock stock = (Stock) stockRepo.findByProductId(req.getProductId())
                .orElse(Stock.builder()
                        .productId(req.getProductId())
                        .quantity(0)
                        .reorderLevel(0)
                        .build());

        stock.setQuantity(req.getQuantity());
        stock.setReorderLevel(req.getReorderLevel());

        Stock saved = stockRepo.save(stock);
        publishLowStockIfNeeded(saved);

        return toResponse(saved);
    }

    @Transactional
    public StockResponse adjust(StockAdjustRequest req) throws ConflictException {
        if (req == null) throw new BadRequestException("Request body missing");
        if (req.getProductId() == null) throw new BadRequestException("productId must not be null");
        if (req.getDelta() == 0) throw new BadRequestException("delta must not be 0");

        Stock stock = (Stock) stockRepo.findByProductId(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Stock not found for productId: " + req.getProductId()
                ));

        int before = stock.getQuantity();
        int after = before + req.getDelta();

        if (after < 0) {
            throw new ConflictException("Insufficient stock. Current=" + before + ", delta=" + req.getDelta());
        }

        stock.setQuantity(after);
        Stock saved = stockRepo.save(stock);

        txRepo.save(StockTransaction.builder()
                .productId(saved.getProductId())
                .delta(req.getDelta())
                .beforeQty(before)
                .afterQty(after)
                .reason(req.getReason())
                .build());

        publishLowStockIfNeeded(saved);

        return toResponse(saved);
    }

    private void publishLowStockIfNeeded(Stock saved) {
        if (saved.getQuantity() <= saved.getReorderLevel()) {
            eventPublisher.publishLowStock(new LowStockEvent(
                    saved.getProductId(), saved.getQuantity(), saved.getReorderLevel()
            ));
        }
    }

    private StockResponse toResponse(Stock s) {
        return StockResponse.builder()
                .productId(s.getProductId())
                .quantity(s.getQuantity())
                .reorderLevel(s.getReorderLevel())
                .lowStock(s.getQuantity() <= s.getReorderLevel())
                .build();
    }
}
