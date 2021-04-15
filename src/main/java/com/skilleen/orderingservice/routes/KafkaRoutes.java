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
                .log("Recieved Message!")
                .to("kafka:scotts-topic?brokers=172.30.74.234:9092");

        from("kafka:scotts-topic?brokers=172.30.74.234:9092")
                .log("Message received from Kafka : ${body}");
    }
}
