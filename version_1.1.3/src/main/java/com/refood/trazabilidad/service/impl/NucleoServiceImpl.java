package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.repository.NucleoRepository;
import com.refood.trazabilidad.service.NucleoService;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import com.refood.trazabilidad.service.mapper.NucleoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nucleo}.
 */
@Service
@Transactional
public class NucleoServiceImpl implements NucleoService {

    private final Logger log = LoggerFactory.getLogger(NucleoServiceImpl.class);

    private final NucleoRepository nucleoRepository;

    private final NucleoMapper nucleoMapper;

    public NucleoServiceImpl(NucleoRepository nucleoRepository, NucleoMapper nucleoMapper) {
        this.nucleoRepository = nucleoRepository;
        this.nucleoMapper = nucleoMapper;
    }

    @Override
    public NucleoDTO save(NucleoDTO nucleoDTO) {
        log.debug("Request to save Nucleo : {}", nucleoDTO);
        Nucleo nucleo = nucleoMapper.toEntity(nucleoDTO);
        nucleo = nucleoRepository.save(nucleo);
        return nucleoMapper.toDto(nucleo);
    }

    @Override
    public NucleoDTO update(NucleoDTO nucleoDTO) {
        log.debug("Request to update Nucleo : {}", nucleoDTO);
        Nucleo nucleo = nucleoMapper.toEntity(nucleoDTO);
        nucleo = nucleoRepository.save(nucleo);
        return nucleoMapper.toDto(nucleo);
    }

    @Override
    public Optional<NucleoDTO> partialUpdate(NucleoDTO nucleoDTO) {
        log.debug("Request to partially update Nucleo : {}", nucleoDTO);

        return nucleoRepository
            .findById(nucleoDTO.getId())
            .map(existingNucleo -> {
                nucleoMapper.partialUpdate(existingNucleo, nucleoDTO);

                return existingNucleo;
            })
            .map(nucleoRepository::save)
            .map(nucleoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NucleoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nucleos");
        return nucleoRepository.findAll(pageable).map(nucleoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NucleoDTO> findOne(Long id) {
        log.debug("Request to get Nucleo : {}", id);
        return nucleoRepository.findById(id).map(nucleoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nucleo : {}", id);
        nucleoRepository.deleteById(id);
    }
}
