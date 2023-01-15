package com.refood.trazabilidad.service;

import java.util.List;
import com.refood.trazabilidad.domain.Donante;

import com.refood.trazabilidad.service.dto.DonanteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing
 * {@link com.refood.trazabilidad.domain.Donante}.
 */
public interface DonanteService {

    List<Donante> findAll();

    /**
     * Save a donante.
     *
     * @param donanteDTO the entity to save.
     * @return the persisted entity.
     */
    DonanteDTO save(DonanteDTO donanteDTO);

    /**
     * Updates a donante.
     *
     * @param donanteDTO the entity to update.
     * @return the persisted entity.
     */
    DonanteDTO update(DonanteDTO donanteDTO);

    /**
     * Partially updates a donante.
     *
     * @param donanteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonanteDTO> partialUpdate(DonanteDTO donanteDTO);

    /**
     * Get all the donantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonanteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" donante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonanteDTO> findOne(Long id);

    /**
     * Delete the "id" donante.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
