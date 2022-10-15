package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Intolerancia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IntoleranciaRepositoryWithBagRelationships {
    Optional<Intolerancia> fetchBagRelationships(Optional<Intolerancia> intolerancia);

    List<Intolerancia> fetchBagRelationships(List<Intolerancia> intolerancias);

    Page<Intolerancia> fetchBagRelationships(Page<Intolerancia> intolerancias);
}
