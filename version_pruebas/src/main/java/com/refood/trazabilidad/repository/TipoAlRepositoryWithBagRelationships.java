package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.TipoAl;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TipoAlRepositoryWithBagRelationships {
    Optional<TipoAl> fetchBagRelationships(Optional<TipoAl> tipoAl);

    List<TipoAl> fetchBagRelationships(List<TipoAl> tipoAls);

    Page<TipoAl> fetchBagRelationships(Page<TipoAl> tipoAls);
}
