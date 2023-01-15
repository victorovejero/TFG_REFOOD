package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.NucleoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Nucleo}.
 */
public interface NucleoService {
    /**
     * Save a nucleo.
     *
     * @param nucleoDTO the entity to save.
     * @return the persisted entity.
     */
    NucleoDTO save(NucleoDTO nucleoDTO);

    /**
     * Updates a nucleo.
     *
     * @param nucleoDTO the entity to update.
     * @return the persisted entity.
     */
    NucleoDTO update(NucleoDTO nucleoDTO);

    /**
     * Partially updates a nucleo.
     *
     * @param nucleoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NucleoDTO> partialUpdate(NucleoDTO nucleoDTO);

    /**
     * Get all the nucleos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NucleoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" nucleo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NucleoDTO> findOne(Long id);

    /**
     * Delete the "id" nucleo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
