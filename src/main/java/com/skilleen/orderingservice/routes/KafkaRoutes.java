package com.skilleen.orderingservice.routes;

import com.skilleen.orderingservice.dto.Order;
import com.skilleen.orderingservice.dto.ShippingOrder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class KafkaRoutes extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:publish")
                .setBody(constant("Message from Scott"))
                .log("Sending Message!")
                .to("kafka:scotts-topic?brokers=172.30.74.234:9092");

        from("direct:order")
                .id("order-route")
                .bean(this,"transformMessage")
                .marshal().json(JsonLibrary.Jackson)
                .log("Order confirmed! Sending to Shipping service..")
                .to("kafka:order-request?brokers=172.30.74.234:9092")
                .id("order-publish")
                .log("DONE");

    }

    public void transformMessage(Exchange exchange){
        Message in = exchange.getIn();
        Order order = in.getBody(Order.class);
        log.info("Received Order: " + order);
        ShippingOrder shippingOrder = new ShippingOrder(order.getName(), order.getPrice());
        in.setBody(shippingOrder);
    }
}
