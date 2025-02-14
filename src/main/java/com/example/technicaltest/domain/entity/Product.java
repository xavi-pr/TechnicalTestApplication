package com.example.technicaltest.domain.entity;

import com.example.technicaltest.utils.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = Constants.PRODUCTS_TABLE_NAME)
public class Product {

    @Id
    @Column(name = Constants.SKU)
    private String sku;
    @Column(name = Constants.PRICE)
    private Float price;
    @Column(name = Constants.DESCRIPTION)
    private String description;
    @Column(name = Constants.CATEGORY)
    private String category;

    public Product() {}

    public Product(Product product) {
        this.sku = product.getSku();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.category = product.getCategory();
    }

    // Constructor to create new object
    public Product(String skuIn, Float priceIn, String descriptionIn, String categoryIn) {
        this.setSku(skuIn);
        this.setPrice(priceIn);
        this.setDescription(descriptionIn);
        this.setCategory(categoryIn);
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product products = (Product) o;
        return Objects.equals(sku, products.sku) && Objects.equals(price, products.price) && Objects.equals(description, products.description) && Objects.equals(category, products.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, price, description, category);
    }

    @Override
    public String toString() {
        return "Products{" +
                "sku='" + sku + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
