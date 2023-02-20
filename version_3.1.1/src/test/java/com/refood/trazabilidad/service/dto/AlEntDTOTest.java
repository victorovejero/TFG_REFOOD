package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlEntDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlEntDTO.class);
        AlEntDTO alEntDTO1 = new AlEntDTO();
        alEntDTO1.setId(1L);
        AlEntDTO alEntDTO2 = new AlEntDTO();
        assertThat(alEntDTO1).isNotEqualTo(alEntDTO2);
        alEntDTO2.setId(alEntDTO1.getId());
        assertThat(alEntDTO1).isEqualTo(alEntDTO2);
        alEntDTO2.setId(2L);
        assertThat(alEntDTO1).isNotEqualTo(alEntDTO2);
        alEntDTO1.setId(null);
        assertThat(alEntDTO1).isNotEqualTo(alEntDTO2);
    }
}
