package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.TipoDeAlimento}.
 */
public interface TipoDeAlimentoService {
    /**
     * Save a tipoDeAlimento.
     *
     * @param tipoDeAlimentoDTO the entity to save.
     * @return the persisted entity.
     */
    TipoDeAlimentoDTO save(TipoDeAlimentoDTO tipoDeAlimentoDTO);

    /**
     * Updates a tipoDeAlimento.
     *
     * @param tipoDeAlimentoDTO the entity to update.
     * @return the persisted entity.
     */
    TipoDeAlimentoDTO update(TipoDeAlimentoDTO tipoDeAlimentoDTO);

    /**
     * Partially updates a tipoDeAlimento.
     *
     * @param tipoDeAlimentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoDeAlimentoDTO> partialUpdate(TipoDeAlimentoDTO tipoDeAlimentoDTO);

    /**
     * Get all the tipoDeAlimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoDeAlimentoDTO> findAll(Pageable pageable);

    /**
     * Get all the tipoDeAlimentos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoDeAlimentoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tipoDeAlimento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoDeAlimentoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDeAlimento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
