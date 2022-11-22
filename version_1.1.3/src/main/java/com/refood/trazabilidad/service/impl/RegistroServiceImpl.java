package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.Registro;
import com.refood.trazabilidad.repository.RegistroRepository;
import com.refood.trazabilidad.service.RegistroService;
import com.refood.trazabilidad.service.dto.RegistroDTO;
import com.refood.trazabilidad.service.mapper.RegistroMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Registro}.
 */
@Service
@Transactional
public class RegistroServiceImpl implements RegistroService {

    private final Logger log = LoggerFactory.getLogger(RegistroServiceImpl.class);

    private final RegistroRepository registroRepository;

    private final RegistroMapper registroMapper;

    public RegistroServiceImpl(RegistroRepository registroRepository, RegistroMapper registroMapper) {
        this.registroRepository = registroRepository;
        this.registroMapper = registroMapper;
    }

    @Override
    public RegistroDTO save(RegistroDTO registroDTO) {
        log.debug("Request to save Registro : {}", registroDTO);
        Registro registro = registroMapper.toEntity(registroDTO);
        registro = registroRepository.save(registro);
        return registroMapper.toDto(registro);
    }

    @Override
    public RegistroDTO update(RegistroDTO registroDTO) {
        log.debug("Request to update Registro : {}", registroDTO);
        Registro registro = registroMapper.toEntity(registroDTO);
        registro = registroRepository.save(registro);
        return registroMapper.toDto(registro);
    }

    @Override
    public Optional<RegistroDTO> partialUpdate(RegistroDTO registroDTO) {
        log.debug("Request to partially update Registro : {}", registroDTO);

        return registroRepository
            .findById(registroDTO.getId())
            .map(existingRegistro -> {
                registroMapper.partialUpdate(existingRegistro, registroDTO);

                return existingRegistro;
            })
            .map(registroRepository::save)
            .map(registroMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegistroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Registros");
        return registroRepository.findAll(pageable).map(registroMapper::toDto);
    }

    public Page<RegistroDTO> findAllWithEagerRelationships(Pageable pageable) {
        return registroRepository.findAllWithEagerRelationships(pageable).map(registroMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroDTO> findOne(Long id) {
        log.debug("Request to get Registro : {}", id);
        return registroRepository.findOneWithEagerRelationships(id).map(registroMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Registro : {}", id);
        registroRepository.deleteById(id);
    }
}
