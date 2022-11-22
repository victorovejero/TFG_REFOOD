package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntoleranciaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntoleranciaDTO.class);
        IntoleranciaDTO intoleranciaDTO1 = new IntoleranciaDTO();
        intoleranciaDTO1.setId(1L);
        IntoleranciaDTO intoleranciaDTO2 = new IntoleranciaDTO();
        assertThat(intoleranciaDTO1).isNotEqualTo(intoleranciaDTO2);
        intoleranciaDTO2.setId(intoleranciaDTO1.getId());
        assertThat(intoleranciaDTO1).isEqualTo(intoleranciaDTO2);
        intoleranciaDTO2.setId(2L);
        assertThat(intoleranciaDTO1).isNotEqualTo(intoleranciaDTO2);
        intoleranciaDTO1.setId(null);
        assertThat(intoleranciaDTO1).isNotEqualTo(intoleranciaDTO2);
    }
}
