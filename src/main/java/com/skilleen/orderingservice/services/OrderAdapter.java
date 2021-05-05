package com.skilleen.orderingservice.services;

import com.skilleen.orderingservice.dto.Order;
import com.skilleen.orderingservice.entities.OrderEntity;
import org.apache.camel.Exchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class OrderAdapter {

    private static final Logger logger = LogManager.getLogger(OrderProcessor.class);

    public void adaptToOrderEntity(Exchange exchange) {
        logger.info("WE ARE HERE");
        Order order = exchange.getIn().getBody(Order.class);
        OrderEntity orderEntity = OrderEntity.builder()
                                             .itemName(order.getName())
                                             .price(order.getPrice())
                                             .customerId(order.getCustomerId())
                                             .build();
        logger.info("WE MADE IT");
        exchange.getIn().setBody(orderEntity);
    }
}
