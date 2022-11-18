package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.TipoDeAlimento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoDeAlimento entity.
 *
 * When extending this class, extend TipoDeAlimentoRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface TipoDeAlimentoRepository extends TipoDeAlimentoRepositoryWithBagRelationships, JpaRepository<TipoDeAlimento, Long> {
    default Optional<TipoDeAlimento> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<TipoDeAlimento> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<TipoDeAlimento> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
