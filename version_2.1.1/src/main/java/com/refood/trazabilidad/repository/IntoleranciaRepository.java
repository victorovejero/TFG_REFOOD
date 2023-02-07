package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Intolerancia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Intolerancia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntoleranciaRepository extends JpaRepository<Intolerancia, Long> {}
