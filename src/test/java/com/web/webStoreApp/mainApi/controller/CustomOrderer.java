package com.web.webStoreApp.mainApi.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;

import java.util.Optional;

public class CustomOrderer implements MethodOrderer {

    @Override
    public void orderMethods(MethodOrdererContext context) {
        context.getMethodDescriptors().sort((m1, m2) -> {
            Optional<Order> o1 = m1.findAnnotation(Order.class);
            Optional<Order> o2 = m2.findAnnotation(Order.class);
            if (o1.isPresent() && o2.isPresent()) {
                return Integer.compare(o1.get().value(), o2.get().value());
            } else if (o1.isPresent()) {
                return -1;
            } else if (o2.isPresent()) {
                return 1;
            } else {
                return 0;
            }
        });
    }
}
