package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.TipoDeAlimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoDeAlimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDeAlimentoRepository extends JpaRepository<TipoDeAlimento, Long> {}
