package com.skilleen.orderingservice.routes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestRoutesTest {

    RestRoutes restRoutes = new RestRoutes();

    @Test
    void mock_configure_test() {
        restRoutes.configure();
        assertThat(true).isTrue();
    }
}
