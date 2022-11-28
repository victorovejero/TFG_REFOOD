package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FrutaYVerduraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FrutaYVerduraDTO.class);
        FrutaYVerduraDTO frutaYVerduraDTO1 = new FrutaYVerduraDTO();
        frutaYVerduraDTO1.setId(1L);
        FrutaYVerduraDTO frutaYVerduraDTO2 = new FrutaYVerduraDTO();
        assertThat(frutaYVerduraDTO1).isNotEqualTo(frutaYVerduraDTO2);
        frutaYVerduraDTO2.setId(frutaYVerduraDTO1.getId());
        assertThat(frutaYVerduraDTO1).isEqualTo(frutaYVerduraDTO2);
        frutaYVerduraDTO2.setId(2L);
        assertThat(frutaYVerduraDTO1).isNotEqualTo(frutaYVerduraDTO2);
        frutaYVerduraDTO1.setId(null);
        assertThat(frutaYVerduraDTO1).isNotEqualTo(frutaYVerduraDTO2);
    }
}
