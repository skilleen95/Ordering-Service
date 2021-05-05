package com.skilleen.orderingservice.services;

import com.skilleen.orderingservice.dto.Order;
import com.skilleen.orderingservice.entities.OrderEntity;
import org.apache.camel.Exchange;

public class OrderAdapter {

    public void adaptToOrderEntity(Exchange exchange) {
        Order order = exchange.getIn().getBody(Order.class);
        OrderEntity orderEntity = OrderEntity.builder()
                                             .itemName(order.getName())
                                             .price(order.getPrice())
                                             .customerId(order.getCustomerId())
                                             .build();
        exchange.getIn().setBody(orderEntity);
    }
}
