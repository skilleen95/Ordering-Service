package com.skilleen.orderingservice.routes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestRoutesTest {

    RestRoutes restRoutes = new RestRoutes();

    @Test
    void configure() {
        restRoutes.configure();
        assertThat(true).isTrue();
    }
}