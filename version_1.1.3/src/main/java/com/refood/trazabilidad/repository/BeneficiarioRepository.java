package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Beneficiario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beneficiario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {}
