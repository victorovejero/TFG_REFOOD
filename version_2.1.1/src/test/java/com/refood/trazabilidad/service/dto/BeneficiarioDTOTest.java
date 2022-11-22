package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeneficiarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiarioDTO.class);
        BeneficiarioDTO beneficiarioDTO1 = new BeneficiarioDTO();
        beneficiarioDTO1.setId(1L);
        BeneficiarioDTO beneficiarioDTO2 = new BeneficiarioDTO();
        assertThat(beneficiarioDTO1).isNotEqualTo(beneficiarioDTO2);
        beneficiarioDTO2.setId(beneficiarioDTO1.getId());
        assertThat(beneficiarioDTO1).isEqualTo(beneficiarioDTO2);
        beneficiarioDTO2.setId(2L);
        assertThat(beneficiarioDTO1).isNotEqualTo(beneficiarioDTO2);
        beneficiarioDTO1.setId(null);
        assertThat(beneficiarioDTO1).isNotEqualTo(beneficiarioDTO2);
    }
}
