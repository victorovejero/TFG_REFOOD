package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoAlDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAlDTO.class);
        TipoAlDTO tipoAlDTO1 = new TipoAlDTO();
        tipoAlDTO1.setId(1L);
        TipoAlDTO tipoAlDTO2 = new TipoAlDTO();
        assertThat(tipoAlDTO1).isNotEqualTo(tipoAlDTO2);
        tipoAlDTO2.setId(tipoAlDTO1.getId());
        assertThat(tipoAlDTO1).isEqualTo(tipoAlDTO2);
        tipoAlDTO2.setId(2L);
        assertThat(tipoAlDTO1).isNotEqualTo(tipoAlDTO2);
        tipoAlDTO1.setId(null);
        assertThat(tipoAlDTO1).isNotEqualTo(tipoAlDTO2);
    }
}
