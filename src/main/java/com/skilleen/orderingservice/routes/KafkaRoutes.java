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
                .to("kafka:scotts-topic?brokers=my-cluster-kafka-brokers:9092");

/*        from("kafka:scotts-topic?brokers=localhost:9092")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");*/
    }
}
