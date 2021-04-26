package com.skilleen.orderingservice.routes;

import com.skilleen.orderingservice.OrderingServiceApplication;
import com.skilleen.orderingservice.dto.Order;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = {OrderingServiceApplication.class})
public class KafkaRoutesTest {

    @Autowired
    CamelContext camelContext;

    @EndpointInject(uri = "mock:kafka:order-request")
    private MockEndpoint kafkaEndpoint;

    @Test
    public void testKafkaRoute() throws Exception {
        // SETUP
        camelContext.getRouteDefinition("order-route").adviceWith(camelContext, new AdviceWithRouteBuilder() {

            @Override
            public void configure() {
                weaveById("order-publish").replace().multicast().to("mock:kafka:order-request");
            }
        });

        // INPUT
        Order order = new Order(1, "test", 12);

        // EXECUTION
        camelContext.createProducerTemplate().sendBody("direct:order", order);

        // OUTPUT
        kafkaEndpoint.expectedMessageCount(1);
        kafkaEndpoint.assertIsSatisfied();
        assertThat(kafkaEndpoint.getExchanges().get(0).getIn().getBody()).isNotNull();
        camelContext.stop();
    }

}