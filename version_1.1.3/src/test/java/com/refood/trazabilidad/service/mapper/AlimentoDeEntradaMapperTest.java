package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlimentoDeEntradaMapperTest {

    private AlimentoDeEntradaMapper alimentoDeEntradaMapper;

    @BeforeEach
    public void setUp() {
        alimentoDeEntradaMapper = new AlimentoDeEntradaMapperImpl();
    }
}
