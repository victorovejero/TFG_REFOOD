package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlimentoDeSalidaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlimentoDeSalidaDTO.class);
        AlimentoDeSalidaDTO alimentoDeSalidaDTO1 = new AlimentoDeSalidaDTO();
        alimentoDeSalidaDTO1.setId(1L);
        AlimentoDeSalidaDTO alimentoDeSalidaDTO2 = new AlimentoDeSalidaDTO();
        assertThat(alimentoDeSalidaDTO1).isNotEqualTo(alimentoDeSalidaDTO2);
        alimentoDeSalidaDTO2.setId(alimentoDeSalidaDTO1.getId());
        assertThat(alimentoDeSalidaDTO1).isEqualTo(alimentoDeSalidaDTO2);
        alimentoDeSalidaDTO2.setId(2L);
        assertThat(alimentoDeSalidaDTO1).isNotEqualTo(alimentoDeSalidaDTO2);
        alimentoDeSalidaDTO1.setId(null);
        assertThat(alimentoDeSalidaDTO1).isNotEqualTo(alimentoDeSalidaDTO2);
    }
}
