package com.sehaj.Inventra_CRUD.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank String name,
        @NotBlank String sku,
        String description,
        Long brandId,
        @NotNull Long categoryId,
        BigDecimal basePrice,
        String status
) {}
