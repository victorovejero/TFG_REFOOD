package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Benef;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface BenefRepositoryWithBagRelationships {
    Optional<Benef> fetchBagRelationships(Optional<Benef> benef);

    List<Benef> fetchBagRelationships(List<Benef> benefs);

    Page<Benef> fetchBagRelationships(Page<Benef> benefs);
}
