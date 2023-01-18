package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.IntolRepository;
import com.refood.trazabilidad.service.IntolService;
import com.refood.trazabilidad.service.dto.IntolDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Intol}.
 */
@RestController
@RequestMapping("/api")
public class IntolResource {

    private final Logger log = LoggerFactory.getLogger(IntolResource.class);

    private static final String ENTITY_NAME = "intol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntolService intolService;

    private final IntolRepository intolRepository;

    public IntolResource(IntolService intolService, IntolRepository intolRepository) {
        this.intolService = intolService;
        this.intolRepository = intolRepository;
    }

    /**
     * {@code POST  /intols} : Create a new intol.
     *
     * @param intolDTO the intolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intolDTO, or with status {@code 400 (Bad Request)} if the intol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intols")
    public ResponseEntity<IntolDTO> createIntol(@Valid @RequestBody IntolDTO intolDTO) throws URISyntaxException {
        log.debug("REST request to save Intol : {}", intolDTO);
        if (intolDTO.getId() != null) {
            throw new BadRequestAlertException("A new intol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntolDTO result = intolService.save(intolDTO);
        return ResponseEntity
            .created(new URI("/api/intols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intols/:id} : Updates an existing intol.
     *
     * @param id the id of the intolDTO to save.
     * @param intolDTO the intolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intolDTO,
     * or with status {@code 400 (Bad Request)} if the intolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intols/{id}")
    public ResponseEntity<IntolDTO> updateIntol(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntolDTO intolDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Intol : {}, {}", id, intolDTO);
        if (intolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intolDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IntolDTO result = intolService.update(intolDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intolDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /intols/:id} : Partial updates given fields of an existing intol, field will ignore if it is null
     *
     * @param id the id of the intolDTO to save.
     * @param intolDTO the intolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intolDTO,
     * or with status {@code 400 (Bad Request)} if the intolDTO is not valid,
     * or with status {@code 404 (Not Found)} if the intolDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the intolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/intols/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntolDTO> partialUpdateIntol(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntolDTO intolDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Intol partially : {}, {}", id, intolDTO);
        if (intolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intolDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntolDTO> result = intolService.partialUpdate(intolDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intolDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /intols} : get all the intols.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intols in body.
     */
    @GetMapping("/intols")
    public ResponseEntity<List<IntolDTO>> getAllIntols(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Intols");
        Page<IntolDTO> page = intolService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intols/:id} : get the "id" intol.
     *
     * @param id the id of the intolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intols/{id}")
    public ResponseEntity<IntolDTO> getIntol(@PathVariable Long id) {
        log.debug("REST request to get Intol : {}", id);
        Optional<IntolDTO> intolDTO = intolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intolDTO);
    }

    /**
     * {@code DELETE  /intols/:id} : delete the "id" intol.
     *
     * @param id the id of the intolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intols/{id}")
    public ResponseEntity<Void> deleteIntol(@PathVariable Long id) {
        log.debug("REST request to delete Intol : {}", id);
        intolService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
