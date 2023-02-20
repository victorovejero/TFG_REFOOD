package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckoutDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckoutDTO.class);
        CheckoutDTO checkoutDTO1 = new CheckoutDTO();
        checkoutDTO1.setId(1L);
        CheckoutDTO checkoutDTO2 = new CheckoutDTO();
        assertThat(checkoutDTO1).isNotEqualTo(checkoutDTO2);
        checkoutDTO2.setId(checkoutDTO1.getId());
        assertThat(checkoutDTO1).isEqualTo(checkoutDTO2);
        checkoutDTO2.setId(2L);
        assertThat(checkoutDTO1).isNotEqualTo(checkoutDTO2);
        checkoutDTO1.setId(null);
        assertThat(checkoutDTO1).isNotEqualTo(checkoutDTO2);
    }
}
