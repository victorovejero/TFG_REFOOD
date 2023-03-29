package com.refood.trazabilidad.service;

import java.util.List;
import com.refood.trazabilidad.domain.Tupper;

import com.refood.trazabilidad.service.dto.TupperDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Tupper}.
 */
public interface TupperService {

    List<Tupper> findAll();

    /**
     * Save a tupper.
     *
     * @param tupperDTO the entity to save.
     * @return the persisted entity.
     */
    TupperDTO save(TupperDTO tupperDTO);

    /**
     * Updates a tupper.
     *
     * @param tupperDTO the entity to update.
     * @return the persisted entity.
     */
    TupperDTO update(TupperDTO tupperDTO);

    /**
     * Partially updates a tupper.
     *
     * @param tupperDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TupperDTO> partialUpdate(TupperDTO tupperDTO);

    /**
     * Get all the tuppers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TupperDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tupper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TupperDTO> findOne(Long id);

    /**
     * Delete the "id" tupper.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
