package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.AlimentoDeEntrada;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AlimentoDeEntradaRepositoryWithBagRelationships {
    Optional<AlimentoDeEntrada> fetchBagRelationships(Optional<AlimentoDeEntrada> alimentoDeEntrada);

    List<AlimentoDeEntrada> fetchBagRelationships(List<AlimentoDeEntrada> alimentoDeEntradas);

    Page<AlimentoDeEntrada> fetchBagRelationships(Page<AlimentoDeEntrada> alimentoDeEntradas);
}
