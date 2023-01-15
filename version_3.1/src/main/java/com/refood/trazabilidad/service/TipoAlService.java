package com.refood.trazabilidad.service;

import java.util.List;
import com.refood.trazabilidad.domain.TipoAl;

import com.refood.trazabilidad.service.dto.TipoAlDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.TipoAl}.
 */
public interface TipoAlService {

    List<TipoAl> findAll();

    /**
     * Save a tipoAl.
     *
     * @param tipoAlDTO the entity to save.
     * @return the persisted entity.
     */
    TipoAlDTO save(TipoAlDTO tipoAlDTO);

    /**
     * Updates a tipoAl.
     *
     * @param tipoAlDTO the entity to update.
     * @return the persisted entity.
     */
    TipoAlDTO update(TipoAlDTO tipoAlDTO);

    /**
     * Partially updates a tipoAl.
     *
     * @param tipoAlDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoAlDTO> partialUpdate(TipoAlDTO tipoAlDTO);

    /**
     * Get all the tipoAls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoAlDTO> findAll(Pageable pageable);

    /**
     * Get all the tipoAls with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoAlDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tipoAl.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoAlDTO> findOne(Long id);

    /**
     * Delete the "id" tipoAl.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
