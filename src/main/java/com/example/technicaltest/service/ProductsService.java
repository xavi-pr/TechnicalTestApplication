package com.example.technicaltest.service;

import com.example.technicaltest.domain.ProductWithDiscount;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductsService {

    /**
     * Get all products
     * @param pageNo   page number
     * @param pageSize page size
     * @param sort sorting fields
     * @return list of products
     */
    List<ProductWithDiscount> getAllProducts(int pageNo, int pageSize, Sort sort);

    /**
     * Get products filtered
     * @param sort sorting fields
     * @param filter filtering field
     * @param pageNo page number
     * @param pageSize page size
     * @return list of products filtered and can be sorted
     */
    List<ProductWithDiscount> getFilteredProducts(Sort sort, String filter, int pageNo, int pageSize);
}
