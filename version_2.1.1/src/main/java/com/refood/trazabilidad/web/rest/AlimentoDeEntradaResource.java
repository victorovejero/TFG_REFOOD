package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.AlimentoDeEntradaRepository;
import com.refood.trazabilidad.service.AlimentoDeEntradaService;
import com.refood.trazabilidad.service.dto.AlimentoDeEntradaDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.AlimentoDeEntrada}.
 */
@RestController
@RequestMapping("/api")
public class AlimentoDeEntradaResource {

    private final Logger log = LoggerFactory.getLogger(AlimentoDeEntradaResource.class);

    private static final String ENTITY_NAME = "alimentoDeEntrada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlimentoDeEntradaService alimentoDeEntradaService;

    private final AlimentoDeEntradaRepository alimentoDeEntradaRepository;

    public AlimentoDeEntradaResource(
        AlimentoDeEntradaService alimentoDeEntradaService,
        AlimentoDeEntradaRepository alimentoDeEntradaRepository
    ) {
        this.alimentoDeEntradaService = alimentoDeEntradaService;
        this.alimentoDeEntradaRepository = alimentoDeEntradaRepository;
    }

    /**
     * {@code POST  /alimento-de-entradas} : Create a new alimentoDeEntrada.
     *
     * @param alimentoDeEntradaDTO the alimentoDeEntradaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alimentoDeEntradaDTO, or with status {@code 400 (Bad Request)} if the alimentoDeEntrada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alimento-de-entradas")
    public ResponseEntity<AlimentoDeEntradaDTO> createAlimentoDeEntrada(@Valid @RequestBody AlimentoDeEntradaDTO alimentoDeEntradaDTO)
        throws URISyntaxException {
        log.debug("REST request to save AlimentoDeEntrada : {}", alimentoDeEntradaDTO);
        if (alimentoDeEntradaDTO.getId() != null) {
            throw new BadRequestAlertException("A new alimentoDeEntrada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlimentoDeEntradaDTO result = alimentoDeEntradaService.save(alimentoDeEntradaDTO);
        return ResponseEntity
            .created(new URI("/api/alimento-de-entradas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alimento-de-entradas/:id} : Updates an existing alimentoDeEntrada.
     *
     * @param id the id of the alimentoDeEntradaDTO to save.
     * @param alimentoDeEntradaDTO the alimentoDeEntradaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alimentoDeEntradaDTO,
     * or with status {@code 400 (Bad Request)} if the alimentoDeEntradaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alimentoDeEntradaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alimento-de-entradas/{id}")
    public ResponseEntity<AlimentoDeEntradaDTO> updateAlimentoDeEntrada(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlimentoDeEntradaDTO alimentoDeEntradaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AlimentoDeEntrada : {}, {}", id, alimentoDeEntradaDTO);
        if (alimentoDeEntradaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alimentoDeEntradaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alimentoDeEntradaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlimentoDeEntradaDTO result = alimentoDeEntradaService.update(alimentoDeEntradaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alimentoDeEntradaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alimento-de-entradas/:id} : Partial updates given fields of an existing alimentoDeEntrada, field will ignore if it is null
     *
     * @param id the id of the alimentoDeEntradaDTO to save.
     * @param alimentoDeEntradaDTO the alimentoDeEntradaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alimentoDeEntradaDTO,
     * or with status {@code 400 (Bad Request)} if the alimentoDeEntradaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alimentoDeEntradaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alimentoDeEntradaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alimento-de-entradas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlimentoDeEntradaDTO> partialUpdateAlimentoDeEntrada(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlimentoDeEntradaDTO alimentoDeEntradaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AlimentoDeEntrada partially : {}, {}", id, alimentoDeEntradaDTO);
        if (alimentoDeEntradaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alimentoDeEntradaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alimentoDeEntradaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlimentoDeEntradaDTO> result = alimentoDeEntradaService.partialUpdate(alimentoDeEntradaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alimentoDeEntradaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alimento-de-entradas} : get all the alimentoDeEntradas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alimentoDeEntradas in body.
     */
    @GetMapping("/alimento-de-entradas")
    public ResponseEntity<List<AlimentoDeEntradaDTO>> getAllAlimentoDeEntradas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of AlimentoDeEntradas");
        Page<AlimentoDeEntradaDTO> page;
        if (eagerload) {
            page = alimentoDeEntradaService.findAllWithEagerRelationships(pageable);
        } else {
            page = alimentoDeEntradaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alimento-de-entradas/:id} : get the "id" alimentoDeEntrada.
     *
     * @param id the id of the alimentoDeEntradaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alimentoDeEntradaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alimento-de-entradas/{id}")
    public ResponseEntity<AlimentoDeEntradaDTO> getAlimentoDeEntrada(@PathVariable Long id) {
        log.debug("REST request to get AlimentoDeEntrada : {}", id);
        Optional<AlimentoDeEntradaDTO> alimentoDeEntradaDTO = alimentoDeEntradaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alimentoDeEntradaDTO);
    }

    /**
     * {@code DELETE  /alimento-de-entradas/:id} : delete the "id" alimentoDeEntrada.
     *
     * @param id the id of the alimentoDeEntradaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alimento-de-entradas/{id}")
    public ResponseEntity<Void> deleteAlimentoDeEntrada(@PathVariable Long id) {
        log.debug("REST request to delete AlimentoDeEntrada : {}", id);
        alimentoDeEntradaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
