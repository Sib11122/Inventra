package com.sehaj.Inventra_CRUD.category;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameAndParent_Id(String name, Long parentId);
}
