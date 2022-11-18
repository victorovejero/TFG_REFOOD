package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.TipoDeAlimento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TipoDeAlimentoRepositoryWithBagRelationships {
    Optional<TipoDeAlimento> fetchBagRelationships(Optional<TipoDeAlimento> tipoDeAlimento);

    List<TipoDeAlimento> fetchBagRelationships(List<TipoDeAlimento> tipoDeAlimentos);

    Page<TipoDeAlimento> fetchBagRelationships(Page<TipoDeAlimento> tipoDeAlimentos);
}
