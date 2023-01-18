package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.Intol;
import com.refood.trazabilidad.repository.IntolRepository;
import com.refood.trazabilidad.service.IntolService;
import com.refood.trazabilidad.service.dto.IntolDTO;
import com.refood.trazabilidad.service.mapper.IntolMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Intol}.
 */
@Service
@Transactional
public class IntolServiceImpl implements IntolService {

    private final Logger log = LoggerFactory.getLogger(IntolServiceImpl.class);

    private final IntolRepository intolRepository;

    private final IntolMapper intolMapper;

    public IntolServiceImpl(IntolRepository intolRepository, IntolMapper intolMapper) {
        this.intolRepository = intolRepository;
        this.intolMapper = intolMapper;
    }

    @Override
    public IntolDTO save(IntolDTO intolDTO) {
        log.debug("Request to save Intol : {}", intolDTO);
        Intol intol = intolMapper.toEntity(intolDTO);
        intol = intolRepository.save(intol);
        return intolMapper.toDto(intol);
    }

    @Override
    public IntolDTO update(IntolDTO intolDTO) {
        log.debug("Request to update Intol : {}", intolDTO);
        Intol intol = intolMapper.toEntity(intolDTO);
        intol = intolRepository.save(intol);
        return intolMapper.toDto(intol);
    }

    @Override
    public Optional<IntolDTO> partialUpdate(IntolDTO intolDTO) {
        log.debug("Request to partially update Intol : {}", intolDTO);

        return intolRepository
            .findById(intolDTO.getId())
            .map(existingIntol -> {
                intolMapper.partialUpdate(existingIntol, intolDTO);

                return existingIntol;
            })
            .map(intolRepository::save)
            .map(intolMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntolDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Intols");
        return intolRepository.findAll(pageable).map(intolMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntolDTO> findOne(Long id) {
        log.debug("Request to get Intol : {}", id);
        return intolRepository.findById(id).map(intolMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intol : {}", id);
        intolRepository.deleteById(id);
    }
}
