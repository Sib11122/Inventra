package com.sehaj.Inventra_CRUD.brand;

import com.sehaj.Inventra_CRUD.brand.dto.BrandCreateRequest;
import com.sehaj.Inventra_CRUD.brand.dto.BrandResponse;
import com.sehaj.Inventra_CRUD.brand.dto.BrandUpdateRequest;
import com.sehaj.Inventra_CRUD.common.exception.BadRequestException;
import com.sehaj.Inventra_CRUD.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<BrandResponse> list() {
        return brandRepository.findAll().stream().map(this::toResponse).toList();
    }

    public BrandResponse get(Long id) {
        return toResponse(findBrand(id));
    }

    @Transactional
    public BrandResponse create(BrandCreateRequest req) {
        if (brandRepository.existsByNameIgnoreCase(req.name().trim())) {
            throw new BadRequestException("Brand already exists.");
        }
        Brand saved = brandRepository.save(Brand.builder()
                .name(req.name().trim())
                .description(req.description())
                .build());
        return toResponse(saved);
    }

    private Brand findBrand(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand not found: " + id));
    }

    private BrandResponse toResponse(Brand b) {
        return new BrandResponse(b.getId(), b.getName(), b.getDescription());
    }
    public Object update(Long id, BrandUpdateRequest req) {
        var brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brand.setName(req.name());
        brand.setDescription(req.description());

        return brandRepository.save(brand);
    }

    public void delete(Long id) {
        var brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brandRepository.delete(brand);
    }
}
