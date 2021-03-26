package com.skilleen.orderingservice.services;

import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultMessage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderProcessorTest {

    @Mock
    RestTemplate restTemplate;

    OrderProcessor orderProcessor;

    @Before
    public void createMocks() {
        MockitoAnnotations.initMocks(this);
        orderProcessor = new OrderProcessor(restTemplate);
    }

    @Test
    public void anExchange_process_returnsResponseFromShippingService() {
        Exchange testExchange = ExchangeBuilder.anExchange(new DefaultCamelContext()).withBody("test").build();
        testExchange.setOut(new DefaultMessage());

        when(restTemplate.postForEntity(any(String.class), any(), any()))
                .thenReturn(new ResponseEntity<>("Test", HttpStatus.OK));

        orderProcessor.process(testExchange);

    }

}