package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlEntMapperTest {

    private AlEntMapper alEntMapper;

    @BeforeEach
    public void setUp() {
        alEntMapper = new AlEntMapperImpl();
    }
}
