package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.PBenef;
import com.refood.trazabilidad.repository.PBenefRepository;
import com.refood.trazabilidad.service.PBenefService;
import com.refood.trazabilidad.service.dto.PBenefDTO;
import com.refood.trazabilidad.service.mapper.PBenefMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PBenef}.
 */
@Service
@Transactional
public class PBenefServiceImpl implements PBenefService {

    private final Logger log = LoggerFactory.getLogger(PBenefServiceImpl.class);

    private final PBenefRepository pBenefRepository;

    private final PBenefMapper pBenefMapper;

    public PBenefServiceImpl(PBenefRepository pBenefRepository, PBenefMapper pBenefMapper) {
        this.pBenefRepository = pBenefRepository;
        this.pBenefMapper = pBenefMapper;
    }

    @Override
    public PBenefDTO save(PBenefDTO pBenefDTO) {
        log.debug("Request to save PBenef : {}", pBenefDTO);
        PBenef pBenef = pBenefMapper.toEntity(pBenefDTO);
        pBenef = pBenefRepository.save(pBenef);
        return pBenefMapper.toDto(pBenef);
    }

    @Override
    public PBenefDTO update(PBenefDTO pBenefDTO) {
        log.debug("Request to update PBenef : {}", pBenefDTO);
        PBenef pBenef = pBenefMapper.toEntity(pBenefDTO);
        pBenef = pBenefRepository.save(pBenef);
        return pBenefMapper.toDto(pBenef);
    }

    @Override
    public Optional<PBenefDTO> partialUpdate(PBenefDTO pBenefDTO) {
        log.debug("Request to partially update PBenef : {}", pBenefDTO);

        return pBenefRepository
            .findById(pBenefDTO.getId())
            .map(existingPBenef -> {
                pBenefMapper.partialUpdate(existingPBenef, pBenefDTO);

                return existingPBenef;
            })
            .map(pBenefRepository::save)
            .map(pBenefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PBenefDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PBenefs");
        return pBenefRepository.findAll(pageable).map(pBenefMapper::toDto);
    }

    public Page<PBenefDTO> findAllWithEagerRelationships(Pageable pageable) {
        return pBenefRepository.findAllWithEagerRelationships(pageable).map(pBenefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PBenefDTO> findOne(Long id) {
        log.debug("Request to get PBenef : {}", id);
        return pBenefRepository.findOneWithEagerRelationships(id).map(pBenefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PBenef : {}", id);
        pBenefRepository.deleteById(id);
    }
}
