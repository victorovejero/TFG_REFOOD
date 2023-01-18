package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.AlEnt;
import com.refood.trazabilidad.repository.AlEntRepository;
import com.refood.trazabilidad.service.AlEntService;
import com.refood.trazabilidad.service.dto.AlEntDTO;
import com.refood.trazabilidad.service.mapper.AlEntMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AlEnt}.
 */
@Service
@Transactional
public class AlEntServiceImpl implements AlEntService {

    private final Logger log = LoggerFactory.getLogger(AlEntServiceImpl.class);

    private final AlEntRepository alEntRepository;

    private final AlEntMapper alEntMapper;

    public AlEntServiceImpl(AlEntRepository alEntRepository, AlEntMapper alEntMapper) {
        this.alEntRepository = alEntRepository;
        this.alEntMapper = alEntMapper;
    }

    @Override
    public AlEntDTO save(AlEntDTO alEntDTO) {
        log.debug("Request to save AlEnt : {}", alEntDTO);
        AlEnt alEnt = alEntMapper.toEntity(alEntDTO);
        alEnt = alEntRepository.save(alEnt);
        return alEntMapper.toDto(alEnt);
    }

    @Override
    public AlEntDTO update(AlEntDTO alEntDTO) {
        log.debug("Request to update AlEnt : {}", alEntDTO);
        AlEnt alEnt = alEntMapper.toEntity(alEntDTO);
        alEnt = alEntRepository.save(alEnt);
        return alEntMapper.toDto(alEnt);
    }

    @Override
    public Optional<AlEntDTO> partialUpdate(AlEntDTO alEntDTO) {
        log.debug("Request to partially update AlEnt : {}", alEntDTO);

        return alEntRepository
            .findById(alEntDTO.getId())
            .map(existingAlEnt -> {
                alEntMapper.partialUpdate(existingAlEnt, alEntDTO);

                return existingAlEnt;
            })
            .map(alEntRepository::save)
            .map(alEntMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlEntDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlEnts");
        return alEntRepository.findAll(pageable).map(alEntMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlEntDTO> findOne(Long id) {
        log.debug("Request to get AlEnt : {}", id);
        return alEntRepository.findById(id).map(alEntMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlEnt : {}", id);
        alEntRepository.deleteById(id);
    }
}
