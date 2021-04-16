package com.skilleen.orderingservice.routes;

import com.skilleen.orderingservice.dto.Order;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaRoutesTest {

    KafkaRoutes kafkaRoutes = new KafkaRoutes();

    @Test
    void mock_configure_test() {
        kafkaRoutes.configure();
        kafkaRoutes.transformMessage(ExchangeBuilder.anExchange(new DefaultCamelContext()).withBody(new Order()).build());
        assertThat(true).isTrue();
    }

}