package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoluntarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voluntario.class);
        Voluntario voluntario1 = new Voluntario();
        voluntario1.setId(1L);
        Voluntario voluntario2 = new Voluntario();
        voluntario2.setId(voluntario1.getId());
        assertThat(voluntario1).isEqualTo(voluntario2);
        voluntario2.setId(2L);
        assertThat(voluntario1).isNotEqualTo(voluntario2);
        voluntario1.setId(null);
        assertThat(voluntario1).isNotEqualTo(voluntario2);
    }
}
