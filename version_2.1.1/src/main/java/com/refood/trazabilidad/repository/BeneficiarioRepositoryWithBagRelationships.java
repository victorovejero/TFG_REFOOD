package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Beneficiario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface BeneficiarioRepositoryWithBagRelationships {
    Optional<Beneficiario> fetchBagRelationships(Optional<Beneficiario> beneficiario);

    List<Beneficiario> fetchBagRelationships(List<Beneficiario> beneficiarios);

    Page<Beneficiario> fetchBagRelationships(Page<Beneficiario> beneficiarios);
}
