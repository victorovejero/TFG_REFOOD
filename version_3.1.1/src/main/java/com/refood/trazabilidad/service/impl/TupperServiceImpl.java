package com.refood.trazabilidad.service.impl;

import java.util.List;

import com.refood.trazabilidad.domain.Tupper;
import com.refood.trazabilidad.repository.TupperRepository;
import com.refood.trazabilidad.service.TupperService;
import com.refood.trazabilidad.service.dto.TupperDTO;
import com.refood.trazabilidad.service.mapper.TupperMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tupper}.
 */
@Service
@Transactional
public class TupperServiceImpl implements TupperService {

    private final Logger log = LoggerFactory.getLogger(TupperServiceImpl.class);

    private final TupperRepository tupperRepository;

    private final TupperMapper tupperMapper;

    public TupperServiceImpl(TupperRepository tupperRepository, TupperMapper tupperMapper) {
        this.tupperRepository = tupperRepository;
        this.tupperMapper = tupperMapper;
    }

    @Override
    public List<Tupper> findAll() {

        List<Tupper> tuppers = tupperRepository.findAll();

        return tuppers;
    }

    @Override
    public TupperDTO save(TupperDTO tupperDTO) {
        log.debug("Request to save Tupper : {}", tupperDTO);
        Tupper tupper = tupperMapper.toEntity(tupperDTO);
        tupper = tupperRepository.save(tupper);
        return tupperMapper.toDto(tupper);
    }

    @Override
    public TupperDTO update(TupperDTO tupperDTO) {
        log.debug("Request to update Tupper : {}", tupperDTO);
        Tupper tupper = tupperMapper.toEntity(tupperDTO);
        tupper = tupperRepository.save(tupper);
        return tupperMapper.toDto(tupper);
    }

    @Override
    public Optional<TupperDTO> partialUpdate(TupperDTO tupperDTO) {
        log.debug("Request to partially update Tupper : {}", tupperDTO);

        return tupperRepository
                .findById(tupperDTO.getId())
                .map(existingTupper -> {
                    tupperMapper.partialUpdate(existingTupper, tupperDTO);

                    return existingTupper;
                })
                .map(tupperRepository::save)
                .map(tupperMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TupperDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tuppers");
        return tupperRepository.findAll(pageable).map(tupperMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TupperDTO> findOne(Long id) {
        log.debug("Request to get Tupper : {}", id);
        return tupperRepository.findById(id).map(tupperMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tupper : {}", id);
        tupperRepository.deleteById(id);
    }
}
