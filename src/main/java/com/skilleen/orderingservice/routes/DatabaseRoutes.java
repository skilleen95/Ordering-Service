package com.skilleen.orderingservice.routes;

import com.skilleen.orderingservice.entities.OrderEntity;
import com.skilleen.orderingservice.services.OrderAdapter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DatabaseRoutes extends RouteBuilder {

    @Autowired
    private OrderAdapter orderAdapter;

    @Override
    public void configure() {
        from("direct:insert-new-order")
                .id("database")
                .saga()
                .propagation(SagaPropagation.SUPPORTS)
                .compensation("direct:removeOrder")
                .bean(orderAdapter, "adaptToOrderEntity")
                .log("Saving Order to Database: ")
                .to("jpa:" + OrderEntity.class.getName() + "?useExecuteUpdate=true")
                .log("Database save successful")
                .log("RIGHT HERE: ${body}")
                .setHeader("test", simple("${body.orderId}"))
                .log("IN ${in.header.test}")
                .log("OUT ${out.header.test}");
                .option("OptionId", simple("${in.header.test}"));

        from("direct:removeOrder")
                .log("NOW HERE: ${body}")
                .log("AND THEN ${in.header.test}")
                .transform(header("OptionId")) // retrieve the CreditId option from headers
                .log("OHNOOOOO")
                .log("NOW HERE: ${body}")
                .transform(header("OptionId"))
                //.bean(creditService, "refundCredit")
                .log("Credit for Custom Id ${body} refunded");
    }
}
