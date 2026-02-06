package com.sehaj.Inventra_CRUD.product.dto;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        String name,
        String description,
        Long brandId,
        Long categoryId,
        BigDecimal basePrice,
        String status
) {}
