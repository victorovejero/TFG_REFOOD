package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BenefMapperTest {

    private BenefMapper benefMapper;

    @BeforeEach
    public void setUp() {
        benefMapper = new BenefMapperImpl();
    }
}
