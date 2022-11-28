package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.AlimentoDeEntrada;
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
public class AlimentoDeEntradaRepositoryWithBagRelationshipsImpl implements AlimentoDeEntradaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AlimentoDeEntrada> fetchBagRelationships(Optional<AlimentoDeEntrada> alimentoDeEntrada) {
        return alimentoDeEntrada.map(this::fetchFrutaYVerduras);
    }

    @Override
    public Page<AlimentoDeEntrada> fetchBagRelationships(Page<AlimentoDeEntrada> alimentoDeEntradas) {
        return new PageImpl<>(
            fetchBagRelationships(alimentoDeEntradas.getContent()),
            alimentoDeEntradas.getPageable(),
            alimentoDeEntradas.getTotalElements()
        );
    }

    @Override
    public List<AlimentoDeEntrada> fetchBagRelationships(List<AlimentoDeEntrada> alimentoDeEntradas) {
        return Optional.of(alimentoDeEntradas).map(this::fetchFrutaYVerduras).orElse(Collections.emptyList());
    }

    AlimentoDeEntrada fetchFrutaYVerduras(AlimentoDeEntrada result) {
        return entityManager
            .createQuery(
                "select alimentoDeEntrada from AlimentoDeEntrada alimentoDeEntrada left join fetch alimentoDeEntrada.frutaYVerduras where alimentoDeEntrada is :alimentoDeEntrada",
                AlimentoDeEntrada.class
            )
            .setParameter("alimentoDeEntrada", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<AlimentoDeEntrada> fetchFrutaYVerduras(List<AlimentoDeEntrada> alimentoDeEntradas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, alimentoDeEntradas.size()).forEach(index -> order.put(alimentoDeEntradas.get(index).getId(), index));
        List<AlimentoDeEntrada> result = entityManager
            .createQuery(
                "select distinct alimentoDeEntrada from AlimentoDeEntrada alimentoDeEntrada left join fetch alimentoDeEntrada.frutaYVerduras where alimentoDeEntrada in :alimentoDeEntradas",
                AlimentoDeEntrada.class
            )
            .setParameter("alimentoDeEntradas", alimentoDeEntradas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
