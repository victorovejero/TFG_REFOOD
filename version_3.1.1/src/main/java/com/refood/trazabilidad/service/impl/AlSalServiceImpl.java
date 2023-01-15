package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.AlSal;
import com.refood.trazabilidad.repository.AlSalRepository;
import com.refood.trazabilidad.service.AlSalService;
import com.refood.trazabilidad.service.dto.AlSalDTO;
import com.refood.trazabilidad.service.mapper.AlSalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AlSal}.
 */
@Service
@Transactional
public class AlSalServiceImpl implements AlSalService {

    private final Logger log = LoggerFactory.getLogger(AlSalServiceImpl.class);

    private final AlSalRepository alSalRepository;

    private final AlSalMapper alSalMapper;

    public AlSalServiceImpl(AlSalRepository alSalRepository, AlSalMapper alSalMapper) {
        this.alSalRepository = alSalRepository;
        this.alSalMapper = alSalMapper;
    }

    @Override
    public AlSalDTO save(AlSalDTO alSalDTO) {
        log.debug("Request to save AlSal : {}", alSalDTO);
        AlSal alSal = alSalMapper.toEntity(alSalDTO);
        alSal = alSalRepository.save(alSal);
        return alSalMapper.toDto(alSal);
    }

    @Override
    public AlSalDTO update(AlSalDTO alSalDTO) {
        log.debug("Request to update AlSal : {}", alSalDTO);
        AlSal alSal = alSalMapper.toEntity(alSalDTO);
        alSal = alSalRepository.save(alSal);
        return alSalMapper.toDto(alSal);
    }

    @Override
    public Optional<AlSalDTO> partialUpdate(AlSalDTO alSalDTO) {
        log.debug("Request to partially update AlSal : {}", alSalDTO);

        return alSalRepository
            .findById(alSalDTO.getId())
            .map(existingAlSal -> {
                alSalMapper.partialUpdate(existingAlSal, alSalDTO);

                return existingAlSal;
            })
            .map(alSalRepository::save)
            .map(alSalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlSalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlSals");
        return alSalRepository.findAll(pageable).map(alSalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlSalDTO> findOne(Long id) {
        log.debug("Request to get AlSal : {}", id);
        return alSalRepository.findById(id).map(alSalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlSal : {}", id);
        alSalRepository.deleteById(id);
    }
}
