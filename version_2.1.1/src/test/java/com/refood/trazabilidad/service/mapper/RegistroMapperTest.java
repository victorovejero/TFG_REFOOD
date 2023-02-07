package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistroMapperTest {

    private RegistroMapper registroMapper;

    @BeforeEach
    public void setUp() {
        registroMapper = new RegistroMapperImpl();
    }
}
