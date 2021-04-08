package com.skilleen.orderingservice.routes;

import com.skilleen.orderingservice.dto.Order;
import com.skilleen.orderingservice.services.OrderProcessor;
import com.skilleen.orderingservice.services.OrderService;
import com.skilleen.orderingservice.dto.ShippingOrder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
                .bean(this,"transformMessage")
                .process(new OrderProcessor(new RestTemplate()));

    }

    public void transformMessage(Exchange exchange){
        Message in = exchange.getIn();
        Order order = in.getBody(Order.class);
        ShippingOrder shippingOrder = new ShippingOrder(order.getName(), order.getPrice());
        in.setBody(shippingOrder);
    }

}
