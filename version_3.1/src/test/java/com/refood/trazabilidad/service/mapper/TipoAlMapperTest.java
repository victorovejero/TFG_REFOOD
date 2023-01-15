package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoAlMapperTest {

    private TipoAlMapper tipoAlMapper;

    @BeforeEach
    public void setUp() {
        tipoAlMapper = new TipoAlMapperImpl();
    }
}
