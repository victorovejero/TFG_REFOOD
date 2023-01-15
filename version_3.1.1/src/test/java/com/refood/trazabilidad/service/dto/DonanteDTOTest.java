package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonanteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonanteDTO.class);
        DonanteDTO donanteDTO1 = new DonanteDTO();
        donanteDTO1.setId(1L);
        DonanteDTO donanteDTO2 = new DonanteDTO();
        assertThat(donanteDTO1).isNotEqualTo(donanteDTO2);
        donanteDTO2.setId(donanteDTO1.getId());
        assertThat(donanteDTO1).isEqualTo(donanteDTO2);
        donanteDTO2.setId(2L);
        assertThat(donanteDTO1).isNotEqualTo(donanteDTO2);
        donanteDTO1.setId(null);
        assertThat(donanteDTO1).isNotEqualTo(donanteDTO2);
    }
}
