package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.AlimentoDeSalida;
import com.refood.trazabilidad.repository.AlimentoDeSalidaRepository;
import com.refood.trazabilidad.service.AlimentoDeSalidaService;
import com.refood.trazabilidad.service.dto.AlimentoDeSalidaDTO;
import com.refood.trazabilidad.service.mapper.AlimentoDeSalidaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AlimentoDeSalida}.
 */
@Service
@Transactional
public class AlimentoDeSalidaServiceImpl implements AlimentoDeSalidaService {

    private final Logger log = LoggerFactory.getLogger(AlimentoDeSalidaServiceImpl.class);

    private final AlimentoDeSalidaRepository alimentoDeSalidaRepository;

    private final AlimentoDeSalidaMapper alimentoDeSalidaMapper;

    public AlimentoDeSalidaServiceImpl(
        AlimentoDeSalidaRepository alimentoDeSalidaRepository,
        AlimentoDeSalidaMapper alimentoDeSalidaMapper
    ) {
        this.alimentoDeSalidaRepository = alimentoDeSalidaRepository;
        this.alimentoDeSalidaMapper = alimentoDeSalidaMapper;
    }

    @Override
    public AlimentoDeSalidaDTO save(AlimentoDeSalidaDTO alimentoDeSalidaDTO) {
        log.debug("Request to save AlimentoDeSalida : {}", alimentoDeSalidaDTO);
        AlimentoDeSalida alimentoDeSalida = alimentoDeSalidaMapper.toEntity(alimentoDeSalidaDTO);
        alimentoDeSalida = alimentoDeSalidaRepository.save(alimentoDeSalida);
        return alimentoDeSalidaMapper.toDto(alimentoDeSalida);
    }

    @Override
    public AlimentoDeSalidaDTO update(AlimentoDeSalidaDTO alimentoDeSalidaDTO) {
        log.debug("Request to update AlimentoDeSalida : {}", alimentoDeSalidaDTO);
        AlimentoDeSalida alimentoDeSalida = alimentoDeSalidaMapper.toEntity(alimentoDeSalidaDTO);
        alimentoDeSalida = alimentoDeSalidaRepository.save(alimentoDeSalida);
        return alimentoDeSalidaMapper.toDto(alimentoDeSalida);
    }

    @Override
    public Optional<AlimentoDeSalidaDTO> partialUpdate(AlimentoDeSalidaDTO alimentoDeSalidaDTO) {
        log.debug("Request to partially update AlimentoDeSalida : {}", alimentoDeSalidaDTO);

        return alimentoDeSalidaRepository
            .findById(alimentoDeSalidaDTO.getId())
            .map(existingAlimentoDeSalida -> {
                alimentoDeSalidaMapper.partialUpdate(existingAlimentoDeSalida, alimentoDeSalidaDTO);

                return existingAlimentoDeSalida;
            })
            .map(alimentoDeSalidaRepository::save)
            .map(alimentoDeSalidaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlimentoDeSalidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlimentoDeSalidas");
        return alimentoDeSalidaRepository.findAll(pageable).map(alimentoDeSalidaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlimentoDeSalidaDTO> findOne(Long id) {
        log.debug("Request to get AlimentoDeSalida : {}", id);
        return alimentoDeSalidaRepository.findById(id).map(alimentoDeSalidaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlimentoDeSalida : {}", id);
        alimentoDeSalidaRepository.deleteById(id);
    }
}
