package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.domain.Donante;

import com.refood.trazabilidad.repository.DonanteRepository;
import com.refood.trazabilidad.service.DonanteService;
import com.refood.trazabilidad.service.dto.DonanteDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Donante}.
 */
@RestController
@RequestMapping("/api")
public class DonanteResource {

    private final Logger log = LoggerFactory.getLogger(DonanteResource.class);

    private static final String ENTITY_NAME = "donante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonanteService donanteService;

    private final DonanteRepository donanteRepository;

    public DonanteResource(DonanteService donanteService, DonanteRepository donanteRepository) {
        this.donanteService = donanteService;
        this.donanteRepository = donanteRepository;
    }

    @GetMapping("/donantes-all")
    public ResponseEntity<List<Donante>> getAll() {
        List<Donante> donantes = donanteService.findAll();
        log.debug("REST request to get Donantes : {}", donantes);
        return ResponseEntity.ok().body(donantes);
    }

    /**
     * {@code POST  /donantes} : Create a new donante.
     *
     * @param donanteDTO the donanteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donanteDTO, or with status {@code 400 (Bad Request)} if the donante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donantes")
    public ResponseEntity<DonanteDTO> createDonante(@Valid @RequestBody DonanteDTO donanteDTO) throws URISyntaxException {
        log.debug("REST request to save Donante : {}", donanteDTO);
        if (donanteDTO.getId() != null) {
            throw new BadRequestAlertException("A new donante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonanteDTO result = donanteService.save(donanteDTO);
        return ResponseEntity
            .created(new URI("/api/donantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donantes/:id} : Updates an existing donante.
     *
     * @param id the id of the donanteDTO to save.
     * @param donanteDTO the donanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donanteDTO,
     * or with status {@code 400 (Bad Request)} if the donanteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donantes/{id}")
    public ResponseEntity<DonanteDTO> updateDonante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DonanteDTO donanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Donante : {}, {}", id, donanteDTO);
        if (donanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DonanteDTO result = donanteService.update(donanteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, donanteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /donantes/:id} : Partial updates given fields of an existing donante, field will ignore if it is null
     *
     * @param id the id of the donanteDTO to save.
     * @param donanteDTO the donanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donanteDTO,
     * or with status {@code 400 (Bad Request)} if the donanteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donanteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/donantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonanteDTO> partialUpdateDonante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DonanteDTO donanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Donante partially : {}, {}", id, donanteDTO);
        if (donanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonanteDTO> result = donanteService.partialUpdate(donanteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, donanteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /donantes} : get all the donantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donantes in body.
     */
    @GetMapping("/donantes")
    public ResponseEntity<List<DonanteDTO>> getAllDonantes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Donantes");
        Page<DonanteDTO> page = donanteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donantes/:id} : get the "id" donante.
     *
     * @param id the id of the donanteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donanteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donantes/{id}")
    public ResponseEntity<DonanteDTO> getDonante(@PathVariable Long id) {
        log.debug("REST request to get Donante : {}", id);
        Optional<DonanteDTO> donanteDTO = donanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donanteDTO);
    }

    /**
     * {@code DELETE  /donantes/:id} : delete the "id" donante.
     *
     * @param id the id of the donanteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donantes/{id}")
    public ResponseEntity<Void> deleteDonante(@PathVariable Long id) {
        log.debug("REST request to delete Donante : {}", id);
        donanteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
