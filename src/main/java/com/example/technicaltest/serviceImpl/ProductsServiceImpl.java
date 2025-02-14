package com.example.technicaltest.serviceImpl;

import com.example.technicaltest.domain.entity.Product;
import com.example.technicaltest.domain.ProductWithDiscount;
import com.example.technicaltest.repository.ProductsRepository;
import com.example.technicaltest.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<ProductWithDiscount> getAllProducts(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Product> productList = productsRepository.findAll(pageable).getContent();

        return getProductWithDiscountList(productList);
    }

    @Override
    public List<ProductWithDiscount> getFilteredProducts(Sort sort, String filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Product> productList = productsRepository.findByCategoryIgnoreCase(filter, pageable);

        return getProductWithDiscountList(productList);
    }

    /**
     * Add the discounts to every product
     * @param productList list of products
     * @return list of products with discounts
     */
    private static List<ProductWithDiscount> getProductWithDiscountList(List<Product> productList) {
        List<ProductWithDiscount> productWithDiscountList = new ArrayList<>();

        // For each product add the discount according to the business rules
        productList.forEach(product -> {
            ProductWithDiscount productWithDiscount = new ProductWithDiscount(product);
            if (product.getSku().endsWith("5")) {
                productWithDiscount.setDiscount("30%");
                // Round the discount price to 2 decimals, round up
                productWithDiscount.setPriceWithDiscount(
                        BigDecimal.valueOf(product.getPrice() - (product.getPrice() * 0.3))
                                .setScale(2, RoundingMode.HALF_UP).doubleValue()
                        );

            } else if (product.getCategory().equalsIgnoreCase("Home & Kitchen")) {
                productWithDiscount.setDiscount("25%");
                // Round the discount price to 2 decimals, round up
                productWithDiscount.setPriceWithDiscount(
                        BigDecimal.valueOf(product.getPrice() - (product.getPrice() * 0.25))
                                .setScale(2, RoundingMode.HALF_UP).doubleValue()
                );

            } else if (product.getCategory().equalsIgnoreCase("Electronics")) {
                productWithDiscount.setDiscount("15%");
                // Round the discount price to 2 decimals, round up
                productWithDiscount.setPriceWithDiscount(
                        BigDecimal.valueOf(product.getPrice() - (product.getPrice() * 0.15))
                                .setScale(2, RoundingMode.HALF_UP).doubleValue()
                );
            }

            productWithDiscountList.add(productWithDiscount);
        });
        return productWithDiscountList;
    }
}
