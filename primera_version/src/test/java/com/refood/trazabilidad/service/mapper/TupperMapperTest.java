package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TupperMapperTest {

    private TupperMapper tupperMapper;

    @BeforeEach
    public void setUp() {
        tupperMapper = new TupperMapperImpl();
    }
}
