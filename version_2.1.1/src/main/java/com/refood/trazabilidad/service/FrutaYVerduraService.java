package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.FrutaYVerduraDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.FrutaYVerdura}.
 */
public interface FrutaYVerduraService {
    /**
     * Save a frutaYVerdura.
     *
     * @param frutaYVerduraDTO the entity to save.
     * @return the persisted entity.
     */
    FrutaYVerduraDTO save(FrutaYVerduraDTO frutaYVerduraDTO);

    /**
     * Updates a frutaYVerdura.
     *
     * @param frutaYVerduraDTO the entity to update.
     * @return the persisted entity.
     */
    FrutaYVerduraDTO update(FrutaYVerduraDTO frutaYVerduraDTO);

    /**
     * Partially updates a frutaYVerdura.
     *
     * @param frutaYVerduraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FrutaYVerduraDTO> partialUpdate(FrutaYVerduraDTO frutaYVerduraDTO);

    /**
     * Get all the frutaYVerduras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FrutaYVerduraDTO> findAll(Pageable pageable);

    /**
     * Get the "id" frutaYVerdura.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FrutaYVerduraDTO> findOne(Long id);

    /**
     * Delete the "id" frutaYVerdura.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
