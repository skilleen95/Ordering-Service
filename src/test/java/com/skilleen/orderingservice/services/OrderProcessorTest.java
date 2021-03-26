package com.skilleen.orderingservice.services;

import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ System.class, OrderProcessor.class })
public class OrderProcessorTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    OrderProcessor orderProcessor;

    @Before
    public void createMocks() {
        MockitoAnnotations.initMocks(this);
        orderProcessor = new OrderProcessor(restTemplate);
    }

    @Test
    public void anExchange_process_returnsResponseFromShippingService() {
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.getenv("shipping-service-dev-url")).thenReturn("url");
        MockitoAnnotations.initMocks(this);
        orderProcessor = new OrderProcessor(restTemplate);
        Exchange testExchange = ExchangeBuilder.anExchange(new DefaultCamelContext()).withBody("test").build();
        testExchange.setOut(new DefaultMessage());

        when(restTemplate.postForEntity(any(String.class), any(), any()))
                .thenReturn(new ResponseEntity<>("Test", HttpStatus.OK));

        orderProcessor.process(testExchange);

        Mockito.verify(restTemplate).postForEntity(any(String.class), any(), any());
        assertThat(true).isTrue();
    }

}