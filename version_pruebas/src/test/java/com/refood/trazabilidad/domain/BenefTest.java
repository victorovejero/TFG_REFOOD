package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benef.class);
        Benef benef1 = new Benef();
        benef1.setId(1L);
        Benef benef2 = new Benef();
        benef2.setId(benef1.getId());
        assertThat(benef1).isEqualTo(benef2);
        benef2.setId(2L);
        assertThat(benef1).isNotEqualTo(benef2);
        benef1.setId(null);
        assertThat(benef1).isNotEqualTo(benef2);
    }
}
