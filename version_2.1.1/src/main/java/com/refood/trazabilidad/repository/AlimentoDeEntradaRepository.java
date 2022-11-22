package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.AlimentoDeEntrada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AlimentoDeEntrada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlimentoDeEntradaRepository extends JpaRepository<AlimentoDeEntrada, Long> {}
