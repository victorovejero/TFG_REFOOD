package com.refood.trazabilidad.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TupperDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TupperDTO.class);
        TupperDTO tupperDTO1 = new TupperDTO();
        tupperDTO1.setId(1L);
        TupperDTO tupperDTO2 = new TupperDTO();
        assertThat(tupperDTO1).isNotEqualTo(tupperDTO2);
        tupperDTO2.setId(tupperDTO1.getId());
        assertThat(tupperDTO1).isEqualTo(tupperDTO2);
        tupperDTO2.setId(2L);
        assertThat(tupperDTO1).isNotEqualTo(tupperDTO2);
        tupperDTO1.setId(null);
        assertThat(tupperDTO1).isNotEqualTo(tupperDTO2);
    }
}
