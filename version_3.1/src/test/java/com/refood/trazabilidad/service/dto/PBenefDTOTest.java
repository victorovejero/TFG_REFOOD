package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PBenefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PBenefDTO.class);
        PBenefDTO pBenefDTO1 = new PBenefDTO();
        pBenefDTO1.setId(1L);
        PBenefDTO pBenefDTO2 = new PBenefDTO();
        assertThat(pBenefDTO1).isNotEqualTo(pBenefDTO2);
        pBenefDTO2.setId(pBenefDTO1.getId());
        assertThat(pBenefDTO1).isEqualTo(pBenefDTO2);
        pBenefDTO2.setId(2L);
        assertThat(pBenefDTO1).isNotEqualTo(pBenefDTO2);
        pBenefDTO1.setId(null);
        assertThat(pBenefDTO1).isNotEqualTo(pBenefDTO2);
    }
}
