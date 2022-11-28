package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonaBeneficiariaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaBeneficiariaDTO.class);
        PersonaBeneficiariaDTO personaBeneficiariaDTO1 = new PersonaBeneficiariaDTO();
        personaBeneficiariaDTO1.setId(1L);
        PersonaBeneficiariaDTO personaBeneficiariaDTO2 = new PersonaBeneficiariaDTO();
        assertThat(personaBeneficiariaDTO1).isNotEqualTo(personaBeneficiariaDTO2);
        personaBeneficiariaDTO2.setId(personaBeneficiariaDTO1.getId());
        assertThat(personaBeneficiariaDTO1).isEqualTo(personaBeneficiariaDTO2);
        personaBeneficiariaDTO2.setId(2L);
        assertThat(personaBeneficiariaDTO1).isNotEqualTo(personaBeneficiariaDTO2);
        personaBeneficiariaDTO1.setId(null);
        assertThat(personaBeneficiariaDTO1).isNotEqualTo(personaBeneficiariaDTO2);
    }
}
