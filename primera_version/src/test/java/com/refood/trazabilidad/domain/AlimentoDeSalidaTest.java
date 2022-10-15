package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlimentoDeSalidaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlimentoDeSalida.class);
        AlimentoDeSalida alimentoDeSalida1 = new AlimentoDeSalida();
        alimentoDeSalida1.setId(1L);
        AlimentoDeSalida alimentoDeSalida2 = new AlimentoDeSalida();
        alimentoDeSalida2.setId(alimentoDeSalida1.getId());
        assertThat(alimentoDeSalida1).isEqualTo(alimentoDeSalida2);
        alimentoDeSalida2.setId(2L);
        assertThat(alimentoDeSalida1).isNotEqualTo(alimentoDeSalida2);
        alimentoDeSalida1.setId(null);
        assertThat(alimentoDeSalida1).isNotEqualTo(alimentoDeSalida2);
    }
}
