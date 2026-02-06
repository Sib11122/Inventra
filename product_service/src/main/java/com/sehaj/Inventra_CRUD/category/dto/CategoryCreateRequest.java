package com.sehaj.Inventra_CRUD.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank String name,
        Long parentId,
        String description
) {}
