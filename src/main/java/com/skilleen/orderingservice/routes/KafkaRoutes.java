package com.skilleen.orderingservice.routes;

import com.skilleen.orderingservice.dto.Order;
import com.skilleen.orderingservice.dto.ShippingOrder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class KafkaRoutes extends RouteBuilder {

    @Override
    @Transactional
    public void configure() {
        from("direct:publish")
                .setBody(constant("Message from Scott"))
                .log("Sending Message!")
                .to("kafka:scotts-topic?brokers=172.30.74.234:9092");

        from("direct:create-shipping-request")
                .saga()
                .propagation(SagaPropagation.SUPPORTS)
                .id("order-route")
                .bean(this,"transformMessage")
                .marshal().json(JsonLibrary.Jackson)
                .log("Order confirmed! Sending to Shipping service..")
                .to("kafka:order-request?brokers=172.30.74.234:9092")
                .id("order-publish")
                .unmarshal().json(JsonLibrary.Jackson)
                .log("DONE")
                .end();

    }

    public void transformMessage(Exchange exchange){
        Message in = exchange.getIn();
        Order order = in.getBody(Order.class);
        String s = null;
        s.contains("hehe");
        log.info("Received Order: " + order);
        ShippingOrder shippingOrder = new ShippingOrder(order.getName(), order.getPrice());
        in.setBody(shippingOrder);
    }
}
