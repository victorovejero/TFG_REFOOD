package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Donante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Donante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonanteRepository extends JpaRepository<Donante, Long> {}
