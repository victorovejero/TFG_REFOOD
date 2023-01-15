package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TupperTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tupper.class);
        Tupper tupper1 = new Tupper();
        tupper1.setId(1L);
        Tupper tupper2 = new Tupper();
        tupper2.setId(tupper1.getId());
        assertThat(tupper1).isEqualTo(tupper2);
        tupper2.setId(2L);
        assertThat(tupper1).isNotEqualTo(tupper2);
        tupper1.setId(null);
        assertThat(tupper1).isNotEqualTo(tupper2);
    }
}
