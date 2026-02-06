package com.sehaj.Inventra_CRUD.product;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        select p from Product p
        left join fetch p.brand b
        join fetch p.category c
    """)
    List<Product> findAllWithBrandAndCategory();

    @Query("""
        select p from Product p
        left join fetch p.brand b
        join fetch p.category c
        where p.id = :id
    """)
    Optional<Product> findByIdWithBrandAndCategory(@Param("id") Long id);

    boolean existsBySkuIgnoreCase(String trim);
}

