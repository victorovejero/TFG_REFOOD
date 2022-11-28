package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.PersonaBeneficiaria;
import com.refood.trazabilidad.repository.PersonaBeneficiariaRepository;
import com.refood.trazabilidad.service.PersonaBeneficiariaService;
import com.refood.trazabilidad.service.dto.PersonaBeneficiariaDTO;
import com.refood.trazabilidad.service.mapper.PersonaBeneficiariaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonaBeneficiaria}.
 */
@Service
@Transactional
public class PersonaBeneficiariaServiceImpl implements PersonaBeneficiariaService {

    private final Logger log = LoggerFactory.getLogger(PersonaBeneficiariaServiceImpl.class);

    private final PersonaBeneficiariaRepository personaBeneficiariaRepository;

    private final PersonaBeneficiariaMapper personaBeneficiariaMapper;

    public PersonaBeneficiariaServiceImpl(
        PersonaBeneficiariaRepository personaBeneficiariaRepository,
        PersonaBeneficiariaMapper personaBeneficiariaMapper
    ) {
        this.personaBeneficiariaRepository = personaBeneficiariaRepository;
        this.personaBeneficiariaMapper = personaBeneficiariaMapper;
    }

    @Override
    public PersonaBeneficiariaDTO save(PersonaBeneficiariaDTO personaBeneficiariaDTO) {
        log.debug("Request to save PersonaBeneficiaria : {}", personaBeneficiariaDTO);
        PersonaBeneficiaria personaBeneficiaria = personaBeneficiariaMapper.toEntity(personaBeneficiariaDTO);
        personaBeneficiaria = personaBeneficiariaRepository.save(personaBeneficiaria);
        return personaBeneficiariaMapper.toDto(personaBeneficiaria);
    }

    @Override
    public PersonaBeneficiariaDTO update(PersonaBeneficiariaDTO personaBeneficiariaDTO) {
        log.debug("Request to update PersonaBeneficiaria : {}", personaBeneficiariaDTO);
        PersonaBeneficiaria personaBeneficiaria = personaBeneficiariaMapper.toEntity(personaBeneficiariaDTO);
        personaBeneficiaria = personaBeneficiariaRepository.save(personaBeneficiaria);
        return personaBeneficiariaMapper.toDto(personaBeneficiaria);
    }

    @Override
    public Optional<PersonaBeneficiariaDTO> partialUpdate(PersonaBeneficiariaDTO personaBeneficiariaDTO) {
        log.debug("Request to partially update PersonaBeneficiaria : {}", personaBeneficiariaDTO);

        return personaBeneficiariaRepository
            .findById(personaBeneficiariaDTO.getId())
            .map(existingPersonaBeneficiaria -> {
                personaBeneficiariaMapper.partialUpdate(existingPersonaBeneficiaria, personaBeneficiariaDTO);

                return existingPersonaBeneficiaria;
            })
            .map(personaBeneficiariaRepository::save)
            .map(personaBeneficiariaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaBeneficiariaDTO> findAll() {
        log.debug("Request to get all PersonaBeneficiarias");
        return personaBeneficiariaRepository
            .findAll()
            .stream()
            .map(personaBeneficiariaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<PersonaBeneficiariaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return personaBeneficiariaRepository.findAllWithEagerRelationships(pageable).map(personaBeneficiariaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonaBeneficiariaDTO> findOne(Long id) {
        log.debug("Request to get PersonaBeneficiaria : {}", id);
        return personaBeneficiariaRepository.findOneWithEagerRelationships(id).map(personaBeneficiariaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonaBeneficiaria : {}", id);
        personaBeneficiariaRepository.deleteById(id);
    }
}
