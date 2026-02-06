package com.sehaj.Inventra_CRUD.product;

import com.sehaj.Inventra_CRUD.brand.Brand;
import com.sehaj.Inventra_CRUD.brand.BrandRepository;
import com.sehaj.Inventra_CRUD.category.Category;
import com.sehaj.Inventra_CRUD.category.CategoryRepository;
import com.sehaj.Inventra_CRUD.common.exception.BadRequestException;
import com.sehaj.Inventra_CRUD.common.exception.NotFoundException;
import com.sehaj.Inventra_CRUD.product.dto.ProductCreateRequest;
import com.sehaj.Inventra_CRUD.product.dto.ProductResponse;
import com.sehaj.Inventra_CRUD.product.dto.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public List<ProductResponse> list() {
        return productRepository.findAllWithBrandAndCategory()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse get(Long id) {
        Product p = productRepository.findByIdWithBrandAndCategory(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        return toResponse(p);
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest req) {
        if (productRepository.existsBySkuIgnoreCase(req.sku().trim())) {
            throw new BadRequestException("SKU already exists.");
        }

        Category category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + req.categoryId()));

        Brand brand = null;
        if (req.brandId() != null) {
            brand = brandRepository.findById(req.brandId())
                    .orElseThrow(() -> new NotFoundException("Brand not found: " + req.brandId()));
        }

        Product p = Product.builder()
                .name(req.name().trim())
                .sku(req.sku().trim())
                .description(req.description())
                .category(category)
                .brand(brand)
                .basePrice(req.basePrice())
                .status(req.status() == null ? "ACTIVE" : req.status().trim())
                .build();

        return toResponse(productRepository.save(p));
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest req) {
        Product p = findProduct(id);

        if (req.name() != null && !req.name().isBlank()) p.setName(req.name().trim());
        if (req.description() != null) p.setDescription(req.description());
        if (req.basePrice() != null) p.setBasePrice(req.basePrice());
        if (req.status() != null && !req.status().isBlank()) p.setStatus(req.status().trim());

        if (req.categoryId() != null) {
            Category category = categoryRepository.findById(req.categoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found: " + req.categoryId()));
            p.setCategory(category);
        }

        if (req.brandId() != null) {
            Brand brand = brandRepository.findById(req.brandId())
                    .orElseThrow(() -> new NotFoundException("Brand not found: " + req.brandId()));
            p.setBrand(brand);
        }

        return toResponse(p);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(findProduct(id));
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
    }

    private ProductResponse toResponse(Product p) {
        Long brandId = (p.getBrand() == null) ? null : p.getBrand().getId();
        String brandName = (p.getBrand() == null) ? null : p.getBrand().getName();
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getSku(),
                p.getDescription(),
                brandId,
                brandName,
                p.getCategory().getId(),
                p.getCategory().getName(),
                p.getBasePrice(),
                p.getStatus()
        );
    }
}
