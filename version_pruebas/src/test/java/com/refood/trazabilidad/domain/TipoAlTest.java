package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoAlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAl.class);
        TipoAl tipoAl1 = new TipoAl();
        tipoAl1.setId(1L);
        TipoAl tipoAl2 = new TipoAl();
        tipoAl2.setId(tipoAl1.getId());
        assertThat(tipoAl1).isEqualTo(tipoAl2);
        tipoAl2.setId(2L);
        assertThat(tipoAl1).isNotEqualTo(tipoAl2);
        tipoAl1.setId(null);
        assertThat(tipoAl1).isNotEqualTo(tipoAl2);
    }
}
