package com.refood.trazabilidad.service.impl;

import java.util.List;

import com.refood.trazabilidad.domain.Donante;
import com.refood.trazabilidad.repository.DonanteRepository;
import com.refood.trazabilidad.service.DonanteService;
import com.refood.trazabilidad.service.dto.DonanteDTO;
import com.refood.trazabilidad.service.mapper.DonanteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Donante}.
 */
@Service
@Transactional
public class DonanteServiceImpl implements DonanteService {

    private final Logger log = LoggerFactory.getLogger(DonanteServiceImpl.class);

    private final DonanteRepository donanteRepository;

    private final DonanteMapper donanteMapper;

    public DonanteServiceImpl(DonanteRepository donanteRepository, DonanteMapper donanteMapper) {
        this.donanteRepository = donanteRepository;
        this.donanteMapper = donanteMapper;
    }

    @Override
    public DonanteDTO save(DonanteDTO donanteDTO) {
        log.debug("Request to save Donante : {}", donanteDTO);
        Donante donante = donanteMapper.toEntity(donanteDTO);
        donante = donanteRepository.save(donante);
        return donanteMapper.toDto(donante);
    }

    @Override
    public List<Donante> findAll() {

        List<Donante> donantes = donanteRepository.findAll();
        // List<TipoDeAlimentoDTO> tipoDeAlimentoDtoList = Arrays
        // .asList(modelMapper.map(tipoDeAlimentos, TipoDeAlimentoDTO[].class));

        return donantes;
    }

    @Override
    public DonanteDTO update(DonanteDTO donanteDTO) {
        log.debug("Request to update Donante : {}", donanteDTO);
        Donante donante = donanteMapper.toEntity(donanteDTO);
        donante = donanteRepository.save(donante);
        return donanteMapper.toDto(donante);
    }

    @Override
    public Optional<DonanteDTO> partialUpdate(DonanteDTO donanteDTO) {
        log.debug("Request to partially update Donante : {}", donanteDTO);

        return donanteRepository
                .findById(donanteDTO.getId())
                .map(existingDonante -> {
                    donanteMapper.partialUpdate(existingDonante, donanteDTO);

                    return existingDonante;
                })
                .map(donanteRepository::save)
                .map(donanteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonanteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Donantes");
        return donanteRepository.findAll(pageable).map(donanteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonanteDTO> findOne(Long id) {
        log.debug("Request to get Donante : {}", id);
        return donanteRepository.findById(id).map(donanteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Donante : {}", id);
        donanteRepository.deleteById(id);
    }
}
