package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.CheckoutRepository;
import com.refood.trazabilidad.service.CheckoutService;
import com.refood.trazabilidad.service.dto.CheckoutDTO;
import com.refood.trazabilidad.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.refood.trazabilidad.domain.Checkout}.
 */
@RestController
@RequestMapping("/api")
public class CheckoutResource {

    private final Logger log = LoggerFactory.getLogger(CheckoutResource.class);

    private static final String ENTITY_NAME = "checkout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckoutService checkoutService;

    private final CheckoutRepository checkoutRepository;

    public CheckoutResource(CheckoutService checkoutService, CheckoutRepository checkoutRepository) {
        this.checkoutService = checkoutService;
        this.checkoutRepository = checkoutRepository;
    }

    /**
     * {@code POST  /checkouts} : Create a new checkout.
     *
     * @param checkoutDTO the checkoutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkoutDTO, or with status {@code 400 (Bad Request)} if the checkout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checkouts")
    public ResponseEntity<CheckoutDTO> createCheckout(@Valid @RequestBody CheckoutDTO checkoutDTO) throws URISyntaxException {
        log.debug("REST request to save Checkout : {}", checkoutDTO);
        if (checkoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckoutDTO result = checkoutService.save(checkoutDTO);
        return ResponseEntity
            .created(new URI("/api/checkouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checkouts/:id} : Updates an existing checkout.
     *
     * @param id the id of the checkoutDTO to save.
     * @param checkoutDTO the checkoutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkoutDTO,
     * or with status {@code 400 (Bad Request)} if the checkoutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkoutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checkouts/{id}")
    public ResponseEntity<CheckoutDTO> updateCheckout(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CheckoutDTO checkoutDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Checkout : {}, {}", id, checkoutDTO);
        if (checkoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkoutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckoutDTO result = checkoutService.update(checkoutDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checkoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /checkouts/:id} : Partial updates given fields of an existing checkout, field will ignore if it is null
     *
     * @param id the id of the checkoutDTO to save.
     * @param checkoutDTO the checkoutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkoutDTO,
     * or with status {@code 400 (Bad Request)} if the checkoutDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkoutDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkoutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/checkouts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CheckoutDTO> partialUpdateCheckout(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CheckoutDTO checkoutDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Checkout partially : {}, {}", id, checkoutDTO);
        if (checkoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkoutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckoutDTO> result = checkoutService.partialUpdate(checkoutDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checkoutDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /checkouts} : get all the checkouts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkouts in body.
     */
    @GetMapping("/checkouts")
    public ResponseEntity<List<CheckoutDTO>> getAllCheckouts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Checkouts");
        Page<CheckoutDTO> page;
        if (eagerload) {
            page = checkoutService.findAllWithEagerRelationships(pageable);
        } else {
            page = checkoutService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /checkouts/:id} : get the "id" checkout.
     *
     * @param id the id of the checkoutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkoutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkouts/{id}")
    public ResponseEntity<CheckoutDTO> getCheckout(@PathVariable Long id) {
        log.debug("REST request to get Checkout : {}", id);
        Optional<CheckoutDTO> checkoutDTO = checkoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkoutDTO);
    }

    /**
     * {@code DELETE  /checkouts/:id} : delete the "id" checkout.
     *
     * @param id the id of the checkoutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checkouts/{id}")
    public ResponseEntity<Void> deleteCheckout(@PathVariable Long id) {
        log.debug("REST request to delete Checkout : {}", id);
        checkoutService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
