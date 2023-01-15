package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.TipoAlRepository;
import com.refood.trazabilidad.service.TipoAlService;
import com.refood.trazabilidad.service.dto.TipoAlDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.TipoAl}.
 */
@RestController
@RequestMapping("/api")
public class TipoAlResource {

    private final Logger log = LoggerFactory.getLogger(TipoAlResource.class);

    private static final String ENTITY_NAME = "tipoAl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoAlService tipoAlService;

    private final TipoAlRepository tipoAlRepository;

    public TipoAlResource(TipoAlService tipoAlService, TipoAlRepository tipoAlRepository) {
        this.tipoAlService = tipoAlService;
        this.tipoAlRepository = tipoAlRepository;
    }

    /**
     * {@code POST  /tipo-als} : Create a new tipoAl.
     *
     * @param tipoAlDTO the tipoAlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoAlDTO, or with status {@code 400 (Bad Request)} if the tipoAl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-als")
    public ResponseEntity<TipoAlDTO> createTipoAl(@Valid @RequestBody TipoAlDTO tipoAlDTO) throws URISyntaxException {
        log.debug("REST request to save TipoAl : {}", tipoAlDTO);
        if (tipoAlDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoAl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoAlDTO result = tipoAlService.save(tipoAlDTO);
        return ResponseEntity
            .created(new URI("/api/tipo-als/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-als/:id} : Updates an existing tipoAl.
     *
     * @param id the id of the tipoAlDTO to save.
     * @param tipoAlDTO the tipoAlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoAlDTO,
     * or with status {@code 400 (Bad Request)} if the tipoAlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoAlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-als/{id}")
    public ResponseEntity<TipoAlDTO> updateTipoAl(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoAlDTO tipoAlDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoAl : {}, {}", id, tipoAlDTO);
        if (tipoAlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoAlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoAlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoAlDTO result = tipoAlService.update(tipoAlDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoAlDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-als/:id} : Partial updates given fields of an existing tipoAl, field will ignore if it is null
     *
     * @param id the id of the tipoAlDTO to save.
     * @param tipoAlDTO the tipoAlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoAlDTO,
     * or with status {@code 400 (Bad Request)} if the tipoAlDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoAlDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoAlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-als/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoAlDTO> partialUpdateTipoAl(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoAlDTO tipoAlDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoAl partially : {}, {}", id, tipoAlDTO);
        if (tipoAlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoAlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoAlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoAlDTO> result = tipoAlService.partialUpdate(tipoAlDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoAlDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-als} : get all the tipoAls.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoAls in body.
     */
    @GetMapping("/tipo-als")
    public ResponseEntity<List<TipoAlDTO>> getAllTipoAls(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of TipoAls");
        Page<TipoAlDTO> page;
        if (eagerload) {
            page = tipoAlService.findAllWithEagerRelationships(pageable);
        } else {
            page = tipoAlService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-als/:id} : get the "id" tipoAl.
     *
     * @param id the id of the tipoAlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoAlDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-als/{id}")
    public ResponseEntity<TipoAlDTO> getTipoAl(@PathVariable Long id) {
        log.debug("REST request to get TipoAl : {}", id);
        Optional<TipoAlDTO> tipoAlDTO = tipoAlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoAlDTO);
    }

    /**
     * {@code DELETE  /tipo-als/:id} : delete the "id" tipoAl.
     *
     * @param id the id of the tipoAlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-als/{id}")
    public ResponseEntity<Void> deleteTipoAl(@PathVariable Long id) {
        log.debug("REST request to delete TipoAl : {}", id);
        tipoAlService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
