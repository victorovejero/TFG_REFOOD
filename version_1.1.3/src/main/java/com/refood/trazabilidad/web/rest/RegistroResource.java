package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.RegistroRepository;
import com.refood.trazabilidad.service.RegistroService;
import com.refood.trazabilidad.service.dto.RegistroDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Registro}.
 */
@RestController
@RequestMapping("/api")
public class RegistroResource {

    private final Logger log = LoggerFactory.getLogger(RegistroResource.class);

    private static final String ENTITY_NAME = "registro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistroService registroService;

    private final RegistroRepository registroRepository;

    public RegistroResource(RegistroService registroService, RegistroRepository registroRepository) {
        this.registroService = registroService;
        this.registroRepository = registroRepository;
    }

    /**
     * {@code POST  /registros} : Create a new registro.
     *
     * @param registroDTO the registroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registroDTO, or with status {@code 400 (Bad Request)} if the registro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registros")
    public ResponseEntity<RegistroDTO> createRegistro(@Valid @RequestBody RegistroDTO registroDTO) throws URISyntaxException {
        log.debug("REST request to save Registro : {}", registroDTO);
        if (registroDTO.getId() != null) {
            throw new BadRequestAlertException("A new registro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistroDTO result = registroService.save(registroDTO);
        return ResponseEntity
            .created(new URI("/api/registros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registros/:id} : Updates an existing registro.
     *
     * @param id the id of the registroDTO to save.
     * @param registroDTO the registroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroDTO,
     * or with status {@code 400 (Bad Request)} if the registroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registros/{id}")
    public ResponseEntity<RegistroDTO> updateRegistro(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegistroDTO registroDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Registro : {}, {}", id, registroDTO);
        if (registroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registroDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegistroDTO result = registroService.update(registroDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, registroDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /registros/:id} : Partial updates given fields of an existing registro, field will ignore if it is null
     *
     * @param id the id of the registroDTO to save.
     * @param registroDTO the registroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroDTO,
     * or with status {@code 400 (Bad Request)} if the registroDTO is not valid,
     * or with status {@code 404 (Not Found)} if the registroDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the registroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/registros/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegistroDTO> partialUpdateRegistro(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegistroDTO registroDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Registro partially : {}, {}", id, registroDTO);
        if (registroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registroDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegistroDTO> result = registroService.partialUpdate(registroDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, registroDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /registros} : get all the registros.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registros in body.
     */
    @GetMapping("/registros")
    public ResponseEntity<List<RegistroDTO>> getAllRegistros(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Registros");
        Page<RegistroDTO> page;
        if (eagerload) {
            page = registroService.findAllWithEagerRelationships(pageable);
        } else {
            page = registroService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /registros/:id} : get the "id" registro.
     *
     * @param id the id of the registroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registros/{id}")
    public ResponseEntity<RegistroDTO> getRegistro(@PathVariable Long id) {
        log.debug("REST request to get Registro : {}", id);
        Optional<RegistroDTO> registroDTO = registroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registroDTO);
    }

    /**
     * {@code DELETE  /registros/:id} : delete the "id" registro.
     *
     * @param id the id of the registroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registros/{id}")
    public ResponseEntity<Void> deleteRegistro(@PathVariable Long id) {
        log.debug("REST request to delete Registro : {}", id);
        registroService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
