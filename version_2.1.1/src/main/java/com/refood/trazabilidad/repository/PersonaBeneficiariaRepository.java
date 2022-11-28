package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.PersonaBeneficiaria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonaBeneficiaria entity.
 *
 * When extending this class, extend PersonaBeneficiariaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PersonaBeneficiariaRepository
    extends PersonaBeneficiariaRepositoryWithBagRelationships, JpaRepository<PersonaBeneficiaria, Long> {
    default Optional<PersonaBeneficiaria> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<PersonaBeneficiaria> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<PersonaBeneficiaria> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
