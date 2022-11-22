package com.refood.trazabilidad.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.refood.trazabilidad.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlimentoDeEntradaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlimentoDeEntrada.class);
        AlimentoDeEntrada alimentoDeEntrada1 = new AlimentoDeEntrada();
        alimentoDeEntrada1.setId(1L);
        AlimentoDeEntrada alimentoDeEntrada2 = new AlimentoDeEntrada();
        alimentoDeEntrada2.setId(alimentoDeEntrada1.getId());
        assertThat(alimentoDeEntrada1).isEqualTo(alimentoDeEntrada2);
        alimentoDeEntrada2.setId(2L);
        assertThat(alimentoDeEntrada1).isNotEqualTo(alimentoDeEntrada2);
        alimentoDeEntrada1.setId(null);
        assertThat(alimentoDeEntrada1).isNotEqualTo(alimentoDeEntrada2);
    }
}
