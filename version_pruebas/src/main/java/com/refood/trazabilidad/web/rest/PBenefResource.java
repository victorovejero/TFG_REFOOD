package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.PBenefRepository;
import com.refood.trazabilidad.service.PBenefService;
import com.refood.trazabilidad.service.dto.PBenefDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.PBenef}.
 */
@RestController
@RequestMapping("/api")
public class PBenefResource {

    private final Logger log = LoggerFactory.getLogger(PBenefResource.class);

    private static final String ENTITY_NAME = "pBenef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PBenefService pBenefService;

    private final PBenefRepository pBenefRepository;

    public PBenefResource(PBenefService pBenefService, PBenefRepository pBenefRepository) {
        this.pBenefService = pBenefService;
        this.pBenefRepository = pBenefRepository;
    }

    /**
     * {@code POST  /p-benefs} : Create a new pBenef.
     *
     * @param pBenefDTO the pBenefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pBenefDTO, or with status {@code 400 (Bad Request)} if the pBenef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-benefs")
    public ResponseEntity<PBenefDTO> createPBenef(@Valid @RequestBody PBenefDTO pBenefDTO) throws URISyntaxException {
        log.debug("REST request to save PBenef : {}", pBenefDTO);
        if (pBenefDTO.getId() != null) {
            throw new BadRequestAlertException("A new pBenef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PBenefDTO result = pBenefService.save(pBenefDTO);
        return ResponseEntity
            .created(new URI("/api/p-benefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-benefs/:id} : Updates an existing pBenef.
     *
     * @param id the id of the pBenefDTO to save.
     * @param pBenefDTO the pBenefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pBenefDTO,
     * or with status {@code 400 (Bad Request)} if the pBenefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pBenefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-benefs/{id}")
    public ResponseEntity<PBenefDTO> updatePBenef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PBenefDTO pBenefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PBenef : {}, {}", id, pBenefDTO);
        if (pBenefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pBenefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pBenefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PBenefDTO result = pBenefService.update(pBenefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pBenefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-benefs/:id} : Partial updates given fields of an existing pBenef, field will ignore if it is null
     *
     * @param id the id of the pBenefDTO to save.
     * @param pBenefDTO the pBenefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pBenefDTO,
     * or with status {@code 400 (Bad Request)} if the pBenefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pBenefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pBenefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-benefs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PBenefDTO> partialUpdatePBenef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PBenefDTO pBenefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PBenef partially : {}, {}", id, pBenefDTO);
        if (pBenefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pBenefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pBenefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PBenefDTO> result = pBenefService.partialUpdate(pBenefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pBenefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /p-benefs} : get all the pBenefs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pBenefs in body.
     */
    @GetMapping("/p-benefs")
    public ResponseEntity<List<PBenefDTO>> getAllPBenefs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PBenefs");
        Page<PBenefDTO> page;
        if (eagerload) {
            page = pBenefService.findAllWithEagerRelationships(pageable);
        } else {
            page = pBenefService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /p-benefs/:id} : get the "id" pBenef.
     *
     * @param id the id of the pBenefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pBenefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-benefs/{id}")
    public ResponseEntity<PBenefDTO> getPBenef(@PathVariable Long id) {
        log.debug("REST request to get PBenef : {}", id);
        Optional<PBenefDTO> pBenefDTO = pBenefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pBenefDTO);
    }

    /**
     * {@code DELETE  /p-benefs/:id} : delete the "id" pBenef.
     *
     * @param id the id of the pBenefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-benefs/{id}")
    public ResponseEntity<Void> deletePBenef(@PathVariable Long id) {
        log.debug("REST request to delete PBenef : {}", id);
        pBenefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
