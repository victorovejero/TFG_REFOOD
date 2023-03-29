package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.SocioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Socio}.
 */
public interface SocioService {
    /**
     * Save a socio.
     *
     * @param socioDTO the entity to save.
     * @return the persisted entity.
     */
    SocioDTO save(SocioDTO socioDTO);

    /**
     * Updates a socio.
     *
     * @param socioDTO the entity to update.
     * @return the persisted entity.
     */
    SocioDTO update(SocioDTO socioDTO);

    /**
     * Partially updates a socio.
     *
     * @param socioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SocioDTO> partialUpdate(SocioDTO socioDTO);

    /**
     * Get all the socios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SocioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" socio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocioDTO> findOne(Long id);

    /**
     * Delete the "id" socio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
