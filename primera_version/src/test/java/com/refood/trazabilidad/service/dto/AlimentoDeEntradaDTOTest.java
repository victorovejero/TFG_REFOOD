package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlimentoDeEntradaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlimentoDeEntradaDTO.class);
        AlimentoDeEntradaDTO alimentoDeEntradaDTO1 = new AlimentoDeEntradaDTO();
        alimentoDeEntradaDTO1.setId(1L);
        AlimentoDeEntradaDTO alimentoDeEntradaDTO2 = new AlimentoDeEntradaDTO();
        assertThat(alimentoDeEntradaDTO1).isNotEqualTo(alimentoDeEntradaDTO2);
        alimentoDeEntradaDTO2.setId(alimentoDeEntradaDTO1.getId());
        assertThat(alimentoDeEntradaDTO1).isEqualTo(alimentoDeEntradaDTO2);
        alimentoDeEntradaDTO2.setId(2L);
        assertThat(alimentoDeEntradaDTO1).isNotEqualTo(alimentoDeEntradaDTO2);
        alimentoDeEntradaDTO1.setId(null);
        assertThat(alimentoDeEntradaDTO1).isNotEqualTo(alimentoDeEntradaDTO2);
    }
}
