package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.SocioRepository;
import com.refood.trazabilidad.service.SocioService;
import com.refood.trazabilidad.service.dto.SocioDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Socio}.
 */
@RestController
@RequestMapping("/api")
public class SocioResource {

    private final Logger log = LoggerFactory.getLogger(SocioResource.class);

    private static final String ENTITY_NAME = "socio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocioService socioService;

    private final SocioRepository socioRepository;

    public SocioResource(SocioService socioService, SocioRepository socioRepository) {
        this.socioService = socioService;
        this.socioRepository = socioRepository;
    }

    /**
     * {@code POST  /socios} : Create a new socio.
     *
     * @param socioDTO the socioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socioDTO, or with status {@code 400 (Bad Request)} if the socio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/socios")
    public ResponseEntity<SocioDTO> createSocio(@Valid @RequestBody SocioDTO socioDTO) throws URISyntaxException {
        log.debug("REST request to save Socio : {}", socioDTO);
        if (socioDTO.getId() != null) {
            throw new BadRequestAlertException("A new socio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocioDTO result = socioService.save(socioDTO);
        return ResponseEntity
            .created(new URI("/api/socios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /socios/:id} : Updates an existing socio.
     *
     * @param id the id of the socioDTO to save.
     * @param socioDTO the socioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socioDTO,
     * or with status {@code 400 (Bad Request)} if the socioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/socios/{id}")
    public ResponseEntity<SocioDTO> updateSocio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SocioDTO socioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Socio : {}, {}", id, socioDTO);
        if (socioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocioDTO result = socioService.update(socioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, socioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /socios/:id} : Partial updates given fields of an existing socio, field will ignore if it is null
     *
     * @param id the id of the socioDTO to save.
     * @param socioDTO the socioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socioDTO,
     * or with status {@code 400 (Bad Request)} if the socioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the socioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the socioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/socios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocioDTO> partialUpdateSocio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SocioDTO socioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Socio partially : {}, {}", id, socioDTO);
        if (socioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocioDTO> result = socioService.partialUpdate(socioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, socioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /socios} : get all the socios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socios in body.
     */
    @GetMapping("/socios")
    public ResponseEntity<List<SocioDTO>> getAllSocios(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Socios");
        Page<SocioDTO> page = socioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /socios/:id} : get the "id" socio.
     *
     * @param id the id of the socioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/socios/{id}")
    public ResponseEntity<SocioDTO> getSocio(@PathVariable Long id) {
        log.debug("REST request to get Socio : {}", id);
        Optional<SocioDTO> socioDTO = socioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socioDTO);
    }

    /**
     * {@code DELETE  /socios/:id} : delete the "id" socio.
     *
     * @param id the id of the socioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/socios/{id}")
    public ResponseEntity<Void> deleteSocio(@PathVariable Long id) {
        log.debug("REST request to delete Socio : {}", id);
        socioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
