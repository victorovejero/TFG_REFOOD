package com.refood.trazabilidad.repository;

import com.refood.trazabilidad.domain.Registro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface RegistroRepositoryWithBagRelationships {
    Optional<Registro> fetchBagRelationships(Optional<Registro> registro);

    List<Registro> fetchBagRelationships(List<Registro> registros);

    Page<Registro> fetchBagRelationships(Page<Registro> registros);
}
