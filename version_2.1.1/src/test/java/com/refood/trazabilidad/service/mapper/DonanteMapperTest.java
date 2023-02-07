package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonanteMapperTest {

    private DonanteMapper donanteMapper;

    @BeforeEach
    public void setUp() {
        donanteMapper = new DonanteMapperImpl();
    }
}
