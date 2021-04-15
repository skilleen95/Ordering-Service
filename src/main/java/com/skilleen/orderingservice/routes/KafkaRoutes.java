package com.skilleen.orderingservice.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaRoutes extends RouteBuilder {


    @Override
    public void configure() {
        from("direct:publish")
                .setBody(constant("Message from Scott"))          // Message to send
                .setHeader("KEY", constant("hello")) // Key of the message
                .log("Sending Message!")
                .to("kafka:scotts-topic?brokers=172.30.74.234:9092");

    }
}
