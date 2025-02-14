package com.example.technicaltest.utils;

import com.example.technicaltest.domain.OrderPair;
import com.example.technicaltest.domain.entity.Product;
import com.example.technicaltest.exceptions.FieldDontMatchColumnException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductsHelper {

    /**
     * Get Sort.Orders for the orderBy of the query
     * @param orderBy order entered through post body
     * @return list of Sort.Order
     */
    public List<Sort.Order> getOrders(List<OrderPair> orderBy) {
        List<Sort.Order> orders = new ArrayList<>();
        // Add orderBy for order field on the post body
        if (orderBy != null && !orderBy.isEmpty()) {

            // Get the JPA fields to check if we have the column to order by on the table
            List<Field> productFields = Arrays.stream(Product.class.getDeclaredFields()).toList();

            // Get the columns name
            List<String> productFieldsString = productFields.stream().map(Field::getName).toList();
            // Lowercase the fields to avoid miscomparison
            List<String> lowercaseProductFieldsString = productFieldsString.stream().map(String::toLowerCase).toList();

            // For every field in the orderBy check if exists and add it to the orders for sort
            orderBy.forEach(orderPair -> {
                        // Check if we have the column asked for order in the JPA, if not, throw exception
                        if (!lowercaseProductFieldsString.contains(orderPair.getOrderField().toLowerCase())) {
                            throw new FieldDontMatchColumnException("The field to order doesn't exist on table Products");
                        }
                        orders.add(new Sort.Order(
                                // Check we have ASC or default DESC to avoid other values
                                Sort.Direction.fromString(
                                        orderPair.getDirection()
                                                .equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                                                Sort.Direction.ASC.name() : Sort.Direction.DESC.name()),
                                // Lowercase to match the column
                                orderPair.getOrderField().toLowerCase())
                        );
                    }
            );
        }

        return orders;
    }

}
