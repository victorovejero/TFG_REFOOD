package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckoutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Checkout.class);
        Checkout checkout1 = new Checkout();
        checkout1.setId(1L);
        Checkout checkout2 = new Checkout();
        checkout2.setId(checkout1.getId());
        assertThat(checkout1).isEqualTo(checkout2);
        checkout2.setId(2L);
        assertThat(checkout1).isNotEqualTo(checkout2);
        checkout1.setId(null);
        assertThat(checkout1).isNotEqualTo(checkout2);
    }
}
