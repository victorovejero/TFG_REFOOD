package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Nucleo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nucleo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NucleoRepository extends JpaRepository<Nucleo, Long> {}
