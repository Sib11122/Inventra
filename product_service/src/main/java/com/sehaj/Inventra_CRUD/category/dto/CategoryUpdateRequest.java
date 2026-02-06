package com.sehaj.Inventra_CRUD.category.dto;


public record CategoryUpdateRequest(
        String name,
        Long parentId,
        String description
) {}
