package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PBenefMapperTest {

    private PBenefMapper pBenefMapper;

    @BeforeEach
    public void setUp() {
        pBenefMapper = new PBenefMapperImpl();
    }
}
