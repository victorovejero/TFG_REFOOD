package com.refood.trazabilidad.service;

import java.util.List;
import com.refood.trazabilidad.domain.AlEnt;

import com.refood.trazabilidad.service.dto.AlEntDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.AlEnt}.
 */
public interface AlEntService {

    List<AlEnt> findAll();

    /**
     * Save a alEnt.
     *
     * @param alEntDTO the entity to save.
     * @return the persisted entity.
     */
    AlEntDTO save(AlEntDTO alEntDTO);

    /**
     * Updates a alEnt.
     *
     * @param alEntDTO the entity to update.
     * @return the persisted entity.
     */
    AlEntDTO update(AlEntDTO alEntDTO);

    /**
     * Partially updates a alEnt.
     *
     * @param alEntDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlEntDTO> partialUpdate(AlEntDTO alEntDTO);

    /**
     * Get all the alEnts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlEntDTO> findAll(Pageable pageable);

    /**
     * Get the "id" alEnt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlEntDTO> findOne(Long id);

    /**
     * Delete the "id" alEnt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
