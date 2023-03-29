package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlSalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlSalDTO.class);
        AlSalDTO alSalDTO1 = new AlSalDTO();
        alSalDTO1.setId(1L);
        AlSalDTO alSalDTO2 = new AlSalDTO();
        assertThat(alSalDTO1).isNotEqualTo(alSalDTO2);
        alSalDTO2.setId(alSalDTO1.getId());
        assertThat(alSalDTO1).isEqualTo(alSalDTO2);
        alSalDTO2.setId(2L);
        assertThat(alSalDTO1).isNotEqualTo(alSalDTO2);
        alSalDTO1.setId(null);
        assertThat(alSalDTO1).isNotEqualTo(alSalDTO2);
    }
}
