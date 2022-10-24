package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoDeAlimentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeAlimentoDTO.class);
        TipoDeAlimentoDTO tipoDeAlimentoDTO1 = new TipoDeAlimentoDTO();
        tipoDeAlimentoDTO1.setId(1L);
        TipoDeAlimentoDTO tipoDeAlimentoDTO2 = new TipoDeAlimentoDTO();
        assertThat(tipoDeAlimentoDTO1).isNotEqualTo(tipoDeAlimentoDTO2);
        tipoDeAlimentoDTO2.setId(tipoDeAlimentoDTO1.getId());
        assertThat(tipoDeAlimentoDTO1).isEqualTo(tipoDeAlimentoDTO2);
        tipoDeAlimentoDTO2.setId(2L);
        assertThat(tipoDeAlimentoDTO1).isNotEqualTo(tipoDeAlimentoDTO2);
        tipoDeAlimentoDTO1.setId(null);
        assertThat(tipoDeAlimentoDTO1).isNotEqualTo(tipoDeAlimentoDTO2);
    }
}
