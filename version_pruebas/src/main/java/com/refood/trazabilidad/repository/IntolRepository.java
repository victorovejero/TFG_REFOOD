package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Intol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Intol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntolRepository extends JpaRepository<Intol, Long> {}
