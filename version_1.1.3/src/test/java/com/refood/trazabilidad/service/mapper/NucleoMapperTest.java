package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NucleoMapperTest {

    private NucleoMapper nucleoMapper;

    @BeforeEach
    public void setUp() {
        nucleoMapper = new NucleoMapperImpl();
    }
}
