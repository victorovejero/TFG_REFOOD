package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoluntarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoluntarioDTO.class);
        VoluntarioDTO voluntarioDTO1 = new VoluntarioDTO();
        voluntarioDTO1.setId(1L);
        VoluntarioDTO voluntarioDTO2 = new VoluntarioDTO();
        assertThat(voluntarioDTO1).isNotEqualTo(voluntarioDTO2);
        voluntarioDTO2.setId(voluntarioDTO1.getId());
        assertThat(voluntarioDTO1).isEqualTo(voluntarioDTO2);
        voluntarioDTO2.setId(2L);
        assertThat(voluntarioDTO1).isNotEqualTo(voluntarioDTO2);
        voluntarioDTO1.setId(null);
        assertThat(voluntarioDTO1).isNotEqualTo(voluntarioDTO2);
    }
}
