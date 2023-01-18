package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlSalMapperTest {

    private AlSalMapper alSalMapper;

    @BeforeEach
    public void setUp() {
        alSalMapper = new AlSalMapperImpl();
    }
}
