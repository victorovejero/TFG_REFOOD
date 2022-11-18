package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.TipoDeAlimento;
import com.refood.trazabilidad.repository.TipoDeAlimentoRepository;
import com.refood.trazabilidad.service.TipoDeAlimentoService;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import com.refood.trazabilidad.service.mapper.TipoDeAlimentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoDeAlimento}.
 */
@Service
@Transactional
public class TipoDeAlimentoServiceImpl implements TipoDeAlimentoService {

    private final Logger log = LoggerFactory.getLogger(TipoDeAlimentoServiceImpl.class);

    private final TipoDeAlimentoRepository tipoDeAlimentoRepository;

    private final TipoDeAlimentoMapper tipoDeAlimentoMapper;

    public TipoDeAlimentoServiceImpl(TipoDeAlimentoRepository tipoDeAlimentoRepository, TipoDeAlimentoMapper tipoDeAlimentoMapper) {
        this.tipoDeAlimentoRepository = tipoDeAlimentoRepository;
        this.tipoDeAlimentoMapper = tipoDeAlimentoMapper;
    }

    @Override
    public TipoDeAlimentoDTO save(TipoDeAlimentoDTO tipoDeAlimentoDTO) {
        log.debug("Request to save TipoDeAlimento : {}", tipoDeAlimentoDTO);
        TipoDeAlimento tipoDeAlimento = tipoDeAlimentoMapper.toEntity(tipoDeAlimentoDTO);
        tipoDeAlimento = tipoDeAlimentoRepository.save(tipoDeAlimento);
        return tipoDeAlimentoMapper.toDto(tipoDeAlimento);
    }

    @Override
    public TipoDeAlimentoDTO update(TipoDeAlimentoDTO tipoDeAlimentoDTO) {
        log.debug("Request to update TipoDeAlimento : {}", tipoDeAlimentoDTO);
        TipoDeAlimento tipoDeAlimento = tipoDeAlimentoMapper.toEntity(tipoDeAlimentoDTO);
        tipoDeAlimento = tipoDeAlimentoRepository.save(tipoDeAlimento);
        return tipoDeAlimentoMapper.toDto(tipoDeAlimento);
    }

    @Override
    public Optional<TipoDeAlimentoDTO> partialUpdate(TipoDeAlimentoDTO tipoDeAlimentoDTO) {
        log.debug("Request to partially update TipoDeAlimento : {}", tipoDeAlimentoDTO);

        return tipoDeAlimentoRepository
            .findById(tipoDeAlimentoDTO.getId())
            .map(existingTipoDeAlimento -> {
                tipoDeAlimentoMapper.partialUpdate(existingTipoDeAlimento, tipoDeAlimentoDTO);

                return existingTipoDeAlimento;
            })
            .map(tipoDeAlimentoRepository::save)
            .map(tipoDeAlimentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeAlimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDeAlimentos");
        return tipoDeAlimentoRepository.findAll(pageable).map(tipoDeAlimentoMapper::toDto);
    }

    public Page<TipoDeAlimentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tipoDeAlimentoRepository.findAllWithEagerRelationships(pageable).map(tipoDeAlimentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeAlimentoDTO> findOne(Long id) {
        log.debug("Request to get TipoDeAlimento : {}", id);
        return tipoDeAlimentoRepository.findOneWithEagerRelationships(id).map(tipoDeAlimentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDeAlimento : {}", id);
        tipoDeAlimentoRepository.deleteById(id);
    }
}
