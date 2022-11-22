package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NucleoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nucleo.class);
        Nucleo nucleo1 = new Nucleo();
        nucleo1.setId(1L);
        Nucleo nucleo2 = new Nucleo();
        nucleo2.setId(nucleo1.getId());
        assertThat(nucleo1).isEqualTo(nucleo2);
        nucleo2.setId(2L);
        assertThat(nucleo1).isNotEqualTo(nucleo2);
        nucleo1.setId(null);
        assertThat(nucleo1).isNotEqualTo(nucleo2);
    }
}
