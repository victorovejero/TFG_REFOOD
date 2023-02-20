package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntolDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntolDTO.class);
        IntolDTO intolDTO1 = new IntolDTO();
        intolDTO1.setId(1L);
        IntolDTO intolDTO2 = new IntolDTO();
        assertThat(intolDTO1).isNotEqualTo(intolDTO2);
        intolDTO2.setId(intolDTO1.getId());
        assertThat(intolDTO1).isEqualTo(intolDTO2);
        intolDTO2.setId(2L);
        assertThat(intolDTO1).isNotEqualTo(intolDTO2);
        intolDTO1.setId(null);
        assertThat(intolDTO1).isNotEqualTo(intolDTO2);
    }
}
