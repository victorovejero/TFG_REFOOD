package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefDTO.class);
        BenefDTO benefDTO1 = new BenefDTO();
        benefDTO1.setId(1L);
        BenefDTO benefDTO2 = new BenefDTO();
        assertThat(benefDTO1).isNotEqualTo(benefDTO2);
        benefDTO2.setId(benefDTO1.getId());
        assertThat(benefDTO1).isEqualTo(benefDTO2);
        benefDTO2.setId(2L);
        assertThat(benefDTO1).isNotEqualTo(benefDTO2);
        benefDTO1.setId(null);
        assertThat(benefDTO1).isNotEqualTo(benefDTO2);
    }
}
