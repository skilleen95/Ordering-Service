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
                .option("OptionId", simple("${body}"));

        from("direct:removeOrder")
                .log("Error occured, removing order entry to database")
                .transform(header("OptionId"))
                .to("jpa:" + OrderEntity.class.getName() + "?query=delete from CustomerOrder where customer_Id = 21 &useExecuteUpdate=true")
                .log("Order cleaned up From Database");
    }
}
