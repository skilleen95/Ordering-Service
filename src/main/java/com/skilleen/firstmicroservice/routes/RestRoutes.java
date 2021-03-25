package com.skilleen.firstmicroservice.routes;

import com.skilleen.firstmicroservice.dto.Order;
import com.skilleen.firstmicroservice.services.OrderProcessor;
import com.skilleen.firstmicroservice.services.OrderService;
import com.skilleen.firstmicroservice.dto.ShippingOrder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RestRoutes extends RouteBuilder {

    @Autowired
    private OrderService orderService;

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest().get("/hello-world").produces(MediaType.APPLICATION_JSON_VALUE)
                .route().setBody(constant("Hello World From the Order Service!"));

        rest().get("/get-orders")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .setBody(() -> orderService.getOrders());

        rest().post("add-order")
                .type(Order.class)
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:order");

        from("direct:order")
                .log("Incoming Body is ${body}")
                .bean(this,"transformMessage")
                .process(new OrderProcessor())
                .log("Outgoing Body is ${body}");

    }

    public void transformMessage(Exchange exchange){
        Message in = exchange.getIn();
        Order order = in.getBody(Order.class);
        ShippingOrder shippingOrder = new ShippingOrder(order.getName(), order.getPrice());
        in.setBody(shippingOrder);
    }
}
