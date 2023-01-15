package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlSalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlSal.class);
        AlSal alSal1 = new AlSal();
        alSal1.setId(1L);
        AlSal alSal2 = new AlSal();
        alSal2.setId(alSal1.getId());
        assertThat(alSal1).isEqualTo(alSal2);
        alSal2.setId(2L);
        assertThat(alSal1).isNotEqualTo(alSal2);
        alSal1.setId(null);
        assertThat(alSal1).isNotEqualTo(alSal2);
    }
}
