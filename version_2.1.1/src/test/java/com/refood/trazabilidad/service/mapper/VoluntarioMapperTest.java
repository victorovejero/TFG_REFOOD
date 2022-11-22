package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoluntarioMapperTest {

    private VoluntarioMapper voluntarioMapper;

    @BeforeEach
    public void setUp() {
        voluntarioMapper = new VoluntarioMapperImpl();
    }
}
