package com.refood.trazabilidad.service;

import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.refood.trazabilidad.domain.Beneficiario}.
 */
public interface BeneficiarioService {
    /**
     * Save a beneficiario.
     *
     * @param beneficiarioDTO the entity to save.
     * @return the persisted entity.
     */
    BeneficiarioDTO save(BeneficiarioDTO beneficiarioDTO);

    /**
     * Updates a beneficiario.
     *
     * @param beneficiarioDTO the entity to update.
     * @return the persisted entity.
     */
    BeneficiarioDTO update(BeneficiarioDTO beneficiarioDTO);

    /**
     * Partially updates a beneficiario.
     *
     * @param beneficiarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BeneficiarioDTO> partialUpdate(BeneficiarioDTO beneficiarioDTO);

    /**
     * Get all the beneficiarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BeneficiarioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" beneficiario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BeneficiarioDTO> findOne(Long id);

    /**
     * Delete the "id" beneficiario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
