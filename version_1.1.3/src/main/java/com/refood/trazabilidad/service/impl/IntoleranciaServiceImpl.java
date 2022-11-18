package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.Intolerancia;
import com.refood.trazabilidad.repository.IntoleranciaRepository;
import com.refood.trazabilidad.service.IntoleranciaService;
import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
import com.refood.trazabilidad.service.mapper.IntoleranciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Intolerancia}.
 */
@Service
@Transactional
public class IntoleranciaServiceImpl implements IntoleranciaService {

    private final Logger log = LoggerFactory.getLogger(IntoleranciaServiceImpl.class);

    private final IntoleranciaRepository intoleranciaRepository;

    private final IntoleranciaMapper intoleranciaMapper;

    public IntoleranciaServiceImpl(IntoleranciaRepository intoleranciaRepository, IntoleranciaMapper intoleranciaMapper) {
        this.intoleranciaRepository = intoleranciaRepository;
        this.intoleranciaMapper = intoleranciaMapper;
    }

    @Override
    public IntoleranciaDTO save(IntoleranciaDTO intoleranciaDTO) {
        log.debug("Request to save Intolerancia : {}", intoleranciaDTO);
        Intolerancia intolerancia = intoleranciaMapper.toEntity(intoleranciaDTO);
        intolerancia = intoleranciaRepository.save(intolerancia);
        return intoleranciaMapper.toDto(intolerancia);
    }

    @Override
    public IntoleranciaDTO update(IntoleranciaDTO intoleranciaDTO) {
        log.debug("Request to update Intolerancia : {}", intoleranciaDTO);
        Intolerancia intolerancia = intoleranciaMapper.toEntity(intoleranciaDTO);
        intolerancia = intoleranciaRepository.save(intolerancia);
        return intoleranciaMapper.toDto(intolerancia);
    }

    @Override
    public Optional<IntoleranciaDTO> partialUpdate(IntoleranciaDTO intoleranciaDTO) {
        log.debug("Request to partially update Intolerancia : {}", intoleranciaDTO);

        return intoleranciaRepository
            .findById(intoleranciaDTO.getId())
            .map(existingIntolerancia -> {
                intoleranciaMapper.partialUpdate(existingIntolerancia, intoleranciaDTO);

                return existingIntolerancia;
            })
            .map(intoleranciaRepository::save)
            .map(intoleranciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntoleranciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Intolerancias");
        return intoleranciaRepository.findAll(pageable).map(intoleranciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntoleranciaDTO> findOne(Long id) {
        log.debug("Request to get Intolerancia : {}", id);
        return intoleranciaRepository.findById(id).map(intoleranciaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intolerancia : {}", id);
        intoleranciaRepository.deleteById(id);
    }
}
