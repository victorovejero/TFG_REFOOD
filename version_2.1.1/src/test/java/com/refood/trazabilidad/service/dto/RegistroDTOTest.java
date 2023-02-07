package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegistroDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroDTO.class);
        RegistroDTO registroDTO1 = new RegistroDTO();
        registroDTO1.setId(1L);
        RegistroDTO registroDTO2 = new RegistroDTO();
        assertThat(registroDTO1).isNotEqualTo(registroDTO2);
        registroDTO2.setId(registroDTO1.getId());
        assertThat(registroDTO1).isEqualTo(registroDTO2);
        registroDTO2.setId(2L);
        assertThat(registroDTO1).isNotEqualTo(registroDTO2);
        registroDTO1.setId(null);
        assertThat(registroDTO1).isNotEqualTo(registroDTO2);
    }
}
