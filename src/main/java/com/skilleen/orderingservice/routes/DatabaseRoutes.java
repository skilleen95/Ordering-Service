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
                .option("OptionId", "${body}");

        from("direct:removeOrder")
                .transform(header("OptionId")) // retrieve the CreditId option from headers
                .log("OHNOOOOO")
                .transform(header("OptionId"))
                //.bean(creditService, "refundCredit")
                .log("Credit for Custom Id ${body} refunded");
    }
}
