package com.sehaj.Inventra_CRUD.product.dto;


import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        String description,
        Long brandId,
        String brandName,
        Long categoryId,
        String categoryName,
        BigDecimal basePrice,
        String status
) {}
