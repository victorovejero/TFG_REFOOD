package com.refood.trazabilidad.service.impl;

import java.util.List;
import java.util.Arrays;

import com.refood.trazabilidad.domain.FrutaYVerdura;
import com.refood.trazabilidad.repository.FrutaYVerduraRepository;
import com.refood.trazabilidad.service.FrutaYVerduraService;
import com.refood.trazabilidad.service.dto.FrutaYVerduraDTO;
import com.refood.trazabilidad.service.mapper.FrutaYVerduraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FrutaYVerdura}.
 */
@Service
@Transactional
public class FrutaYVerduraServiceImpl implements FrutaYVerduraService {

    private final Logger log = LoggerFactory.getLogger(FrutaYVerduraServiceImpl.class);

    private final FrutaYVerduraRepository frutaYVerduraRepository;

    private final FrutaYVerduraMapper frutaYVerduraMapper;

    public FrutaYVerduraServiceImpl(FrutaYVerduraRepository frutaYVerduraRepository,
            FrutaYVerduraMapper frutaYVerduraMapper) {
        this.frutaYVerduraRepository = frutaYVerduraRepository;
        this.frutaYVerduraMapper = frutaYVerduraMapper;
    }

    @Override
    public List<FrutaYVerdura> findAll() {

        List<FrutaYVerdura> frutaYVerduras = frutaYVerduraRepository.findAll();
        // List<TipoDeAlimentoDTO> tipoDeAlimentoDtoList = Arrays
        // .asList(modelMapper.map(tipoDeAlimentos, TipoDeAlimentoDTO[].class));

        return frutaYVerduras;
    }

    @Override
    public FrutaYVerduraDTO save(FrutaYVerduraDTO frutaYVerduraDTO) {
        log.debug("Request to save FrutaYVerdura : {}", frutaYVerduraDTO);
        FrutaYVerdura frutaYVerdura = frutaYVerduraMapper.toEntity(frutaYVerduraDTO);
        frutaYVerdura = frutaYVerduraRepository.save(frutaYVerdura);
        return frutaYVerduraMapper.toDto(frutaYVerdura);
    }

    @Override
    public FrutaYVerduraDTO update(FrutaYVerduraDTO frutaYVerduraDTO) {
        log.debug("Request to update FrutaYVerdura : {}", frutaYVerduraDTO);
        FrutaYVerdura frutaYVerdura = frutaYVerduraMapper.toEntity(frutaYVerduraDTO);
        frutaYVerdura = frutaYVerduraRepository.save(frutaYVerdura);
        return frutaYVerduraMapper.toDto(frutaYVerdura);
    }

    @Override
    public Optional<FrutaYVerduraDTO> partialUpdate(FrutaYVerduraDTO frutaYVerduraDTO) {
        log.debug("Request to partially update FrutaYVerdura : {}", frutaYVerduraDTO);

        return frutaYVerduraRepository
                .findById(frutaYVerduraDTO.getId())
                .map(existingFrutaYVerdura -> {
                    frutaYVerduraMapper.partialUpdate(existingFrutaYVerdura, frutaYVerduraDTO);

                    return existingFrutaYVerdura;
                })
                .map(frutaYVerduraRepository::save)
                .map(frutaYVerduraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FrutaYVerduraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FrutaYVerduras");
        return frutaYVerduraRepository.findAll(pageable).map(frutaYVerduraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FrutaYVerduraDTO> findOne(Long id) {
        log.debug("Request to get FrutaYVerdura : {}", id);
        return frutaYVerduraRepository.findById(id).map(frutaYVerduraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FrutaYVerdura : {}", id);
        frutaYVerduraRepository.deleteById(id);
    }
}
