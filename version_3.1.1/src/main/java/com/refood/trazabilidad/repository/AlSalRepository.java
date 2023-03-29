package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.AlSal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AlSal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlSalRepository extends JpaRepository<AlSal, Long> {}
