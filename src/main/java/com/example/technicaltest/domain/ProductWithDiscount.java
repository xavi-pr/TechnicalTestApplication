package com.example.technicaltest.domain;

import com.example.technicaltest.domain.entity.Product;

import java.util.Objects;

public class ProductWithDiscount extends Product {

    private String discount;
    private double priceWithDiscount;

    public ProductWithDiscount() {}

    public ProductWithDiscount(Product product) {
        super(product);
        this.discount = "";
        this.priceWithDiscount = product.getPrice();
    }

    public ProductWithDiscount(Product product, String discount, double priceWithDiscount) {
        super(product);
        this.discount = discount;
        this.priceWithDiscount = priceWithDiscount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductWithDiscount that = (ProductWithDiscount) o;
        return Double.compare(priceWithDiscount, that.priceWithDiscount) == 0 && Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), discount, priceWithDiscount);
    }

    @Override
    public String toString() {
        return "ProductWithDiscount{" +
                "discount='" + discount + '\'' +
                ", priceWithDiscount=" + priceWithDiscount +
                '}';
    }
}
