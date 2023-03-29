package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.domain.AlSal;

import com.refood.trazabilidad.repository.AlSalRepository;
import com.refood.trazabilidad.service.AlSalService;
import com.refood.trazabilidad.service.dto.AlSalDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.AlSal}.
 */
@RestController
@RequestMapping("/api")
public class AlSalResource {

    private final Logger log = LoggerFactory.getLogger(AlSalResource.class);

    private static final String ENTITY_NAME = "alSal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlSalService alSalService;

    private final AlSalRepository alSalRepository;

    public AlSalResource(AlSalService alSalService, AlSalRepository alSalRepository) {
        this.alSalService = alSalService;
        this.alSalRepository = alSalRepository;
    }

    // Implementación Endpoint para sacar todos los elementos de la tabla
    @GetMapping("/al-sal-all")
    public ResponseEntity<List<AlSal>> getAll() {
        List<AlSal> alimentosDeSalida = alSalService.findAll();
        log.debug("REST request to get AlimentoDeSalida : {}", alimentosDeSalida);
        return ResponseEntity.ok().body(alimentosDeSalida);

    }

    /**
     * {@code POST  /al-sals} : Create a new alSal.
     *
     * @param alSalDTO the alSalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new alSalDTO, or with status {@code 400 (Bad Request)} if
     *         the alSal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/al-sals")
    public ResponseEntity<AlSalDTO> createAlSal(@Valid @RequestBody AlSalDTO alSalDTO) throws URISyntaxException {
        log.debug("REST request to save AlSal : {}", alSalDTO);
        if (alSalDTO.getId() != null) {
            throw new BadRequestAlertException("A new alSal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlSalDTO result = alSalService.save(alSalDTO);
        return ResponseEntity
                .created(new URI("/api/al-sals/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                        result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /al-sals/:id} : Updates an existing alSal.
     *
     * @param id       the id of the alSalDTO to save.
     * @param alSalDTO the alSalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated alSalDTO,
     *         or with status {@code 400 (Bad Request)} if the alSalDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the alSalDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/al-sals/{id}")
    public ResponseEntity<AlSalDTO> updateAlSal(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody AlSalDTO alSalDTO) throws URISyntaxException {
        log.debug("REST request to update AlSal : {}, {}", id, alSalDTO);
        if (alSalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alSalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alSalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlSalDTO result = alSalService.update(alSalDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                        alSalDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /al-sals/:id} : Partial updates given fields of an existing
     * alSal, field will ignore if it is null
     *
     * @param id       the id of the alSalDTO to save.
     * @param alSalDTO the alSalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated alSalDTO,
     *         or with status {@code 400 (Bad Request)} if the alSalDTO is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the alSalDTO is not found,
     *         or with status {@code 500 (Internal Server Error)} if the alSalDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/al-sals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlSalDTO> partialUpdateAlSal(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody AlSalDTO alSalDTO) throws URISyntaxException {
        log.debug("REST request to partial update AlSal partially : {}, {}", id, alSalDTO);
        if (alSalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alSalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alSalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlSalDTO> result = alSalService.partialUpdate(alSalDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alSalDTO.getId().toString()));
    }

    /**
     * {@code GET  /al-sals} : get all the alSals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of alSals in body.
     */
    @GetMapping("/al-sals")
    public ResponseEntity<List<AlSalDTO>> getAllAlSals(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AlSals");
        Page<AlSalDTO> page = alSalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /al-sals/:id} : get the "id" alSal.
     *
     * @param id the id of the alSalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the alSalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/al-sals/{id}")
    public ResponseEntity<AlSalDTO> getAlSal(@PathVariable Long id) {
        log.debug("REST request to get AlSal : {}", id);
        Optional<AlSalDTO> alSalDTO = alSalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alSalDTO);
    }

    /**
     * {@code DELETE  /al-sals/:id} : delete the "id" alSal.
     *
     * @param id the id of the alSalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/al-sals/{id}")
    public ResponseEntity<Void> deleteAlSal(@PathVariable Long id) {
        log.debug("REST request to delete AlSal : {}", id);
        alSalService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
