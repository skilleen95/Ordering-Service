package com.skilleen.orderingservice.routes;

import com.skilleen.orderingservice.dto.Order;
import com.skilleen.orderingservice.services.OrderService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class RestRoutes extends RouteBuilder {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CamelContext camelContext;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void configure() throws Exception {
        camelContext.addService(new org.apache.camel.impl.saga.InMemorySagaService());
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest().get("/hello-world").produces(MediaType.APPLICATION_JSON_VALUE)
                .route().setBody(constant("Hello World From the Order Service!"));

       rest().post("publish-message")
                .type(String.class)
                .to("direct:publish");

       getOrdersRoute();
       addNewOrderRoute();

    }

    private void getOrdersRoute() {
        rest().get("/get-orders")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .setBody(() -> orderService.getOrders());
    }

    private void addNewOrderRoute() {

        onException(Exception.class).markRollbackOnlyLast()
        .log("OH NOOO").routeId("add-order2-exception");
        rest().post("add-order")
                .id("add-order1")
                .type(Order.class)
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:add-order");

        from("direct:add-order")
                .id("add-order2")
                .transacted()
                .saga()
                .multicast()
                .to("direct:insert-new-order")
                .to("direct:create-shipping-request");

    }

}
