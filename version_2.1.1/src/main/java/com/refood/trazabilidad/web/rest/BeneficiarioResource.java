package com.refood.trazabilidad.web.rest;


import java.util.List;
import com.refood.trazabilidad.domain.Beneficiario;

import com.refood.trazabilidad.repository.BeneficiarioRepository;
import com.refood.trazabilidad.service.BeneficiarioService;
import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Beneficiario}.
 */
@RestController
@RequestMapping("/api")
public class BeneficiarioResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiarioResource.class);

    private static final String ENTITY_NAME = "beneficiario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeneficiarioService beneficiarioService;

    private final BeneficiarioRepository beneficiarioRepository;

    public BeneficiarioResource(BeneficiarioService beneficiarioService, BeneficiarioRepository beneficiarioRepository) {
        this.beneficiarioService = beneficiarioService;
        this.beneficiarioRepository = beneficiarioRepository;
    }


    @GetMapping("/beneficiarios-all")
    public ResponseEntity<List<Beneficiario>> getAll() {
        List<Beneficiario> beneficiarios = beneficiarioService.findAll();
        log.debug("REST request to get Beneficiario : {}", beneficiarios);
        return ResponseEntity.ok().body(beneficiarios);

    }

    /**
     * {@code POST  /beneficiarios} : Create a new beneficiario.
     *
     * @param beneficiarioDTO the beneficiarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beneficiarioDTO, or with status {@code 400 (Bad Request)} if the beneficiario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beneficiarios")
    public ResponseEntity<BeneficiarioDTO> createBeneficiario(@Valid @RequestBody BeneficiarioDTO beneficiarioDTO)
        throws URISyntaxException {
        log.debug("REST request to save Beneficiario : {}", beneficiarioDTO);
        if (beneficiarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new beneficiario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeneficiarioDTO result = beneficiarioService.save(beneficiarioDTO);
        return ResponseEntity
            .created(new URI("/api/beneficiarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beneficiarios/:id} : Updates an existing beneficiario.
     *
     * @param id the id of the beneficiarioDTO to save.
     * @param beneficiarioDTO the beneficiarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beneficiarioDTO,
     * or with status {@code 400 (Bad Request)} if the beneficiarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beneficiarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beneficiarios/{id}")
    public ResponseEntity<BeneficiarioDTO> updateBeneficiario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BeneficiarioDTO beneficiarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Beneficiario : {}, {}", id, beneficiarioDTO);
        if (beneficiarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beneficiarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beneficiarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BeneficiarioDTO result = beneficiarioService.update(beneficiarioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beneficiarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /beneficiarios/:id} : Partial updates given fields of an existing beneficiario, field will ignore if it is null
     *
     * @param id the id of the beneficiarioDTO to save.
     * @param beneficiarioDTO the beneficiarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beneficiarioDTO,
     * or with status {@code 400 (Bad Request)} if the beneficiarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the beneficiarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the beneficiarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/beneficiarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BeneficiarioDTO> partialUpdateBeneficiario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BeneficiarioDTO beneficiarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beneficiario partially : {}, {}", id, beneficiarioDTO);
        if (beneficiarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beneficiarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beneficiarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BeneficiarioDTO> result = beneficiarioService.partialUpdate(beneficiarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beneficiarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /beneficiarios} : get all the beneficiarios.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beneficiarios in body.
     */
    @GetMapping("/beneficiarios")
    public ResponseEntity<List<BeneficiarioDTO>> getAllBeneficiarios(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Beneficiarios");
        Page<BeneficiarioDTO> page;
        if (eagerload) {
            page = beneficiarioService.findAllWithEagerRelationships(pageable);
        } else {
            page = beneficiarioService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /beneficiarios/:id} : get the "id" beneficiario.
     *
     * @param id the id of the beneficiarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiarios/{id}")
    public ResponseEntity<BeneficiarioDTO> getBeneficiario(@PathVariable Long id) {
        log.debug("REST request to get Beneficiario : {}", id);
        Optional<BeneficiarioDTO> beneficiarioDTO = beneficiarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beneficiarioDTO);
    }

    /**
     * {@code DELETE  /beneficiarios/:id} : delete the "id" beneficiario.
     *
     * @param id the id of the beneficiarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beneficiarios/{id}")
    public ResponseEntity<Void> deleteBeneficiario(@PathVariable Long id) {
        log.debug("REST request to delete Beneficiario : {}", id);
        beneficiarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
