package com.skilleen.firstmicroservice.services;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderProcessor implements Processor {

    @Value("${shipping-service-dev-url:default}")
    private String shippingUrl;

    @Override
    public void process(Exchange exchange) {
        ShippingOrder order = exchange.getIn().getBody(ShippingOrder.class);
        exchange.getOut().setBody(updateShippingServiceWithOrder(order));
    }

    public String updateShippingServiceWithOrder(ShippingOrder order) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ShippingOrder> httpEntity = new HttpEntity<>(order);
        //http://shipping-service-scilleen-code.apps.sandbox-m2.ll9k.p1.openshiftapps.com
        if (shippingUrl != null) {
            String shipOrderUrl = shippingUrl + "/ship-order";
            ResponseEntity<String> response = restTemplate.postForEntity(shipOrderUrl, httpEntity, String.class);
            return response.getBody();
        }
        else {
            return "oops" + System.getenv("shipping-service-dev-url");
        }
    }
}
