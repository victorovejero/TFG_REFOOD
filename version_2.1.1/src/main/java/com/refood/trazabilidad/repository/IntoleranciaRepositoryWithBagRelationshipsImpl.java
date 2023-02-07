package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Intolerancia;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class IntoleranciaRepositoryWithBagRelationshipsImpl implements IntoleranciaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Intolerancia> fetchBagRelationships(Optional<Intolerancia> intolerancia) {
        return intolerancia.map(this::fetchBeneficiarios);
    }

    @Override
    public Page<Intolerancia> fetchBagRelationships(Page<Intolerancia> intolerancias) {
        return new PageImpl<>(
            fetchBagRelationships(intolerancias.getContent()),
            intolerancias.getPageable(),
            intolerancias.getTotalElements()
        );
    }

    @Override
    public List<Intolerancia> fetchBagRelationships(List<Intolerancia> intolerancias) {
        return Optional.of(intolerancias).map(this::fetchBeneficiarios).orElse(Collections.emptyList());
    }

    Intolerancia fetchBeneficiarios(Intolerancia result) {
        return entityManager
            .createQuery(
                "select intolerancia from Intolerancia intolerancia left join fetch intolerancia.beneficiarios where intolerancia is :intolerancia",
                Intolerancia.class
            )
            .setParameter("intolerancia", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Intolerancia> fetchBeneficiarios(List<Intolerancia> intolerancias) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, intolerancias.size()).forEach(index -> order.put(intolerancias.get(index).getId(), index));
        List<Intolerancia> result = entityManager
            .createQuery(
                "select distinct intolerancia from Intolerancia intolerancia left join fetch intolerancia.beneficiarios where intolerancia in :intolerancias",
                Intolerancia.class
            )
            .setParameter("intolerancias", intolerancias)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
