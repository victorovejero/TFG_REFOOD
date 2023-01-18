package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.AlEnt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AlEnt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlEntRepository extends JpaRepository<AlEnt, Long> {}
