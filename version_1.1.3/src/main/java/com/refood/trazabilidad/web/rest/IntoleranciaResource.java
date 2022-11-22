package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.IntoleranciaRepository;
import com.refood.trazabilidad.service.IntoleranciaService;
import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Intolerancia}.
 */
@RestController
@RequestMapping("/api")
public class IntoleranciaResource {

    private final Logger log = LoggerFactory.getLogger(IntoleranciaResource.class);

    private static final String ENTITY_NAME = "intolerancia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntoleranciaService intoleranciaService;

    private final IntoleranciaRepository intoleranciaRepository;

    public IntoleranciaResource(IntoleranciaService intoleranciaService, IntoleranciaRepository intoleranciaRepository) {
        this.intoleranciaService = intoleranciaService;
        this.intoleranciaRepository = intoleranciaRepository;
    }

    /**
     * {@code POST  /intolerancias} : Create a new intolerancia.
     *
     * @param intoleranciaDTO the intoleranciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intoleranciaDTO, or with status {@code 400 (Bad Request)} if the intolerancia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intolerancias")
    public ResponseEntity<IntoleranciaDTO> createIntolerancia(@Valid @RequestBody IntoleranciaDTO intoleranciaDTO)
        throws URISyntaxException {
        log.debug("REST request to save Intolerancia : {}", intoleranciaDTO);
        if (intoleranciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new intolerancia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntoleranciaDTO result = intoleranciaService.save(intoleranciaDTO);
        return ResponseEntity
            .created(new URI("/api/intolerancias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intolerancias/:id} : Updates an existing intolerancia.
     *
     * @param id the id of the intoleranciaDTO to save.
     * @param intoleranciaDTO the intoleranciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intoleranciaDTO,
     * or with status {@code 400 (Bad Request)} if the intoleranciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intoleranciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intolerancias/{id}")
    public ResponseEntity<IntoleranciaDTO> updateIntolerancia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntoleranciaDTO intoleranciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Intolerancia : {}, {}", id, intoleranciaDTO);
        if (intoleranciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intoleranciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intoleranciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IntoleranciaDTO result = intoleranciaService.update(intoleranciaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intoleranciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /intolerancias/:id} : Partial updates given fields of an existing intolerancia, field will ignore if it is null
     *
     * @param id the id of the intoleranciaDTO to save.
     * @param intoleranciaDTO the intoleranciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intoleranciaDTO,
     * or with status {@code 400 (Bad Request)} if the intoleranciaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the intoleranciaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the intoleranciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/intolerancias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntoleranciaDTO> partialUpdateIntolerancia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntoleranciaDTO intoleranciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Intolerancia partially : {}, {}", id, intoleranciaDTO);
        if (intoleranciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intoleranciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intoleranciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntoleranciaDTO> result = intoleranciaService.partialUpdate(intoleranciaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intoleranciaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /intolerancias} : get all the intolerancias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intolerancias in body.
     */
    @GetMapping("/intolerancias")
    public ResponseEntity<List<IntoleranciaDTO>> getAllIntolerancias(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Intolerancias");
        Page<IntoleranciaDTO> page = intoleranciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intolerancias/:id} : get the "id" intolerancia.
     *
     * @param id the id of the intoleranciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intoleranciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intolerancias/{id}")
    public ResponseEntity<IntoleranciaDTO> getIntolerancia(@PathVariable Long id) {
        log.debug("REST request to get Intolerancia : {}", id);
        Optional<IntoleranciaDTO> intoleranciaDTO = intoleranciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intoleranciaDTO);
    }

    /**
     * {@code DELETE  /intolerancias/:id} : delete the "id" intolerancia.
     *
     * @param id the id of the intoleranciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intolerancias/{id}")
    public ResponseEntity<Void> deleteIntolerancia(@PathVariable Long id) {
        log.debug("REST request to delete Intolerancia : {}", id);
        intoleranciaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
