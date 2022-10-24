package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.AlimentoDeSalida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AlimentoDeSalida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlimentoDeSalidaRepository extends JpaRepository<AlimentoDeSalida, Long> {}
