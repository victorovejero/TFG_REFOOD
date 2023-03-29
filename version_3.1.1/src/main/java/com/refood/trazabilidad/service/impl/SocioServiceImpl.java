package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.Socio;
import com.refood.trazabilidad.repository.SocioRepository;
import com.refood.trazabilidad.service.SocioService;
import com.refood.trazabilidad.service.dto.SocioDTO;
import com.refood.trazabilidad.service.mapper.SocioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Socio}.
 */
@Service
@Transactional
public class SocioServiceImpl implements SocioService {

    private final Logger log = LoggerFactory.getLogger(SocioServiceImpl.class);

    private final SocioRepository socioRepository;

    private final SocioMapper socioMapper;

    public SocioServiceImpl(SocioRepository socioRepository, SocioMapper socioMapper) {
        this.socioRepository = socioRepository;
        this.socioMapper = socioMapper;
    }

    @Override
    public SocioDTO save(SocioDTO socioDTO) {
        log.debug("Request to save Socio : {}", socioDTO);
        Socio socio = socioMapper.toEntity(socioDTO);
        socio = socioRepository.save(socio);
        return socioMapper.toDto(socio);
    }

    @Override
    public SocioDTO update(SocioDTO socioDTO) {
        log.debug("Request to update Socio : {}", socioDTO);
        Socio socio = socioMapper.toEntity(socioDTO);
        socio = socioRepository.save(socio);
        return socioMapper.toDto(socio);
    }

    @Override
    public Optional<SocioDTO> partialUpdate(SocioDTO socioDTO) {
        log.debug("Request to partially update Socio : {}", socioDTO);

        return socioRepository
            .findById(socioDTO.getId())
            .map(existingSocio -> {
                socioMapper.partialUpdate(existingSocio, socioDTO);

                return existingSocio;
            })
            .map(socioRepository::save)
            .map(socioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Socios");
        return socioRepository.findAll(pageable).map(socioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocioDTO> findOne(Long id) {
        log.debug("Request to get Socio : {}", id);
        return socioRepository.findById(id).map(socioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Socio : {}", id);
        socioRepository.deleteById(id);
    }
}
