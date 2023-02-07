package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonaBeneficiariaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaBeneficiaria.class);
        PersonaBeneficiaria personaBeneficiaria1 = new PersonaBeneficiaria();
        personaBeneficiaria1.setId(1L);
        PersonaBeneficiaria personaBeneficiaria2 = new PersonaBeneficiaria();
        personaBeneficiaria2.setId(personaBeneficiaria1.getId());
        assertThat(personaBeneficiaria1).isEqualTo(personaBeneficiaria2);
        personaBeneficiaria2.setId(2L);
        assertThat(personaBeneficiaria1).isNotEqualTo(personaBeneficiaria2);
        personaBeneficiaria1.setId(null);
        assertThat(personaBeneficiaria1).isNotEqualTo(personaBeneficiaria2);
    }
}
