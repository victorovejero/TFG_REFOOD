package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocioDTO.class);
        SocioDTO socioDTO1 = new SocioDTO();
        socioDTO1.setId(1L);
        SocioDTO socioDTO2 = new SocioDTO();
        assertThat(socioDTO1).isNotEqualTo(socioDTO2);
        socioDTO2.setId(socioDTO1.getId());
        assertThat(socioDTO1).isEqualTo(socioDTO2);
        socioDTO2.setId(2L);
        assertThat(socioDTO1).isNotEqualTo(socioDTO2);
        socioDTO1.setId(null);
        assertThat(socioDTO1).isNotEqualTo(socioDTO2);
    }
}
