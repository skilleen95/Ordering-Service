package com.skilleen.firstmicroservice.services;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OrderProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        ShippingOrder order = exchange.getIn().getBody(ShippingOrder.class);
        exchange.getOut().setBody(updateShippingServiceWithOrder(order));
    }

    public String updateShippingServiceWithOrder(ShippingOrder order) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ShippingOrder> httpEntity = new HttpEntity<>(order);
        String url = "http://shipping-service-scilleen-code.apps.sandbox-m2.ll9k.p1.openshiftapps.com/ship-order";
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        return response.getBody();
    }
}
