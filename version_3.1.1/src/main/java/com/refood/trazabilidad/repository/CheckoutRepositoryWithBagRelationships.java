package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Checkout;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CheckoutRepositoryWithBagRelationships {
    Optional<Checkout> fetchBagRelationships(Optional<Checkout> checkout);

    List<Checkout> fetchBagRelationships(List<Checkout> checkouts);

    Page<Checkout> fetchBagRelationships(Page<Checkout> checkouts);
}
