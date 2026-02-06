package com.sehaj.Inventra_CRUD.category;

import com.sehaj.Inventra_CRUD.category.dto.CategoryCreateRequest;
import com.sehaj.Inventra_CRUD.category.dto.CategoryUpdateRequest;
import com.sehaj.Inventra_CRUD.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok("categories fetched", categoryService.list());
    }

    @GetMapping("/{id}")
    public ApiResponse<?> get(@PathVariable Long id) {
        return ApiResponse.ok("category fetched", categoryService.get(id));
    }

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody CategoryCreateRequest req) {
        return ApiResponse.ok("category created", categoryService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody CategoryUpdateRequest req) {
        return ApiResponse.ok("category updated", categoryService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.ok("category deleted", null);
    }
}
