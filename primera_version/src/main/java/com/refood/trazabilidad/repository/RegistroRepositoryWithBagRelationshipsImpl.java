package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Registro;
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
public class RegistroRepositoryWithBagRelationshipsImpl implements RegistroRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Registro> fetchBagRelationships(Optional<Registro> registro) {
        return registro.map(this::fetchVoluntarios);
    }

    @Override
    public Page<Registro> fetchBagRelationships(Page<Registro> registros) {
        return new PageImpl<>(fetchBagRelationships(registros.getContent()), registros.getPageable(), registros.getTotalElements());
    }

    @Override
    public List<Registro> fetchBagRelationships(List<Registro> registros) {
        return Optional.of(registros).map(this::fetchVoluntarios).orElse(Collections.emptyList());
    }

    Registro fetchVoluntarios(Registro result) {
        return entityManager
            .createQuery(
                "select registro from Registro registro left join fetch registro.voluntarios where registro is :registro",
                Registro.class
            )
            .setParameter("registro", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Registro> fetchVoluntarios(List<Registro> registros) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, registros.size()).forEach(index -> order.put(registros.get(index).getId(), index));
        List<Registro> result = entityManager
            .createQuery(
                "select distinct registro from Registro registro left join fetch registro.voluntarios where registro in :registros",
                Registro.class
            )
            .setParameter("registros", registros)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
