package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FrutaYVerduraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FrutaYVerdura.class);
        FrutaYVerdura frutaYVerdura1 = new FrutaYVerdura();
        frutaYVerdura1.setId(1L);
        FrutaYVerdura frutaYVerdura2 = new FrutaYVerdura();
        frutaYVerdura2.setId(frutaYVerdura1.getId());
        assertThat(frutaYVerdura1).isEqualTo(frutaYVerdura2);
        frutaYVerdura2.setId(2L);
        assertThat(frutaYVerdura1).isNotEqualTo(frutaYVerdura2);
        frutaYVerdura1.setId(null);
        assertThat(frutaYVerdura1).isNotEqualTo(frutaYVerdura2);
    }
}
