package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.PersonaBeneficiaria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PersonaBeneficiariaRepositoryWithBagRelationships {
    Optional<PersonaBeneficiaria> fetchBagRelationships(Optional<PersonaBeneficiaria> personaBeneficiaria);

    List<PersonaBeneficiaria> fetchBagRelationships(List<PersonaBeneficiaria> personaBeneficiarias);

    Page<PersonaBeneficiaria> fetchBagRelationships(Page<PersonaBeneficiaria> personaBeneficiarias);
}
