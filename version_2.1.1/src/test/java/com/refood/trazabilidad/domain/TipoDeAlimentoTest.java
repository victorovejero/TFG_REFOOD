package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoDeAlimentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeAlimento.class);
        TipoDeAlimento tipoDeAlimento1 = new TipoDeAlimento();
        tipoDeAlimento1.setId(1L);
        TipoDeAlimento tipoDeAlimento2 = new TipoDeAlimento();
        tipoDeAlimento2.setId(tipoDeAlimento1.getId());
        assertThat(tipoDeAlimento1).isEqualTo(tipoDeAlimento2);
        tipoDeAlimento2.setId(2L);
        assertThat(tipoDeAlimento1).isNotEqualTo(tipoDeAlimento2);
        tipoDeAlimento1.setId(null);
        assertThat(tipoDeAlimento1).isNotEqualTo(tipoDeAlimento2);
    }
}
