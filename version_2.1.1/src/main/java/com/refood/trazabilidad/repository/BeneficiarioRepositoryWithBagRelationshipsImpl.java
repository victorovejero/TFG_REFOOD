package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Beneficiario;
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
public class BeneficiarioRepositoryWithBagRelationshipsImpl implements BeneficiarioRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Beneficiario> fetchBagRelationships(Optional<Beneficiario> beneficiario) {
        return beneficiario.map(this::fetchIntolerancias);
    }

    @Override
    public Page<Beneficiario> fetchBagRelationships(Page<Beneficiario> beneficiarios) {
        return new PageImpl<>(
            fetchBagRelationships(beneficiarios.getContent()),
            beneficiarios.getPageable(),
            beneficiarios.getTotalElements()
        );
    }

    @Override
    public List<Beneficiario> fetchBagRelationships(List<Beneficiario> beneficiarios) {
        return Optional.of(beneficiarios).map(this::fetchIntolerancias).orElse(Collections.emptyList());
    }

    Beneficiario fetchIntolerancias(Beneficiario result) {
        return entityManager
            .createQuery(
                "select beneficiario from Beneficiario beneficiario left join fetch beneficiario.intolerancias where beneficiario is :beneficiario",
                Beneficiario.class
            )
            .setParameter("beneficiario", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Beneficiario> fetchIntolerancias(List<Beneficiario> beneficiarios) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, beneficiarios.size()).forEach(index -> order.put(beneficiarios.get(index).getId(), index));
        List<Beneficiario> result = entityManager
            .createQuery(
                "select distinct beneficiario from Beneficiario beneficiario left join fetch beneficiario.intolerancias where beneficiario in :beneficiarios",
                Beneficiario.class
            )
            .setParameter("beneficiarios", beneficiarios)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
