package com.refood.trazabilidad.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaBeneficiariaMapperTest {

    private PersonaBeneficiariaMapper personaBeneficiariaMapper;

    @BeforeEach
    public void setUp() {
        personaBeneficiariaMapper = new PersonaBeneficiariaMapperImpl();
    }
}
