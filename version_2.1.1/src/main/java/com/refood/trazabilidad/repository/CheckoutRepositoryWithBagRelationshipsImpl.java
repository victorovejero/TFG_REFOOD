package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Checkout;
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
public class CheckoutRepositoryWithBagRelationshipsImpl implements CheckoutRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Checkout> fetchBagRelationships(Optional<Checkout> checkout) {
        return checkout.map(this::fetchAlimentoDeSalidas);
    }

    @Override
    public Page<Checkout> fetchBagRelationships(Page<Checkout> checkouts) {
        return new PageImpl<>(fetchBagRelationships(checkouts.getContent()), checkouts.getPageable(), checkouts.getTotalElements());
    }

    @Override
    public List<Checkout> fetchBagRelationships(List<Checkout> checkouts) {
        return Optional.of(checkouts).map(this::fetchAlimentoDeSalidas).orElse(Collections.emptyList());
    }

    Checkout fetchAlimentoDeSalidas(Checkout result) {
        return entityManager
            .createQuery(
                "select checkout from Checkout checkout left join fetch checkout.alimentoDeSalidas where checkout is :checkout",
                Checkout.class
            )
            .setParameter("checkout", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Checkout> fetchAlimentoDeSalidas(List<Checkout> checkouts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, checkouts.size()).forEach(index -> order.put(checkouts.get(index).getId(), index));
        List<Checkout> result = entityManager
            .createQuery(
                "select distinct checkout from Checkout checkout left join fetch checkout.alimentoDeSalidas where checkout in :checkouts",
                Checkout.class
            )
            .setParameter("checkouts", checkouts)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
