package com.sehaj.Inventra_CRUD.category.dto;


public record CategoryResponse(
        Long id,
        String name,
        Long parentId,
        String description
) {}
