package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intol.class);
        Intol intol1 = new Intol();
        intol1.setId(1L);
        Intol intol2 = new Intol();
        intol2.setId(intol1.getId());
        assertThat(intol1).isEqualTo(intol2);
        intol2.setId(2L);
        assertThat(intol1).isNotEqualTo(intol2);
        intol1.setId(null);
        assertThat(intol1).isNotEqualTo(intol2);
    }
}
