package com.example.technicaltest.repository;

import com.example.technicaltest.domain.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Product, String> {
    /**
     * Find by category with pagination
     * @param category category to filter
     * @param pageable pagination
     * @return list of products filtered by category
     */
    List<Product> findByCategoryIgnoreCase(String category, Pageable pageable);

}
