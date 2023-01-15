package com.refood.trazabilidad.service.impl;

import java.util.List;

import com.refood.trazabilidad.domain.Benef;
import com.refood.trazabilidad.repository.BenefRepository;
import com.refood.trazabilidad.service.BenefService;
import com.refood.trazabilidad.service.dto.BenefDTO;
import com.refood.trazabilidad.service.mapper.BenefMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Benef}.
 */
@Service
@Transactional
public class BenefServiceImpl implements BenefService {

    private final Logger log = LoggerFactory.getLogger(BenefServiceImpl.class);

    private final BenefRepository benefRepository;

    private final BenefMapper benefMapper;

    public BenefServiceImpl(BenefRepository benefRepository, BenefMapper benefMapper) {
        this.benefRepository = benefRepository;
        this.benefMapper = benefMapper;
    }

    @Override
    public List<Benef> findAll() {

        List<Benef> beneficiarios = benefRepository.findAll();
        // List<TipoDeAlimentoDTO> tipoDeAlimentoDtoList = Arrays
        // .asList(modelMapper.map(tipoDeAlimentos, TipoDeAlimentoDTO[].class));

        return beneficiarios;
    }

    @Override
    public BenefDTO save(BenefDTO benefDTO) {
        log.debug("Request to save Benef : {}", benefDTO);
        Benef benef = benefMapper.toEntity(benefDTO);
        benef = benefRepository.save(benef);
        return benefMapper.toDto(benef);
    }

    @Override
    public BenefDTO update(BenefDTO benefDTO) {
        log.debug("Request to update Benef : {}", benefDTO);
        Benef benef = benefMapper.toEntity(benefDTO);
        benef = benefRepository.save(benef);
        return benefMapper.toDto(benef);
    }

    @Override
    public Optional<BenefDTO> partialUpdate(BenefDTO benefDTO) {
        log.debug("Request to partially update Benef : {}", benefDTO);

        return benefRepository
                .findById(benefDTO.getId())
                .map(existingBenef -> {
                    benefMapper.partialUpdate(existingBenef, benefDTO);

                    return existingBenef;
                })
                .map(benefRepository::save)
                .map(benefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BenefDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Benefs");
        return benefRepository.findAll(pageable).map(benefMapper::toDto);
    }

    public Page<BenefDTO> findAllWithEagerRelationships(Pageable pageable) {
        return benefRepository.findAllWithEagerRelationships(pageable).map(benefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BenefDTO> findOne(Long id) {
        log.debug("Request to get Benef : {}", id);
        return benefRepository.findOneWithEagerRelationships(id).map(benefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Benef : {}", id);
        benefRepository.deleteById(id);
    }
}
