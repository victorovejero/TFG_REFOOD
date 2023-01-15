package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.AlEntRepository;
import com.refood.trazabilidad.service.AlEntService;
import com.refood.trazabilidad.service.dto.AlEntDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.AlEnt}.
 */
@RestController
@RequestMapping("/api")
public class AlEntResource {

    private final Logger log = LoggerFactory.getLogger(AlEntResource.class);

    private static final String ENTITY_NAME = "alEnt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlEntService alEntService;

    private final AlEntRepository alEntRepository;

    public AlEntResource(AlEntService alEntService, AlEntRepository alEntRepository) {
        this.alEntService = alEntService;
        this.alEntRepository = alEntRepository;
    }

    /**
     * {@code POST  /al-ents} : Create a new alEnt.
     *
     * @param alEntDTO the alEntDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alEntDTO, or with status {@code 400 (Bad Request)} if the alEnt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/al-ents")
    public ResponseEntity<AlEntDTO> createAlEnt(@Valid @RequestBody AlEntDTO alEntDTO) throws URISyntaxException {
        log.debug("REST request to save AlEnt : {}", alEntDTO);
        if (alEntDTO.getId() != null) {
            throw new BadRequestAlertException("A new alEnt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlEntDTO result = alEntService.save(alEntDTO);
        return ResponseEntity
            .created(new URI("/api/al-ents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /al-ents/:id} : Updates an existing alEnt.
     *
     * @param id the id of the alEntDTO to save.
     * @param alEntDTO the alEntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alEntDTO,
     * or with status {@code 400 (Bad Request)} if the alEntDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alEntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/al-ents/{id}")
    public ResponseEntity<AlEntDTO> updateAlEnt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlEntDTO alEntDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AlEnt : {}, {}", id, alEntDTO);
        if (alEntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alEntDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alEntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlEntDTO result = alEntService.update(alEntDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alEntDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /al-ents/:id} : Partial updates given fields of an existing alEnt, field will ignore if it is null
     *
     * @param id the id of the alEntDTO to save.
     * @param alEntDTO the alEntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alEntDTO,
     * or with status {@code 400 (Bad Request)} if the alEntDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alEntDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alEntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/al-ents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlEntDTO> partialUpdateAlEnt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlEntDTO alEntDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AlEnt partially : {}, {}", id, alEntDTO);
        if (alEntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alEntDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alEntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlEntDTO> result = alEntService.partialUpdate(alEntDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alEntDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /al-ents} : get all the alEnts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alEnts in body.
     */
    @GetMapping("/al-ents")
    public ResponseEntity<List<AlEntDTO>> getAllAlEnts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AlEnts");
        Page<AlEntDTO> page = alEntService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /al-ents/:id} : get the "id" alEnt.
     *
     * @param id the id of the alEntDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alEntDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/al-ents/{id}")
    public ResponseEntity<AlEntDTO> getAlEnt(@PathVariable Long id) {
        log.debug("REST request to get AlEnt : {}", id);
        Optional<AlEntDTO> alEntDTO = alEntService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alEntDTO);
    }

    /**
     * {@code DELETE  /al-ents/:id} : delete the "id" alEnt.
     *
     * @param id the id of the alEntDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/al-ents/{id}")
    public ResponseEntity<Void> deleteAlEnt(@PathVariable Long id) {
        log.debug("REST request to delete AlEnt : {}", id);
        alEntService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
