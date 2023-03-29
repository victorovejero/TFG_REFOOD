package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlEntTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlEnt.class);
        AlEnt alEnt1 = new AlEnt();
        alEnt1.setId(1L);
        AlEnt alEnt2 = new AlEnt();
        alEnt2.setId(alEnt1.getId());
        assertThat(alEnt1).isEqualTo(alEnt2);
        alEnt2.setId(2L);
        assertThat(alEnt1).isNotEqualTo(alEnt2);
        alEnt1.setId(null);
        assertThat(alEnt1).isNotEqualTo(alEnt2);
    }
}
