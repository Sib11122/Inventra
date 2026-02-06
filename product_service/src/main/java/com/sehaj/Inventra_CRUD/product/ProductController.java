package com.sehaj.Inventra_CRUD.product;

import com.sehaj.Inventra_CRUD.common.ApiResponse;
import com.sehaj.Inventra_CRUD.product.dto.ProductCreateRequest;
import com.sehaj.Inventra_CRUD.product.dto.ProductUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok("products fetched", productService.list());
    }

    @GetMapping("/{id}")
    public ApiResponse<?> get(@PathVariable("id") Long id) {
        return ApiResponse.ok("product fetched", productService.get(id));
    }

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody ProductCreateRequest req) {
        return ApiResponse.ok("product created", productService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductUpdateRequest req
    ) {
        return ApiResponse.ok("product updated", productService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable("id") Long id) {
        productService.delete(id);
        return ApiResponse.ok("product deleted", null);
    }
}
