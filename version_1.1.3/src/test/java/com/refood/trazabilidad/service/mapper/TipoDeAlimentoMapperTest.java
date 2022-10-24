package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoDeAlimentoMapperTest {

    private TipoDeAlimentoMapper tipoDeAlimentoMapper;

    @BeforeEach
    public void setUp() {
        tipoDeAlimentoMapper = new TipoDeAlimentoMapperImpl();
    }
}
