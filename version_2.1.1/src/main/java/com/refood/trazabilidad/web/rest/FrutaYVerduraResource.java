package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.domain.FrutaYVerdura;

import com.refood.trazabilidad.repository.FrutaYVerduraRepository;
import com.refood.trazabilidad.service.FrutaYVerduraService;
import com.refood.trazabilidad.service.dto.FrutaYVerduraDTO;
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
 * REST controller for managing
 * {@link com.refood.trazabilidad.domain.FrutaYVerdura}.
 */
@RestController
@RequestMapping("/api")
public class FrutaYVerduraResource {

    private final Logger log = LoggerFactory.getLogger(FrutaYVerduraResource.class);

    private static final String ENTITY_NAME = "frutaYVerdura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FrutaYVerduraService frutaYVerduraService;

    private final FrutaYVerduraRepository frutaYVerduraRepository;

    public FrutaYVerduraResource(FrutaYVerduraService frutaYVerduraService,
            FrutaYVerduraRepository frutaYVerduraRepository) {
        this.frutaYVerduraService = frutaYVerduraService;
        this.frutaYVerduraRepository = frutaYVerduraRepository;
    }

    @GetMapping("/fruta-y-verdura-all")
    public ResponseEntity<List<FrutaYVerdura>> getAll() {
        List<FrutaYVerdura> frutaYVerduras = frutaYVerduraService.findAll();
        log.debug("REST request to get FrutaYVerdura : {}", frutaYVerduras);
        return ResponseEntity.ok().body(frutaYVerduras);

    }

    /**
     * {@code POST  /fruta-y-verduras} : Create a new frutaYVerdura.
     *
     * @param frutaYVerduraDTO the frutaYVerduraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new frutaYVerduraDTO, or with status
     *         {@code 400 (Bad Request)} if the frutaYVerdura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fruta-y-verduras")
    public ResponseEntity<FrutaYVerduraDTO> createFrutaYVerdura(@Valid @RequestBody FrutaYVerduraDTO frutaYVerduraDTO)
            throws URISyntaxException {
        log.debug("REST request to save FrutaYVerdura : {}", frutaYVerduraDTO);
        if (frutaYVerduraDTO.getId() != null) {
            throw new BadRequestAlertException("A new frutaYVerdura cannot already have an ID", ENTITY_NAME,
                    "idexists");
        }
        FrutaYVerduraDTO result = frutaYVerduraService.save(frutaYVerduraDTO);
        return ResponseEntity
                .created(new URI("/api/fruta-y-verduras/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                        result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /fruta-y-verduras/:id} : Updates an existing frutaYVerdura.
     *
     * @param id               the id of the frutaYVerduraDTO to save.
     * @param frutaYVerduraDTO the frutaYVerduraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated frutaYVerduraDTO,
     *         or with status {@code 400 (Bad Request)} if the frutaYVerduraDTO is
     *         not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         frutaYVerduraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fruta-y-verduras/{id}")
    public ResponseEntity<FrutaYVerduraDTO> updateFrutaYVerdura(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody FrutaYVerduraDTO frutaYVerduraDTO) throws URISyntaxException {
        log.debug("REST request to update FrutaYVerdura : {}, {}", id, frutaYVerduraDTO);
        if (frutaYVerduraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frutaYVerduraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frutaYVerduraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FrutaYVerduraDTO result = frutaYVerduraService.update(frutaYVerduraDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                        frutaYVerduraDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /fruta-y-verduras/:id} : Partial updates given fields of an
     * existing frutaYVerdura, field will ignore if it is null
     *
     * @param id               the id of the frutaYVerduraDTO to save.
     * @param frutaYVerduraDTO the frutaYVerduraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated frutaYVerduraDTO,
     *         or with status {@code 400 (Bad Request)} if the frutaYVerduraDTO is
     *         not valid,
     *         or with status {@code 404 (Not Found)} if the frutaYVerduraDTO is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         frutaYVerduraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fruta-y-verduras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FrutaYVerduraDTO> partialUpdateFrutaYVerdura(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody FrutaYVerduraDTO frutaYVerduraDTO) throws URISyntaxException {
        log.debug("REST request to partial update FrutaYVerdura partially : {}, {}", id, frutaYVerduraDTO);
        if (frutaYVerduraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frutaYVerduraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frutaYVerduraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FrutaYVerduraDTO> result = frutaYVerduraService.partialUpdate(frutaYVerduraDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                        frutaYVerduraDTO.getId().toString()));
    }

    /**
     * {@code GET  /fruta-y-verduras} : get all the frutaYVerduras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of frutaYVerduras in body.
     */
    @GetMapping("/fruta-y-verduras")
    public ResponseEntity<List<FrutaYVerduraDTO>> getAllFrutaYVerduras(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FrutaYVerduras");
        Page<FrutaYVerduraDTO> page = frutaYVerduraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fruta-y-verduras/:id} : get the "id" frutaYVerdura.
     *
     * @param id the id of the frutaYVerduraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the frutaYVerduraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fruta-y-verduras/{id}")
    public ResponseEntity<FrutaYVerduraDTO> getFrutaYVerdura(@PathVariable Long id) {
        log.debug("REST request to get FrutaYVerdura : {}", id);
        Optional<FrutaYVerduraDTO> frutaYVerduraDTO = frutaYVerduraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(frutaYVerduraDTO);
    }

    /**
     * {@code DELETE  /fruta-y-verduras/:id} : delete the "id" frutaYVerdura.
     *
     * @param id the id of the frutaYVerduraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fruta-y-verduras/{id}")
    public ResponseEntity<Void> deleteFrutaYVerdura(@PathVariable Long id) {
        log.debug("REST request to delete FrutaYVerdura : {}", id);
        frutaYVerduraService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
