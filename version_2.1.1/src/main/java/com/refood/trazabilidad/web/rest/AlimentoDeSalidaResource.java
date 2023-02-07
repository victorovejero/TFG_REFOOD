package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.domain.AlimentoDeSalida;

import com.refood.trazabilidad.repository.AlimentoDeSalidaRepository;
import com.refood.trazabilidad.service.AlimentoDeSalidaService;
import com.refood.trazabilidad.service.dto.AlimentoDeSalidaDTO;
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
 * REST controller for managing
 * {@link com.refood.trazabilidad.domain.AlimentoDeSalida}.
 */
@RestController
@RequestMapping("/api")
public class AlimentoDeSalidaResource {

    private final Logger log = LoggerFactory.getLogger(AlimentoDeSalidaResource.class);

    private static final String ENTITY_NAME = "alimentoDeSalida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlimentoDeSalidaService alimentoDeSalidaService;

    private final AlimentoDeSalidaRepository alimentoDeSalidaRepository;

    public AlimentoDeSalidaResource(
            AlimentoDeSalidaService alimentoDeSalidaService,
            AlimentoDeSalidaRepository alimentoDeSalidaRepository) {
        this.alimentoDeSalidaService = alimentoDeSalidaService;
        this.alimentoDeSalidaRepository = alimentoDeSalidaRepository;
    }

    // Implementación Endpoint para sacar todos los elementos de la tabla
    @GetMapping("/alimento-de-salida-all")
    public ResponseEntity<List<AlimentoDeSalida>> getAll() {
        List<AlimentoDeSalida> alimentosDeSalida = alimentoDeSalidaService.findAll();
        log.debug("REST request to get AlimentoDeSalida : {}", alimentosDeSalida);
        return ResponseEntity.ok().body(alimentosDeSalida);

    }

    /**
     * {@code POST  /alimento-de-salidas} : Create a new alimentoDeSalida.
     *
     * @param alimentoDeSalidaDTO the alimentoDeSalidaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new alimentoDeSalidaDTO, or with status
     *         {@code 400 (Bad Request)} if the alimentoDeSalida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alimento-de-salidas")
    public ResponseEntity<AlimentoDeSalidaDTO> createAlimentoDeSalida(
            @Valid @RequestBody AlimentoDeSalidaDTO alimentoDeSalidaDTO)
            throws URISyntaxException {
        log.debug("REST request to save AlimentoDeSalida : {}", alimentoDeSalidaDTO);
        if (alimentoDeSalidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new alimentoDeSalida cannot already have an ID", ENTITY_NAME,
                    "idexists");
        }
        AlimentoDeSalidaDTO result = alimentoDeSalidaService.save(alimentoDeSalidaDTO);
        return ResponseEntity
                .created(new URI("/api/alimento-de-salidas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                        result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /alimento-de-salidas/:id} : Updates an existing alimentoDeSalida.
     *
     * @param id                  the id of the alimentoDeSalidaDTO to save.
     * @param alimentoDeSalidaDTO the alimentoDeSalidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated alimentoDeSalidaDTO,
     *         or with status {@code 400 (Bad Request)} if the alimentoDeSalidaDTO
     *         is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         alimentoDeSalidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alimento-de-salidas/{id}")
    public ResponseEntity<AlimentoDeSalidaDTO> updateAlimentoDeSalida(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody AlimentoDeSalidaDTO alimentoDeSalidaDTO) throws URISyntaxException {
        log.debug("REST request to update AlimentoDeSalida : {}, {}", id, alimentoDeSalidaDTO);
        if (alimentoDeSalidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alimentoDeSalidaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alimentoDeSalidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlimentoDeSalidaDTO result = alimentoDeSalidaService.update(alimentoDeSalidaDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                        alimentoDeSalidaDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /alimento-de-salidas/:id} : Partial updates given fields of an
     * existing alimentoDeSalida, field will ignore if it is null
     *
     * @param id                  the id of the alimentoDeSalidaDTO to save.
     * @param alimentoDeSalidaDTO the alimentoDeSalidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated alimentoDeSalidaDTO,
     *         or with status {@code 400 (Bad Request)} if the alimentoDeSalidaDTO
     *         is not valid,
     *         or with status {@code 404 (Not Found)} if the alimentoDeSalidaDTO is
     *         not found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         alimentoDeSalidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alimento-de-salidas/{id}", consumes = { "application/json",
            "application/merge-patch+json" })
    public ResponseEntity<AlimentoDeSalidaDTO> partialUpdateAlimentoDeSalida(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody AlimentoDeSalidaDTO alimentoDeSalidaDTO) throws URISyntaxException {
        log.debug("REST request to partial update AlimentoDeSalida partially : {}, {}", id, alimentoDeSalidaDTO);
        if (alimentoDeSalidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alimentoDeSalidaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alimentoDeSalidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlimentoDeSalidaDTO> result = alimentoDeSalidaService.partialUpdate(alimentoDeSalidaDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                        alimentoDeSalidaDTO.getId().toString()));
    }

    /**
     * {@code GET  /alimento-de-salidas} : get all the alimentoDeSalidas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of alimentoDeSalidas in body.
     */
    @GetMapping("/alimento-de-salidas")
    public ResponseEntity<List<AlimentoDeSalidaDTO>> getAllAlimentoDeSalidas(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AlimentoDeSalidas");
        Page<AlimentoDeSalidaDTO> page = alimentoDeSalidaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alimento-de-salidas/:id} : get the "id" alimentoDeSalida.
     *
     * @param id the id of the alimentoDeSalidaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the alimentoDeSalidaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alimento-de-salidas/{id}")
    public ResponseEntity<AlimentoDeSalidaDTO> getAlimentoDeSalida(@PathVariable Long id) {
        log.debug("REST request to get AlimentoDeSalida : {}", id);
        Optional<AlimentoDeSalidaDTO> alimentoDeSalidaDTO = alimentoDeSalidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alimentoDeSalidaDTO);
    }

    /**
     * {@code DELETE  /alimento-de-salidas/:id} : delete the "id" alimentoDeSalida.
     *
     * @param id the id of the alimentoDeSalidaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alimento-de-salidas/{id}")
    public ResponseEntity<Void> deleteAlimentoDeSalida(@PathVariable Long id) {
        log.debug("REST request to delete AlimentoDeSalida : {}", id);
        alimentoDeSalidaService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
