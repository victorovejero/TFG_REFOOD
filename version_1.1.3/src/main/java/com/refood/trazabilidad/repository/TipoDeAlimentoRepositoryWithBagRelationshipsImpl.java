package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.TipoDeAlimento;
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
public class TipoDeAlimentoRepositoryWithBagRelationshipsImpl implements TipoDeAlimentoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TipoDeAlimento> fetchBagRelationships(Optional<TipoDeAlimento> tipoDeAlimento) {
        return tipoDeAlimento.map(this::fetchIntolerancias);
    }

    @Override
    public Page<TipoDeAlimento> fetchBagRelationships(Page<TipoDeAlimento> tipoDeAlimentos) {
        return new PageImpl<>(
            fetchBagRelationships(tipoDeAlimentos.getContent()),
            tipoDeAlimentos.getPageable(),
            tipoDeAlimentos.getTotalElements()
        );
    }

    @Override
    public List<TipoDeAlimento> fetchBagRelationships(List<TipoDeAlimento> tipoDeAlimentos) {
        return Optional.of(tipoDeAlimentos).map(this::fetchIntolerancias).orElse(Collections.emptyList());
    }

    TipoDeAlimento fetchIntolerancias(TipoDeAlimento result) {
        return entityManager
            .createQuery(
                "select tipoDeAlimento from TipoDeAlimento tipoDeAlimento left join fetch tipoDeAlimento.intolerancias where tipoDeAlimento is :tipoDeAlimento",
                TipoDeAlimento.class
            )
            .setParameter("tipoDeAlimento", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<TipoDeAlimento> fetchIntolerancias(List<TipoDeAlimento> tipoDeAlimentos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tipoDeAlimentos.size()).forEach(index -> order.put(tipoDeAlimentos.get(index).getId(), index));
        List<TipoDeAlimento> result = entityManager
            .createQuery(
                "select distinct tipoDeAlimento from TipoDeAlimento tipoDeAlimento left join fetch tipoDeAlimento.intolerancias where tipoDeAlimento in :tipoDeAlimentos",
                TipoDeAlimento.class
            )
            .setParameter("tipoDeAlimentos", tipoDeAlimentos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
