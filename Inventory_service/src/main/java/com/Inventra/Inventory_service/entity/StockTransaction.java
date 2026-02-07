package com.Inventra.Inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="stock_transactions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_id", nullable=false)
    private Long productId;

    @Column(nullable=false)
    private Integer delta; // +10, -5

    @Column(nullable=false)
    private Integer beforeQty;

    @Column(nullable=false)
    private Integer afterQty;

    @Column(nullable=false)
    private String reason; // SALE, PURCHASE, MANUAL_ADJUST, ORDER_CANCEL etc.

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
