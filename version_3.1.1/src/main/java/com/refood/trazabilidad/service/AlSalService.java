package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.AlSalDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.AlSal}.
 */
public interface AlSalService {
    /**
     * Save a alSal.
     *
     * @param alSalDTO the entity to save.
     * @return the persisted entity.
     */
    AlSalDTO save(AlSalDTO alSalDTO);

    /**
     * Updates a alSal.
     *
     * @param alSalDTO the entity to update.
     * @return the persisted entity.
     */
    AlSalDTO update(AlSalDTO alSalDTO);

    /**
     * Partially updates a alSal.
     *
     * @param alSalDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlSalDTO> partialUpdate(AlSalDTO alSalDTO);

    /**
     * Get all the alSals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlSalDTO> findAll(Pageable pageable);

    /**
     * Get the "id" alSal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlSalDTO> findOne(Long id);

    /**
     * Delete the "id" alSal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
