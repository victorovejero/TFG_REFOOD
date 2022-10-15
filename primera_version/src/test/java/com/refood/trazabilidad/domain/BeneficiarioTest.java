package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeneficiarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beneficiario.class);
        Beneficiario beneficiario1 = new Beneficiario();
        beneficiario1.setId(1L);
        Beneficiario beneficiario2 = new Beneficiario();
        beneficiario2.setId(beneficiario1.getId());
        assertThat(beneficiario1).isEqualTo(beneficiario2);
        beneficiario2.setId(2L);
        assertThat(beneficiario1).isNotEqualTo(beneficiario2);
        beneficiario1.setId(null);
        assertThat(beneficiario1).isNotEqualTo(beneficiario2);
    }
}
