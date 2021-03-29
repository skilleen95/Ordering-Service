package com.skilleen.orderingservice.services;

import com.skilleen.orderingservice.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {

    OrderService orderService = new OrderService();

    @BeforeEach
    private void setup() {
        orderService.initDB();
    }

    @Test
    void getOrders_returnsListOfOrders() {
        List<Order> orders = orderService.getOrders();

        assertThat(orders).hasSize(4);
    }

    @Test
    void anOrder_addOrder_orderIsAddedToList() {
        Order order = new Order(1, "item", 20);

        orderService.addOrder(order);

        assertThat(orderService.getOrders()).contains(order);
    }


}