package com.example.technicaltest.domain;

import java.io.Serializable;
import java.util.Objects;

public class OrderPair implements Serializable {

    private String orderField;
    private String direction;

    public OrderPair(String orderField, String direction) {
        this.orderField = orderField;
        this.direction = direction;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderPair orderPair = (OrderPair) o;
        return Objects.equals(orderField, orderPair.orderField) && Objects.equals(direction, orderPair.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderField, direction);
    }

    @Override
    public String toString() {
        return "OrderPair{" +
                "orderField='" + orderField + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
