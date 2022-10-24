package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeneficiarioMapperTest {

    private BeneficiarioMapper beneficiarioMapper;

    @BeforeEach
    public void setUp() {
        beneficiarioMapper = new BeneficiarioMapperImpl();
    }
}
