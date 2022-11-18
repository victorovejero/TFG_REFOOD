package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.AlimentoDeEntradaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.AlimentoDeEntrada}.
 */
public interface AlimentoDeEntradaService {
    /**
     * Save a alimentoDeEntrada.
     *
     * @param alimentoDeEntradaDTO the entity to save.
     * @return the persisted entity.
     */
    AlimentoDeEntradaDTO save(AlimentoDeEntradaDTO alimentoDeEntradaDTO);

    /**
     * Updates a alimentoDeEntrada.
     *
     * @param alimentoDeEntradaDTO the entity to update.
     * @return the persisted entity.
     */
    AlimentoDeEntradaDTO update(AlimentoDeEntradaDTO alimentoDeEntradaDTO);

    /**
     * Partially updates a alimentoDeEntrada.
     *
     * @param alimentoDeEntradaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlimentoDeEntradaDTO> partialUpdate(AlimentoDeEntradaDTO alimentoDeEntradaDTO);

    /**
     * Get all the alimentoDeEntradas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlimentoDeEntradaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" alimentoDeEntrada.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlimentoDeEntradaDTO> findOne(Long id);

    /**
     * Delete the "id" alimentoDeEntrada.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}