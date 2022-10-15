package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NucleoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NucleoDTO.class);
        NucleoDTO nucleoDTO1 = new NucleoDTO();
        nucleoDTO1.setId(1L);
        NucleoDTO nucleoDTO2 = new NucleoDTO();
        assertThat(nucleoDTO1).isNotEqualTo(nucleoDTO2);
        nucleoDTO2.setId(nucleoDTO1.getId());
        assertThat(nucleoDTO1).isEqualTo(nucleoDTO2);
        nucleoDTO2.setId(2L);
        assertThat(nucleoDTO1).isNotEqualTo(nucleoDTO2);
        nucleoDTO1.setId(null);
        assertThat(nucleoDTO1).isNotEqualTo(nucleoDTO2);
    }
}
