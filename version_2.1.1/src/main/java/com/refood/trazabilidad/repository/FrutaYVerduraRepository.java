package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.FrutaYVerdura;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FrutaYVerdura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FrutaYVerduraRepository extends JpaRepository<FrutaYVerdura, Long> {}
