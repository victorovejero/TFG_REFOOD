package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FrutaYVerduraMapperTest {

    private FrutaYVerduraMapper frutaYVerduraMapper;

    @BeforeEach
    public void setUp() {
        frutaYVerduraMapper = new FrutaYVerduraMapperImpl();
    }
}
