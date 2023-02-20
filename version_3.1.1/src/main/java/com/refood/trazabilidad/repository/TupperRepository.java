package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Tupper;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tupper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TupperRepository extends JpaRepository<Tupper, Long> {}
