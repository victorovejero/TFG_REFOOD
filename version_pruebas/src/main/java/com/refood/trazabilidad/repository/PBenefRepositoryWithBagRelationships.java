package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.PBenef;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PBenefRepositoryWithBagRelationships {
    Optional<PBenef> fetchBagRelationships(Optional<PBenef> pBenef);

    List<PBenef> fetchBagRelationships(List<PBenef> pBenefs);

    Page<PBenef> fetchBagRelationships(Page<PBenef> pBenefs);
}
