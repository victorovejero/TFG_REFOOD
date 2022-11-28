package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.AlimentoDeEntrada;
import com.refood.trazabilidad.repository.AlimentoDeEntradaRepository;
import com.refood.trazabilidad.service.AlimentoDeEntradaService;
import com.refood.trazabilidad.service.dto.AlimentoDeEntradaDTO;
import com.refood.trazabilidad.service.mapper.AlimentoDeEntradaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AlimentoDeEntrada}.
 */
@Service
@Transactional
public class AlimentoDeEntradaServiceImpl implements AlimentoDeEntradaService {

    private final Logger log = LoggerFactory.getLogger(AlimentoDeEntradaServiceImpl.class);

    private final AlimentoDeEntradaRepository alimentoDeEntradaRepository;

    private final AlimentoDeEntradaMapper alimentoDeEntradaMapper;

    public AlimentoDeEntradaServiceImpl(
        AlimentoDeEntradaRepository alimentoDeEntradaRepository,
        AlimentoDeEntradaMapper alimentoDeEntradaMapper
    ) {
        this.alimentoDeEntradaRepository = alimentoDeEntradaRepository;
        this.alimentoDeEntradaMapper = alimentoDeEntradaMapper;
    }

    @Override
    public AlimentoDeEntradaDTO save(AlimentoDeEntradaDTO alimentoDeEntradaDTO) {
        log.debug("Request to save AlimentoDeEntrada : {}", alimentoDeEntradaDTO);
        AlimentoDeEntrada alimentoDeEntrada = alimentoDeEntradaMapper.toEntity(alimentoDeEntradaDTO);
        alimentoDeEntrada = alimentoDeEntradaRepository.save(alimentoDeEntrada);
        return alimentoDeEntradaMapper.toDto(alimentoDeEntrada);
    }

    @Override
    public AlimentoDeEntradaDTO update(AlimentoDeEntradaDTO alimentoDeEntradaDTO) {
        log.debug("Request to update AlimentoDeEntrada : {}", alimentoDeEntradaDTO);
        AlimentoDeEntrada alimentoDeEntrada = alimentoDeEntradaMapper.toEntity(alimentoDeEntradaDTO);
        alimentoDeEntrada = alimentoDeEntradaRepository.save(alimentoDeEntrada);
        return alimentoDeEntradaMapper.toDto(alimentoDeEntrada);
    }

    @Override
    public Optional<AlimentoDeEntradaDTO> partialUpdate(AlimentoDeEntradaDTO alimentoDeEntradaDTO) {
        log.debug("Request to partially update AlimentoDeEntrada : {}", alimentoDeEntradaDTO);

        return alimentoDeEntradaRepository
            .findById(alimentoDeEntradaDTO.getId())
            .map(existingAlimentoDeEntrada -> {
                alimentoDeEntradaMapper.partialUpdate(existingAlimentoDeEntrada, alimentoDeEntradaDTO);

                return existingAlimentoDeEntrada;
            })
            .map(alimentoDeEntradaRepository::save)
            .map(alimentoDeEntradaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlimentoDeEntradaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlimentoDeEntradas");
        return alimentoDeEntradaRepository.findAll(pageable).map(alimentoDeEntradaMapper::toDto);
    }

    public Page<AlimentoDeEntradaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return alimentoDeEntradaRepository.findAllWithEagerRelationships(pageable).map(alimentoDeEntradaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlimentoDeEntradaDTO> findOne(Long id) {
        log.debug("Request to get AlimentoDeEntrada : {}", id);
        return alimentoDeEntradaRepository.findOneWithEagerRelationships(id).map(alimentoDeEntradaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlimentoDeEntrada : {}", id);
        alimentoDeEntradaRepository.deleteById(id);
    }
}
