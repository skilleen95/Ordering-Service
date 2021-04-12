package com.skilleen.orderingservice.services;

import com.skilleen.orderingservice.dto.ShippingOrder;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class OrderProcessor implements Processor {

    RestTemplate restTemplate;

    private final String shippingUrl = System.getenv("shipping-service-dev-urlss");
    private static final Logger logger = LogManager.getLogger(OrderProcessor.class);

    @Override
    public void process(Exchange exchange) {
        ShippingOrder order = exchange.getIn().getBody(ShippingOrder.class);
        exchange.getOut().setBody(updateShippingServiceWithOrder(order));
    }

    private String updateShippingServiceWithOrder(ShippingOrder order) {
        HttpEntity<ShippingOrder> httpEntity = new HttpEntity<>(order);
        if (shippingUrl != null) {
            System.out.println("Sending Order to Shipping Service");
            ResponseEntity<String> response = restTemplate.postForEntity(shippingUrl + "/ship-order", httpEntity, String.class);
            System.out.println("DONE");
            System.out.println(response.getBody());
            return response.getBody();
        }
        else {
            logger.error("Invalid Shipping URL");
            System.err.println("Invalid Shipping URL");
            return "Invalid Shipping URL";
        }
    }
}
