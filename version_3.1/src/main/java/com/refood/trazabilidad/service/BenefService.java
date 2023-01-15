package com.refood.trazabilidad.service;

import java.util.List;
import com.refood.trazabilidad.domain.Benef;

import com.refood.trazabilidad.service.dto.BenefDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Benef}.
 */
public interface BenefService {

    List<Benef> findAll();

    /**
     * Save a benef.
     *
     * @param benefDTO the entity to save.
     * @return the persisted entity.
     */
    BenefDTO save(BenefDTO benefDTO);

    /**
     * Updates a benef.
     *
     * @param benefDTO the entity to update.
     * @return the persisted entity.
     */
    BenefDTO update(BenefDTO benefDTO);

    /**
     * Partially updates a benef.
     *
     * @param benefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BenefDTO> partialUpdate(BenefDTO benefDTO);

    /**
     * Get all the benefs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BenefDTO> findAll(Pageable pageable);

    /**
     * Get all the benefs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BenefDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" benef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BenefDTO> findOne(Long id);

    /**
     * Delete the "id" benef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
