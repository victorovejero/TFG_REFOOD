package com.refood.trazabilidad.service;
import java.util.List;
import com.refood.trazabilidad.domain.AlimentoDeEntrada;
import com.refood.trazabilidad.service.dto.AlimentoDeEntradaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.AlimentoDeEntrada}.
 */
public interface AlimentoDeEntradaService {

    List<AlimentoDeEntrada> findAll();

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
     * Get all the alimentoDeEntradas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlimentoDeEntradaDTO> findAllWithEagerRelationships(Pageable pageable);

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
