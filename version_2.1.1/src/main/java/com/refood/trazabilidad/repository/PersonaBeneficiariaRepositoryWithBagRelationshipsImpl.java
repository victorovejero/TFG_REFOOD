package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.PersonaBeneficiaria;
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
public class PersonaBeneficiariaRepositoryWithBagRelationshipsImpl implements PersonaBeneficiariaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PersonaBeneficiaria> fetchBagRelationships(Optional<PersonaBeneficiaria> personaBeneficiaria) {
        return personaBeneficiaria.map(this::fetchIntolerancias);
    }

    @Override
    public Page<PersonaBeneficiaria> fetchBagRelationships(Page<PersonaBeneficiaria> personaBeneficiarias) {
        return new PageImpl<>(
            fetchBagRelationships(personaBeneficiarias.getContent()),
            personaBeneficiarias.getPageable(),
            personaBeneficiarias.getTotalElements()
        );
    }

    @Override
    public List<PersonaBeneficiaria> fetchBagRelationships(List<PersonaBeneficiaria> personaBeneficiarias) {
        return Optional.of(personaBeneficiarias).map(this::fetchIntolerancias).orElse(Collections.emptyList());
    }

    PersonaBeneficiaria fetchIntolerancias(PersonaBeneficiaria result) {
        return entityManager
            .createQuery(
                "select personaBeneficiaria from PersonaBeneficiaria personaBeneficiaria left join fetch personaBeneficiaria.intolerancias where personaBeneficiaria is :personaBeneficiaria",
                PersonaBeneficiaria.class
            )
            .setParameter("personaBeneficiaria", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<PersonaBeneficiaria> fetchIntolerancias(List<PersonaBeneficiaria> personaBeneficiarias) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, personaBeneficiarias.size()).forEach(index -> order.put(personaBeneficiarias.get(index).getId(), index));
        List<PersonaBeneficiaria> result = entityManager
            .createQuery(
                "select distinct personaBeneficiaria from PersonaBeneficiaria personaBeneficiaria left join fetch personaBeneficiaria.intolerancias where personaBeneficiaria in :personaBeneficiarias",
                PersonaBeneficiaria.class
            )
            .setParameter("personaBeneficiarias", personaBeneficiarias)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
