package com.skilleen.orderingservice.services;

import com.skilleen.orderingservice.dto.Order;
import com.skilleen.orderingservice.entities.OrderEntity;
import org.apache.camel.Exchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class OrderAdapter {

    private static final Logger logger = LogManager.getLogger(OrderAdapter.class);

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
