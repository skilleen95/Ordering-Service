package com.skilleen.firstmicroservice.Routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RestRoutes extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);
        rest().get("/hello-world").produces(MediaType.APPLICATION_JSON_VALUE)
                .route().setBody(constant("Welcome to REST demo"));


    }
}
