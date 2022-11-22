package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.TupperRepository;
import com.refood.trazabilidad.service.TupperService;
import com.refood.trazabilidad.service.dto.TupperDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Tupper}.
 */
@RestController
@RequestMapping("/api")
public class TupperResource {

    private final Logger log = LoggerFactory.getLogger(TupperResource.class);

    private static final String ENTITY_NAME = "tupper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TupperService tupperService;

    private final TupperRepository tupperRepository;

    public TupperResource(TupperService tupperService, TupperRepository tupperRepository) {
        this.tupperService = tupperService;
        this.tupperRepository = tupperRepository;
    }

    /**
     * {@code POST  /tuppers} : Create a new tupper.
     *
     * @param tupperDTO the tupperDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tupperDTO, or with status {@code 400 (Bad Request)} if the tupper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tuppers")
    public ResponseEntity<TupperDTO> createTupper(@Valid @RequestBody TupperDTO tupperDTO) throws URISyntaxException {
        log.debug("REST request to save Tupper : {}", tupperDTO);
        if (tupperDTO.getId() != null) {
            throw new BadRequestAlertException("A new tupper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TupperDTO result = tupperService.save(tupperDTO);
        return ResponseEntity
            .created(new URI("/api/tuppers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tuppers/:id} : Updates an existing tupper.
     *
     * @param id the id of the tupperDTO to save.
     * @param tupperDTO the tupperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tupperDTO,
     * or with status {@code 400 (Bad Request)} if the tupperDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tupperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tuppers/{id}")
    public ResponseEntity<TupperDTO> updateTupper(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TupperDTO tupperDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tupper : {}, {}", id, tupperDTO);
        if (tupperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tupperDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tupperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TupperDTO result = tupperService.update(tupperDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tupperDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tuppers/:id} : Partial updates given fields of an existing tupper, field will ignore if it is null
     *
     * @param id the id of the tupperDTO to save.
     * @param tupperDTO the tupperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tupperDTO,
     * or with status {@code 400 (Bad Request)} if the tupperDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tupperDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tupperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tuppers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TupperDTO> partialUpdateTupper(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TupperDTO tupperDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tupper partially : {}, {}", id, tupperDTO);
        if (tupperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tupperDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tupperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TupperDTO> result = tupperService.partialUpdate(tupperDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tupperDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tuppers} : get all the tuppers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tuppers in body.
     */
    @GetMapping("/tuppers")
    public ResponseEntity<List<TupperDTO>> getAllTuppers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Tuppers");
        Page<TupperDTO> page = tupperService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tuppers/:id} : get the "id" tupper.
     *
     * @param id the id of the tupperDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tupperDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tuppers/{id}")
    public ResponseEntity<TupperDTO> getTupper(@PathVariable Long id) {
        log.debug("REST request to get Tupper : {}", id);
        Optional<TupperDTO> tupperDTO = tupperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tupperDTO);
    }

    /**
     * {@code DELETE  /tuppers/:id} : delete the "id" tupper.
     *
     * @param id the id of the tupperDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tuppers/{id}")
    public ResponseEntity<Void> deleteTupper(@PathVariable Long id) {
        log.debug("REST request to delete Tupper : {}", id);
        tupperService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
