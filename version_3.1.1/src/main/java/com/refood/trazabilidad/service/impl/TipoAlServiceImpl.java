package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.TipoAl;
import com.refood.trazabilidad.repository.TipoAlRepository;
import com.refood.trazabilidad.service.TipoAlService;
import com.refood.trazabilidad.service.dto.TipoAlDTO;
import com.refood.trazabilidad.service.mapper.TipoAlMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoAl}.
 */
@Service
@Transactional
public class TipoAlServiceImpl implements TipoAlService {

    private final Logger log = LoggerFactory.getLogger(TipoAlServiceImpl.class);

    private final TipoAlRepository tipoAlRepository;

    private final TipoAlMapper tipoAlMapper;

    public TipoAlServiceImpl(TipoAlRepository tipoAlRepository, TipoAlMapper tipoAlMapper) {
        this.tipoAlRepository = tipoAlRepository;
        this.tipoAlMapper = tipoAlMapper;
    }

    @Override
    public TipoAlDTO save(TipoAlDTO tipoAlDTO) {
        log.debug("Request to save TipoAl : {}", tipoAlDTO);
        TipoAl tipoAl = tipoAlMapper.toEntity(tipoAlDTO);
        tipoAl = tipoAlRepository.save(tipoAl);
        return tipoAlMapper.toDto(tipoAl);
    }

    @Override
    public TipoAlDTO update(TipoAlDTO tipoAlDTO) {
        log.debug("Request to update TipoAl : {}", tipoAlDTO);
        TipoAl tipoAl = tipoAlMapper.toEntity(tipoAlDTO);
        tipoAl = tipoAlRepository.save(tipoAl);
        return tipoAlMapper.toDto(tipoAl);
    }

    @Override
    public Optional<TipoAlDTO> partialUpdate(TipoAlDTO tipoAlDTO) {
        log.debug("Request to partially update TipoAl : {}", tipoAlDTO);

        return tipoAlRepository
            .findById(tipoAlDTO.getId())
            .map(existingTipoAl -> {
                tipoAlMapper.partialUpdate(existingTipoAl, tipoAlDTO);

                return existingTipoAl;
            })
            .map(tipoAlRepository::save)
            .map(tipoAlMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoAlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoAls");
        return tipoAlRepository.findAll(pageable).map(tipoAlMapper::toDto);
    }

    public Page<TipoAlDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tipoAlRepository.findAllWithEagerRelationships(pageable).map(tipoAlMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoAlDTO> findOne(Long id) {
        log.debug("Request to get TipoAl : {}", id);
        return tipoAlRepository.findOneWithEagerRelationships(id).map(tipoAlMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoAl : {}", id);
        tipoAlRepository.deleteById(id);
    }
}
