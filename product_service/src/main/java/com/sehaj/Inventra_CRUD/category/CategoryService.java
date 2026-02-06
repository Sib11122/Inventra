package com.sehaj.Inventra_CRUD.category;

import com.sehaj.Inventra_CRUD.category.dto.CategoryCreateRequest;
import com.sehaj.Inventra_CRUD.category.dto.CategoryResponse;
import com.sehaj.Inventra_CRUD.category.dto.CategoryUpdateRequest;
import com.sehaj.Inventra_CRUD.common.exception.BadRequestException;
import com.sehaj.Inventra_CRUD.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> list() {
        return categoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoryResponse get(Long id) {
        return toResponse(findCategory(id));
    }

    @Transactional
    public CategoryResponse create(CategoryCreateRequest req) {
        Category parent = (req.parentId() == null) ? null : findCategory(req.parentId());

        if (req.parentId() != null && categoryRepository.existsByNameAndParent_Id(req.name(), req.parentId())) {
            throw new BadRequestException("Category with same name already exists under this parent.");
        }

        Category saved = categoryRepository.save(Category.builder()
                .name(req.name().trim())
                .parent(parent)
                .description(req.description())
                .build());

        return toResponse(saved);
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryUpdateRequest req) {
        Category c = findCategory(id);

        if (req.name() != null && !req.name().isBlank()) c.setName(req.name().trim());
        if (req.description() != null) c.setDescription(req.description());

        if (req.parentId() != null) {
            if (req.parentId().equals(id)) throw new BadRequestException("A category cannot be its own parent.");
            c.setParent(findCategory(req.parentId()));
        }

        return toResponse(c);
    }

    @Transactional
    public void delete(Long id) {
        Category c = findCategory(id);
        categoryRepository.delete(c);
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found: " + id));
    }

    private CategoryResponse toResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName(),
                c.getParent() == null ? null : c.getParent().getId(),
                c.getDescription());
    }
}
