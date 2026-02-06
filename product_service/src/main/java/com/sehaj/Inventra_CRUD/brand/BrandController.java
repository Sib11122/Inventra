package com.sehaj.Inventra_CRUD.brand;

import com.sehaj.Inventra_CRUD.brand.dto.BrandCreateRequest;
import com.sehaj.Inventra_CRUD.brand.dto.BrandUpdateRequest;
import com.sehaj.Inventra_CRUD.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok("brands fetched", brandService.list());
    }

    @GetMapping("/{id}")
    public ApiResponse<?> get(@PathVariable Long id) {
        return ApiResponse.ok("brand fetched", brandService.get(id));
    }

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody BrandCreateRequest req) {
        return ApiResponse.ok("brand created", brandService.create(req));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable("id") Long id,
                                 @Valid @RequestBody BrandUpdateRequest req) {
        return ApiResponse.ok("brand updated", brandService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable("id") Long id) {
        brandService.delete(id);
        return ApiResponse.ok("brand deleted", null);
    }
}
