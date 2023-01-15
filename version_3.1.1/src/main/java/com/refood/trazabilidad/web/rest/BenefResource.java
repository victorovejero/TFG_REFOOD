package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.BenefRepository;
import com.refood.trazabilidad.service.BenefService;
import com.refood.trazabilidad.service.dto.BenefDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Benef}.
 */
@RestController
@RequestMapping("/api")
public class BenefResource {

    private final Logger log = LoggerFactory.getLogger(BenefResource.class);

    private static final String ENTITY_NAME = "benef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefService benefService;

    private final BenefRepository benefRepository;

    public BenefResource(BenefService benefService, BenefRepository benefRepository) {
        this.benefService = benefService;
        this.benefRepository = benefRepository;
    }

    /**
     * {@code POST  /benefs} : Create a new benef.
     *
     * @param benefDTO the benefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefDTO, or with status {@code 400 (Bad Request)} if the benef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefs")
    public ResponseEntity<BenefDTO> createBenef(@Valid @RequestBody BenefDTO benefDTO) throws URISyntaxException {
        log.debug("REST request to save Benef : {}", benefDTO);
        if (benefDTO.getId() != null) {
            throw new BadRequestAlertException("A new benef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenefDTO result = benefService.save(benefDTO);
        return ResponseEntity
            .created(new URI("/api/benefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefs/:id} : Updates an existing benef.
     *
     * @param id the id of the benefDTO to save.
     * @param benefDTO the benefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefDTO,
     * or with status {@code 400 (Bad Request)} if the benefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefs/{id}")
    public ResponseEntity<BenefDTO> updateBenef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BenefDTO benefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Benef : {}, {}", id, benefDTO);
        if (benefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BenefDTO result = benefService.update(benefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /benefs/:id} : Partial updates given fields of an existing benef, field will ignore if it is null
     *
     * @param id the id of the benefDTO to save.
     * @param benefDTO the benefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefDTO,
     * or with status {@code 400 (Bad Request)} if the benefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the benefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the benefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/benefs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BenefDTO> partialUpdateBenef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BenefDTO benefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Benef partially : {}, {}", id, benefDTO);
        if (benefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BenefDTO> result = benefService.partialUpdate(benefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /benefs} : get all the benefs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefs in body.
     */
    @GetMapping("/benefs")
    public ResponseEntity<List<BenefDTO>> getAllBenefs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Benefs");
        Page<BenefDTO> page;
        if (eagerload) {
            page = benefService.findAllWithEagerRelationships(pageable);
        } else {
            page = benefService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /benefs/:id} : get the "id" benef.
     *
     * @param id the id of the benefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefs/{id}")
    public ResponseEntity<BenefDTO> getBenef(@PathVariable Long id) {
        log.debug("REST request to get Benef : {}", id);
        Optional<BenefDTO> benefDTO = benefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefDTO);
    }

    /**
     * {@code DELETE  /benefs/:id} : delete the "id" benef.
     *
     * @param id the id of the benefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefs/{id}")
    public ResponseEntity<Void> deleteBenef(@PathVariable Long id) {
        log.debug("REST request to delete Benef : {}", id);
        benefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
