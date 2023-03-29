package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckoutMapperTest {

    private CheckoutMapper checkoutMapper;

    @BeforeEach
    public void setUp() {
        checkoutMapper = new CheckoutMapperImpl();
    }
}
