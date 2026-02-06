package com.sehaj.Inventra_CRUD.brand.dto;


import jakarta.validation.constraints.NotBlank;

public record BrandCreateRequest(
        @NotBlank String name,
        String description
) {}
