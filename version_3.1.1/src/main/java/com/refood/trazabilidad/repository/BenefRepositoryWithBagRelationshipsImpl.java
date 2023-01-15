package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Benef;
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
public class BenefRepositoryWithBagRelationshipsImpl implements BenefRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Benef> fetchBagRelationships(Optional<Benef> benef) {
        return benef.map(this::fetchIntols);
    }

    @Override
    public Page<Benef> fetchBagRelationships(Page<Benef> benefs) {
        return new PageImpl<>(fetchBagRelationships(benefs.getContent()), benefs.getPageable(), benefs.getTotalElements());
    }

    @Override
    public List<Benef> fetchBagRelationships(List<Benef> benefs) {
        return Optional.of(benefs).map(this::fetchIntols).orElse(Collections.emptyList());
    }

    Benef fetchIntols(Benef result) {
        return entityManager
            .createQuery("select benef from Benef benef left join fetch benef.intols where benef is :benef", Benef.class)
            .setParameter("benef", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Benef> fetchIntols(List<Benef> benefs) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, benefs.size()).forEach(index -> order.put(benefs.get(index).getId(), index));
        List<Benef> result = entityManager
            .createQuery("select distinct benef from Benef benef left join fetch benef.intols where benef in :benefs", Benef.class)
            .setParameter("benefs", benefs)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
