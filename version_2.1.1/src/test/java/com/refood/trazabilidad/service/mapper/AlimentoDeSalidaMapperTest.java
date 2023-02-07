package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlimentoDeSalidaMapperTest {

    private AlimentoDeSalidaMapper alimentoDeSalidaMapper;

    @BeforeEach
    public void setUp() {
        alimentoDeSalidaMapper = new AlimentoDeSalidaMapperImpl();
    }
}
