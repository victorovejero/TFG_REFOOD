package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Intolerancia}.
 */
public interface IntoleranciaService {
    /**
     * Save a intolerancia.
     *
     * @param intoleranciaDTO the entity to save.
     * @return the persisted entity.
     */
    IntoleranciaDTO save(IntoleranciaDTO intoleranciaDTO);

    /**
     * Updates a intolerancia.
     *
     * @param intoleranciaDTO the entity to update.
     * @return the persisted entity.
     */
    IntoleranciaDTO update(IntoleranciaDTO intoleranciaDTO);

    /**
     * Partially updates a intolerancia.
     *
     * @param intoleranciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntoleranciaDTO> partialUpdate(IntoleranciaDTO intoleranciaDTO);

    /**
     * Get all the intolerancias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntoleranciaDTO> findAll(Pageable pageable);

    /**
     * Get all the intolerancias with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntoleranciaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" intolerancia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntoleranciaDTO> findOne(Long id);

    /**
     * Delete the "id" intolerancia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
