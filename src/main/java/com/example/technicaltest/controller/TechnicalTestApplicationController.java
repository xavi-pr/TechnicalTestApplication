package com.example.technicaltest.controller;

import com.example.technicaltest.domain.OrderPair;
import com.example.technicaltest.domain.ProductWithDiscount;
import com.example.technicaltest.service.ProductsService;
import com.example.technicaltest.utils.ProductsHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TechnicalTestApplicationController {

    @Autowired
    ProductsService productsService;
    @Autowired
    ProductsHelper productsHelper;

    @PostMapping("/productsList")
    public ResponseEntity<List<ProductWithDiscount>> productsList(@RequestBody(required = false) List<OrderPair> orderBy,
                                                                  @RequestParam(required = false) String filterBy,
                                                                  @RequestParam(defaultValue = "0") int pageNo,
                                                                  @RequestParam(defaultValue = "10") int pageSize) {

        // Perform validations to ensure we can do the search without problems
        List<Sort.Order> orders = productsHelper.getOrders(orderBy);

        if (StringUtils.isNotBlank(filterBy)) {
            // Get products with filter, and maybe sorted
            return ResponseEntity.ok(productsService.getFilteredProducts(Sort.by(orders),
                    StringUtils.isNotBlank(filterBy) ? filterBy : "", pageNo, pageSize));
        } else {
            // Get products without filter, and maybe sorted
            return ResponseEntity.ok(productsService.getAllProducts(pageNo, pageSize, Sort.by(orders)));
        }

    }



}
