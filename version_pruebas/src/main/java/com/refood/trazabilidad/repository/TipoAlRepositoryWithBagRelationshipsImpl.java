package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.TipoAl;
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
public class TipoAlRepositoryWithBagRelationshipsImpl implements TipoAlRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TipoAl> fetchBagRelationships(Optional<TipoAl> tipoAl) {
        return tipoAl.map(this::fetchIntols);
    }

    @Override
    public Page<TipoAl> fetchBagRelationships(Page<TipoAl> tipoAls) {
        return new PageImpl<>(fetchBagRelationships(tipoAls.getContent()), tipoAls.getPageable(), tipoAls.getTotalElements());
    }

    @Override
    public List<TipoAl> fetchBagRelationships(List<TipoAl> tipoAls) {
        return Optional.of(tipoAls).map(this::fetchIntols).orElse(Collections.emptyList());
    }

    TipoAl fetchIntols(TipoAl result) {
        return entityManager
            .createQuery("select tipoAl from TipoAl tipoAl left join fetch tipoAl.intols where tipoAl is :tipoAl", TipoAl.class)
            .setParameter("tipoAl", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<TipoAl> fetchIntols(List<TipoAl> tipoAls) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tipoAls.size()).forEach(index -> order.put(tipoAls.get(index).getId(), index));
        List<TipoAl> result = entityManager
            .createQuery("select distinct tipoAl from TipoAl tipoAl left join fetch tipoAl.intols where tipoAl in :tipoAls", TipoAl.class)
            .setParameter("tipoAls", tipoAls)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
