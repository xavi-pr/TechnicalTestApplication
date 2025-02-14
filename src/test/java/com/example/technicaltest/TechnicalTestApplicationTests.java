package com.example.technicaltest;

import com.example.technicaltest.configuration.DatabaseConfiguration;
import com.example.technicaltest.domain.entity.Product;
import com.example.technicaltest.repository.ProductsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.sql.SQLException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TechnicalTestApplicationTests {

    @Autowired
    private ProductsRepository productsRepository;

    private final Product productSKU1 = new Product("SKU0001", 20.36F, "Wireless Keyboard", "Electronics");
    private final Product productSKU2 = new Product("SKU0002", 15.36F, "Sport socks", "Footwear");
    private final Product productSKU3 = new Product("SKU0003", 230.36F, "BBQ set for garden", "Home & Kitchen");

    @BeforeAll
    static void setup() {
        DatabaseConfiguration.configuration(true);
    }

    @BeforeEach
    void setUp() {
        productsRepository.save(productSKU1);
        productsRepository.save(productSKU2);
        productsRepository.save(productSKU3);
    }

    @AfterEach
    void cleanUp() {
        productsRepository.deleteAll();
    }

    @AfterAll
    static void cleanUpAll() throws SQLException {
        DatabaseConfiguration.dropTable();
    }

    @Test
    @Order(1)
    void saveProductAndSearchByIdTest() {
        System.out.println("saveProductAndSearchByIdTest");

        var productSearched = productsRepository.findById(productSKU1.getSku());

        Assert.notNull(productSearched,"List of products is null");

        // Change to not optional since we know it's not null (can be an empty list)
        var productSearchedNotOptional = productSearched.stream().toList();
        Assert.notEmpty(productSearchedNotOptional, "List of products is empty");

        System.out.println(productSearchedNotOptional.get(0).toString());
    }

    @Test
    @Order(2)
    void saveProductAndSearchFilterByCategoryTest() {
        System.out.println("saveProductAndSearchByCategoryTest");

        var productSearched = productsRepository.findByCategoryIgnoreCase(
                productSKU2.getCategory(), null);

        Assert.notNull(productSearched,"List of products is null");

        Assert.notEmpty(productSearched, "List of products is empty");

        System.out.println(productSearched.get(0).toString());
    }

    @Test
    @Order(3)
    void saveProductsAndSearchOrderedByPriceASCTest() {
        System.out.println("saveProductsAndSearchOrderedByPriceASCTest");

        Sort sort = Sort.by(Sort.Direction.ASC, "price");

        var productSearched = productsRepository.findAll(sort);

        Assert.notNull(productSearched,"List of products is null");
        Assert.notEmpty(productSearched, "List of products is empty");

        Assert.isTrue(productSearched.get(0).getSku().equals(productSKU2.getSku()), "List of products does not match");

        productSearched.forEach(System.out::println);

    }

    @Test
    @Order(4)
    void saveProductsAndSearchOrderedByDescriptionASCTest() {
        System.out.println("saveProductsAndSearchOrderedByDescriptionASCTest");

        Sort sort = Sort.by(Sort.Direction.ASC, "description");

        var productSearched = productsRepository.findAll(sort);

        Assert.notNull(productSearched,"List of products is null");
        Assert.notEmpty(productSearched, "List of products is empty");

        Assert.isTrue(productSearched.get(0).getSku().equals(productSKU3.getSku()), "List of products does not match");

        productSearched.forEach(System.out::println);

    }

    @Test
    @Order(5)
    void saveProductsAndSearchOrderedByCategoryASCTest() {
        System.out.println("saveProductsAndSearchOrderedByCategoryASCTest");

        Sort sort = Sort.by(Sort.Direction.ASC, "category");

        var productSearched = productsRepository.findAll(sort);

        Assert.notNull(productSearched,"List of products is null");
        Assert.notEmpty(productSearched, "List of products is empty");

        Assert.isTrue(productSearched.get(0).getSku().equals(productSKU1.getSku()), "List of products does not match");

        productSearched.forEach(System.out::println);

    }

}
