package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.PersonaBeneficiariaDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.PersonaBeneficiaria}.
 */
public interface PersonaBeneficiariaService {
    /**
     * Save a personaBeneficiaria.
     *
     * @param personaBeneficiariaDTO the entity to save.
     * @return the persisted entity.
     */
    PersonaBeneficiariaDTO save(PersonaBeneficiariaDTO personaBeneficiariaDTO);

    /**
     * Updates a personaBeneficiaria.
     *
     * @param personaBeneficiariaDTO the entity to update.
     * @return the persisted entity.
     */
    PersonaBeneficiariaDTO update(PersonaBeneficiariaDTO personaBeneficiariaDTO);

    /**
     * Partially updates a personaBeneficiaria.
     *
     * @param personaBeneficiariaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonaBeneficiariaDTO> partialUpdate(PersonaBeneficiariaDTO personaBeneficiariaDTO);

    /**
     * Get all the personaBeneficiarias.
     *
     * @return the list of entities.
     */
    List<PersonaBeneficiariaDTO> findAll();

    /**
     * Get all the personaBeneficiarias with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonaBeneficiariaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" personaBeneficiaria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonaBeneficiariaDTO> findOne(Long id);

    /**
     * Delete the "id" personaBeneficiaria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
