package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.RegistroDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Registro}.
 */
public interface RegistroService {
    /**
     * Save a registro.
     *
     * @param registroDTO the entity to save.
     * @return the persisted entity.
     */
    RegistroDTO save(RegistroDTO registroDTO);

    /**
     * Updates a registro.
     *
     * @param registroDTO the entity to update.
     * @return the persisted entity.
     */
    RegistroDTO update(RegistroDTO registroDTO);

    /**
     * Partially updates a registro.
     *
     * @param registroDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegistroDTO> partialUpdate(RegistroDTO registroDTO);

    /**
     * Get all the registros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegistroDTO> findAll(Pageable pageable);

    /**
     * Get all the registros with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegistroDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" registro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegistroDTO> findOne(Long id);

    /**
     * Delete the "id" registro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
