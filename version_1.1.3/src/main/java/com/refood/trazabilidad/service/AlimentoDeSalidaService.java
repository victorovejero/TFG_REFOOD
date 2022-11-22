package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.AlimentoDeSalidaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.AlimentoDeSalida}.
 */
public interface AlimentoDeSalidaService {
    /**
     * Save a alimentoDeSalida.
     *
     * @param alimentoDeSalidaDTO the entity to save.
     * @return the persisted entity.
     */
    AlimentoDeSalidaDTO save(AlimentoDeSalidaDTO alimentoDeSalidaDTO);

    /**
     * Updates a alimentoDeSalida.
     *
     * @param alimentoDeSalidaDTO the entity to update.
     * @return the persisted entity.
     */
    AlimentoDeSalidaDTO update(AlimentoDeSalidaDTO alimentoDeSalidaDTO);

    /**
     * Partially updates a alimentoDeSalida.
     *
     * @param alimentoDeSalidaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlimentoDeSalidaDTO> partialUpdate(AlimentoDeSalidaDTO alimentoDeSalidaDTO);

    /**
     * Get all the alimentoDeSalidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlimentoDeSalidaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" alimentoDeSalida.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlimentoDeSalidaDTO> findOne(Long id);

    /**
     * Delete the "id" alimentoDeSalida.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
