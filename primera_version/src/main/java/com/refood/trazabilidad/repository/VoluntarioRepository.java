package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Voluntario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Voluntario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {}
