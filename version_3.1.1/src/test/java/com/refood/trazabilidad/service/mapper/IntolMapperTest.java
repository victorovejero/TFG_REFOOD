package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntolMapperTest {

    private IntolMapper intolMapper;

    @BeforeEach
    public void setUp() {
        intolMapper = new IntolMapperImpl();
    }
}
