package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.PBenefDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.PBenef}.
 */
public interface PBenefService {
    /**
     * Save a pBenef.
     *
     * @param pBenefDTO the entity to save.
     * @return the persisted entity.
     */
    PBenefDTO save(PBenefDTO pBenefDTO);

    /**
     * Updates a pBenef.
     *
     * @param pBenefDTO the entity to update.
     * @return the persisted entity.
     */
    PBenefDTO update(PBenefDTO pBenefDTO);

    /**
     * Partially updates a pBenef.
     *
     * @param pBenefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PBenefDTO> partialUpdate(PBenefDTO pBenefDTO);

    /**
     * Get all the pBenefs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PBenefDTO> findAll(Pageable pageable);

    /**
     * Get all the pBenefs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PBenefDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" pBenef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PBenefDTO> findOne(Long id);

    /**
     * Delete the "id" pBenef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
