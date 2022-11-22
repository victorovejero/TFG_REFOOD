package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntoleranciaMapperTest {

    private IntoleranciaMapper intoleranciaMapper;

    @BeforeEach
    public void setUp() {
        intoleranciaMapper = new IntoleranciaMapperImpl();
    }
}
