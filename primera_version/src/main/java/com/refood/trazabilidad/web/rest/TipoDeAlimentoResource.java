package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.TipoDeAlimentoRepository;
import com.refood.trazabilidad.service.TipoDeAlimentoService;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.TipoDeAlimento}.
 */
@RestController
@RequestMapping("/api")
public class TipoDeAlimentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDeAlimentoResource.class);

    private static final String ENTITY_NAME = "tipoDeAlimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDeAlimentoService tipoDeAlimentoService;

    private final TipoDeAlimentoRepository tipoDeAlimentoRepository;

    public TipoDeAlimentoResource(TipoDeAlimentoService tipoDeAlimentoService, TipoDeAlimentoRepository tipoDeAlimentoRepository) {
        this.tipoDeAlimentoService = tipoDeAlimentoService;
        this.tipoDeAlimentoRepository = tipoDeAlimentoRepository;
    }

    /**
     * {@code POST  /tipo-de-alimentos} : Create a new tipoDeAlimento.
     *
     * @param tipoDeAlimentoDTO the tipoDeAlimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDeAlimentoDTO, or with status {@code 400 (Bad Request)} if the tipoDeAlimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-de-alimentos")
    public ResponseEntity<TipoDeAlimentoDTO> createTipoDeAlimento(@Valid @RequestBody TipoDeAlimentoDTO tipoDeAlimentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save TipoDeAlimento : {}", tipoDeAlimentoDTO);
        if (tipoDeAlimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeAlimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeAlimentoDTO result = tipoDeAlimentoService.save(tipoDeAlimentoDTO);
        return ResponseEntity
            .created(new URI("/api/tipo-de-alimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-de-alimentos/:id} : Updates an existing tipoDeAlimento.
     *
     * @param id the id of the tipoDeAlimentoDTO to save.
     * @param tipoDeAlimentoDTO the tipoDeAlimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDeAlimentoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDeAlimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDeAlimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-de-alimentos/{id}")
    public ResponseEntity<TipoDeAlimentoDTO> updateTipoDeAlimento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoDeAlimentoDTO tipoDeAlimentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoDeAlimento : {}, {}", id, tipoDeAlimentoDTO);
        if (tipoDeAlimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDeAlimentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDeAlimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoDeAlimentoDTO result = tipoDeAlimentoService.update(tipoDeAlimentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDeAlimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-de-alimentos/:id} : Partial updates given fields of an existing tipoDeAlimento, field will ignore if it is null
     *
     * @param id the id of the tipoDeAlimentoDTO to save.
     * @param tipoDeAlimentoDTO the tipoDeAlimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDeAlimentoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDeAlimentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoDeAlimentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoDeAlimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-de-alimentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoDeAlimentoDTO> partialUpdateTipoDeAlimento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoDeAlimentoDTO tipoDeAlimentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoDeAlimento partially : {}, {}", id, tipoDeAlimentoDTO);
        if (tipoDeAlimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDeAlimentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDeAlimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoDeAlimentoDTO> result = tipoDeAlimentoService.partialUpdate(tipoDeAlimentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDeAlimentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-de-alimentos} : get all the tipoDeAlimentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDeAlimentos in body.
     */
    @GetMapping("/tipo-de-alimentos")
    public ResponseEntity<List<TipoDeAlimentoDTO>> getAllTipoDeAlimentos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoDeAlimentos");
        Page<TipoDeAlimentoDTO> page = tipoDeAlimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-de-alimentos/:id} : get the "id" tipoDeAlimento.
     *
     * @param id the id of the tipoDeAlimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDeAlimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-de-alimentos/{id}")
    public ResponseEntity<TipoDeAlimentoDTO> getTipoDeAlimento(@PathVariable Long id) {
        log.debug("REST request to get TipoDeAlimento : {}", id);
        Optional<TipoDeAlimentoDTO> tipoDeAlimentoDTO = tipoDeAlimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeAlimentoDTO);
    }

    /**
     * {@code DELETE  /tipo-de-alimentos/:id} : delete the "id" tipoDeAlimento.
     *
     * @param id the id of the tipoDeAlimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-de-alimentos/{id}")
    public ResponseEntity<Void> deleteTipoDeAlimento(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeAlimento : {}", id);
        tipoDeAlimentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
