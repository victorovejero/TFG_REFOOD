package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.PBenef;
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
public class PBenefRepositoryWithBagRelationshipsImpl implements PBenefRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PBenef> fetchBagRelationships(Optional<PBenef> pBenef) {
        return pBenef.map(this::fetchIntols);
    }

    @Override
    public Page<PBenef> fetchBagRelationships(Page<PBenef> pBenefs) {
        return new PageImpl<>(fetchBagRelationships(pBenefs.getContent()), pBenefs.getPageable(), pBenefs.getTotalElements());
    }

    @Override
    public List<PBenef> fetchBagRelationships(List<PBenef> pBenefs) {
        return Optional.of(pBenefs).map(this::fetchIntols).orElse(Collections.emptyList());
    }

    PBenef fetchIntols(PBenef result) {
        return entityManager
            .createQuery("select pBenef from PBenef pBenef left join fetch pBenef.intols where pBenef is :pBenef", PBenef.class)
            .setParameter("pBenef", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<PBenef> fetchIntols(List<PBenef> pBenefs) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, pBenefs.size()).forEach(index -> order.put(pBenefs.get(index).getId(), index));
        List<PBenef> result = entityManager
            .createQuery("select distinct pBenef from PBenef pBenef left join fetch pBenef.intols where pBenef in :pBenefs", PBenef.class)
            .setParameter("pBenefs", pBenefs)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
