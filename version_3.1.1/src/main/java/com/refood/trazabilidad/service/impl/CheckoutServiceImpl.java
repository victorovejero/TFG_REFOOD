package com.refood.trazabilidad.service.impl;

import com.refood.trazabilidad.domain.Checkout;
import com.refood.trazabilidad.repository.CheckoutRepository;
import com.refood.trazabilidad.service.CheckoutService;
import com.refood.trazabilidad.service.dto.CheckoutDTO;
import com.refood.trazabilidad.service.mapper.CheckoutMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Checkout}.
 */
@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    private final Logger log = LoggerFactory.getLogger(CheckoutServiceImpl.class);

    private final CheckoutRepository checkoutRepository;

    private final CheckoutMapper checkoutMapper;

    public CheckoutServiceImpl(CheckoutRepository checkoutRepository, CheckoutMapper checkoutMapper) {
        this.checkoutRepository = checkoutRepository;
        this.checkoutMapper = checkoutMapper;
    }

    @Override
    public CheckoutDTO save(CheckoutDTO checkoutDTO) {
        log.debug("Request to save Checkout : {}", checkoutDTO);
        Checkout checkout = checkoutMapper.toEntity(checkoutDTO);
        checkout = checkoutRepository.save(checkout);
        return checkoutMapper.toDto(checkout);
    }

    @Override
    public CheckoutDTO update(CheckoutDTO checkoutDTO) {
        log.debug("Request to update Checkout : {}", checkoutDTO);
        Checkout checkout = checkoutMapper.toEntity(checkoutDTO);
        checkout = checkoutRepository.save(checkout);
        return checkoutMapper.toDto(checkout);
    }

    @Override
    public Optional<CheckoutDTO> partialUpdate(CheckoutDTO checkoutDTO) {
        log.debug("Request to partially update Checkout : {}", checkoutDTO);

        return checkoutRepository
                .findById(checkoutDTO.getId())
                .map(existingCheckout -> {
                    checkoutMapper.partialUpdate(existingCheckout, checkoutDTO);

                    return existingCheckout;
                })
                .map(checkoutRepository::save)
                .map(checkoutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CheckoutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Checkouts");
        return checkoutRepository.findAll(pageable).map(checkoutMapper::toDto);
    }

    public Page<CheckoutDTO> findAllWithEagerRelationships(Pageable pageable) {
        return checkoutRepository.findAllWithEagerRelationships(pageable).map(checkoutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckoutDTO> findOne(Long id) {
        log.debug("Request to get Checkout : {}", id);
        return checkoutRepository.findOneWithEagerRelationships(id).map(checkoutMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Checkout : {}", id);
        checkoutRepository.deleteById(id);
    }
}
