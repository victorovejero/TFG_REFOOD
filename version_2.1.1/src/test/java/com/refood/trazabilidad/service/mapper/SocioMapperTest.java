package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocioMapperTest {

    private SocioMapper socioMapper;

    @BeforeEach
    public void setUp() {
        socioMapper = new SocioMapperImpl();
    }
}
