package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntoleranciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intolerancia.class);
        Intolerancia intolerancia1 = new Intolerancia();
        intolerancia1.setId(1L);
        Intolerancia intolerancia2 = new Intolerancia();
        intolerancia2.setId(intolerancia1.getId());
        assertThat(intolerancia1).isEqualTo(intolerancia2);
        intolerancia2.setId(2L);
        assertThat(intolerancia1).isNotEqualTo(intolerancia2);
        intolerancia1.setId(null);
        assertThat(intolerancia1).isNotEqualTo(intolerancia2);
    }
}
