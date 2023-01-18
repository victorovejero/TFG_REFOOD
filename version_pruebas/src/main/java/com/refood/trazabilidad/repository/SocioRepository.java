package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Socio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Socio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {}
