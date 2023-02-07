package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.VoluntarioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Voluntario}.
 */
public interface VoluntarioService {
    /**
     * Save a voluntario.
     *
     * @param voluntarioDTO the entity to save.
     * @return the persisted entity.
     */
    VoluntarioDTO save(VoluntarioDTO voluntarioDTO);

    /**
     * Updates a voluntario.
     *
     * @param voluntarioDTO the entity to update.
     * @return the persisted entity.
     */
    VoluntarioDTO update(VoluntarioDTO voluntarioDTO);

    /**
     * Partially updates a voluntario.
     *
     * @param voluntarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VoluntarioDTO> partialUpdate(VoluntarioDTO voluntarioDTO);

    /**
     * Get all the voluntarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VoluntarioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" voluntario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoluntarioDTO> findOne(Long id);

    /**
     * Delete the "id" voluntario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
