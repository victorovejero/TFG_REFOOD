package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PBenefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PBenef.class);
        PBenef pBenef1 = new PBenef();
        pBenef1.setId(1L);
        PBenef pBenef2 = new PBenef();
        pBenef2.setId(pBenef1.getId());
        assertThat(pBenef1).isEqualTo(pBenef2);
        pBenef2.setId(2L);
        assertThat(pBenef1).isNotEqualTo(pBenef2);
        pBenef1.setId(null);
        assertThat(pBenef1).isNotEqualTo(pBenef2);
    }
}
