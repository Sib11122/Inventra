package com.Inventra.Inventory_service.repository;

import com.Inventra.Inventory_service.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
}
