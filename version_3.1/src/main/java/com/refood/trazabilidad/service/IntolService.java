package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.IntolDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Intol}.
 */
public interface IntolService {
    /**
     * Save a intol.
     *
     * @param intolDTO the entity to save.
     * @return the persisted entity.
     */
    IntolDTO save(IntolDTO intolDTO);

    /**
     * Updates a intol.
     *
     * @param intolDTO the entity to update.
     * @return the persisted entity.
     */
    IntolDTO update(IntolDTO intolDTO);

    /**
     * Partially updates a intol.
     *
     * @param intolDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntolDTO> partialUpdate(IntolDTO intolDTO);

    /**
     * Get all the intols.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntolDTO> findAll(Pageable pageable);

    /**
     * Get the "id" intol.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntolDTO> findOne(Long id);

    /**
     * Delete the "id" intol.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
