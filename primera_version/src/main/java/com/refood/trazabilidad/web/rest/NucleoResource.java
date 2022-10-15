package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.NucleoRepository;
import com.refood.trazabilidad.service.NucleoService;
import com.refood.trazabilidad.service.dto.NucleoDTO;
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
 * REST controller for managing {@link com.refood.trazabilidad.domain.Nucleo}.
 */
@RestController
@RequestMapping("/api")
public class NucleoResource {

    private final Logger log = LoggerFactory.getLogger(NucleoResource.class);

    private static final String ENTITY_NAME = "nucleo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NucleoService nucleoService;

    private final NucleoRepository nucleoRepository;

    public NucleoResource(NucleoService nucleoService, NucleoRepository nucleoRepository) {
        this.nucleoService = nucleoService;
        this.nucleoRepository = nucleoRepository;
    }

    /**
     * {@code POST  /nucleos} : Create a new nucleo.
     *
     * @param nucleoDTO the nucleoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nucleoDTO, or with status {@code 400 (Bad Request)} if the nucleo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nucleos")
    public ResponseEntity<NucleoDTO> createNucleo(@Valid @RequestBody NucleoDTO nucleoDTO) throws URISyntaxException {
        log.debug("REST request to save Nucleo : {}", nucleoDTO);
        if (nucleoDTO.getId() != null) {
            throw new BadRequestAlertException("A new nucleo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NucleoDTO result = nucleoService.save(nucleoDTO);
        return ResponseEntity
            .created(new URI("/api/nucleos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nucleos/:id} : Updates an existing nucleo.
     *
     * @param id the id of the nucleoDTO to save.
     * @param nucleoDTO the nucleoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nucleoDTO,
     * or with status {@code 400 (Bad Request)} if the nucleoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nucleoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nucleos/{id}")
    public ResponseEntity<NucleoDTO> updateNucleo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NucleoDTO nucleoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Nucleo : {}, {}", id, nucleoDTO);
        if (nucleoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nucleoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nucleoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NucleoDTO result = nucleoService.update(nucleoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nucleoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nucleos/:id} : Partial updates given fields of an existing nucleo, field will ignore if it is null
     *
     * @param id the id of the nucleoDTO to save.
     * @param nucleoDTO the nucleoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nucleoDTO,
     * or with status {@code 400 (Bad Request)} if the nucleoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nucleoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nucleoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nucleos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NucleoDTO> partialUpdateNucleo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NucleoDTO nucleoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nucleo partially : {}, {}", id, nucleoDTO);
        if (nucleoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nucleoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nucleoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NucleoDTO> result = nucleoService.partialUpdate(nucleoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nucleoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nucleos} : get all the nucleos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nucleos in body.
     */
    @GetMapping("/nucleos")
    public ResponseEntity<List<NucleoDTO>> getAllNucleos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Nucleos");
        Page<NucleoDTO> page = nucleoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nucleos/:id} : get the "id" nucleo.
     *
     * @param id the id of the nucleoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nucleoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nucleos/{id}")
    public ResponseEntity<NucleoDTO> getNucleo(@PathVariable Long id) {
        log.debug("REST request to get Nucleo : {}", id);
        Optional<NucleoDTO> nucleoDTO = nucleoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nucleoDTO);
    }

    /**
     * {@code DELETE  /nucleos/:id} : delete the "id" nucleo.
     *
     * @param id the id of the nucleoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nucleos/{id}")
    public ResponseEntity<Void> deleteNucleo(@PathVariable Long id) {
        log.debug("REST request to delete Nucleo : {}", id);
        nucleoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
