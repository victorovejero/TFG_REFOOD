package com.refood.trazabilidad.web.rest;

import com.refood.trazabilidad.repository.PersonaBeneficiariaRepository;
import com.refood.trazabilidad.service.PersonaBeneficiariaService;
import com.refood.trazabilidad.service.dto.PersonaBeneficiariaDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.refood.trazabilidad.domain.PersonaBeneficiaria}.
 */
@RestController
@RequestMapping("/api")
public class PersonaBeneficiariaResource {

    private final Logger log = LoggerFactory.getLogger(PersonaBeneficiariaResource.class);

    private static final String ENTITY_NAME = "personaBeneficiaria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonaBeneficiariaService personaBeneficiariaService;

    private final PersonaBeneficiariaRepository personaBeneficiariaRepository;

    public PersonaBeneficiariaResource(
        PersonaBeneficiariaService personaBeneficiariaService,
        PersonaBeneficiariaRepository personaBeneficiariaRepository
    ) {
        this.personaBeneficiariaService = personaBeneficiariaService;
        this.personaBeneficiariaRepository = personaBeneficiariaRepository;
    }

    /**
     * {@code POST  /persona-beneficiarias} : Create a new personaBeneficiaria.
     *
     * @param personaBeneficiariaDTO the personaBeneficiariaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personaBeneficiariaDTO, or with status {@code 400 (Bad Request)} if the personaBeneficiaria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/persona-beneficiarias")
    public ResponseEntity<PersonaBeneficiariaDTO> createPersonaBeneficiaria(
        @Valid @RequestBody PersonaBeneficiariaDTO personaBeneficiariaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PersonaBeneficiaria : {}", personaBeneficiariaDTO);
        if (personaBeneficiariaDTO.getId() != null) {
            throw new BadRequestAlertException("A new personaBeneficiaria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonaBeneficiariaDTO result = personaBeneficiariaService.save(personaBeneficiariaDTO);
        return ResponseEntity
            .created(new URI("/api/persona-beneficiarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /persona-beneficiarias/:id} : Updates an existing personaBeneficiaria.
     *
     * @param id the id of the personaBeneficiariaDTO to save.
     * @param personaBeneficiariaDTO the personaBeneficiariaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaBeneficiariaDTO,
     * or with status {@code 400 (Bad Request)} if the personaBeneficiariaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personaBeneficiariaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/persona-beneficiarias/{id}")
    public ResponseEntity<PersonaBeneficiariaDTO> updatePersonaBeneficiaria(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonaBeneficiariaDTO personaBeneficiariaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonaBeneficiaria : {}, {}", id, personaBeneficiariaDTO);
        if (personaBeneficiariaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personaBeneficiariaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personaBeneficiariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonaBeneficiariaDTO result = personaBeneficiariaService.update(personaBeneficiariaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personaBeneficiariaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /persona-beneficiarias/:id} : Partial updates given fields of an existing personaBeneficiaria, field will ignore if it is null
     *
     * @param id the id of the personaBeneficiariaDTO to save.
     * @param personaBeneficiariaDTO the personaBeneficiariaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaBeneficiariaDTO,
     * or with status {@code 400 (Bad Request)} if the personaBeneficiariaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personaBeneficiariaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personaBeneficiariaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/persona-beneficiarias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonaBeneficiariaDTO> partialUpdatePersonaBeneficiaria(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonaBeneficiariaDTO personaBeneficiariaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonaBeneficiaria partially : {}, {}", id, personaBeneficiariaDTO);
        if (personaBeneficiariaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personaBeneficiariaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personaBeneficiariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonaBeneficiariaDTO> result = personaBeneficiariaService.partialUpdate(personaBeneficiariaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personaBeneficiariaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /persona-beneficiarias} : get all the personaBeneficiarias.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personaBeneficiarias in body.
     */
    @GetMapping("/persona-beneficiarias")
    public List<PersonaBeneficiariaDTO> getAllPersonaBeneficiarias(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all PersonaBeneficiarias");
        return personaBeneficiariaService.findAll();
    }

    /**
     * {@code GET  /persona-beneficiarias/:id} : get the "id" personaBeneficiaria.
     *
     * @param id the id of the personaBeneficiariaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personaBeneficiariaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/persona-beneficiarias/{id}")
    public ResponseEntity<PersonaBeneficiariaDTO> getPersonaBeneficiaria(@PathVariable Long id) {
        log.debug("REST request to get PersonaBeneficiaria : {}", id);
        Optional<PersonaBeneficiariaDTO> personaBeneficiariaDTO = personaBeneficiariaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personaBeneficiariaDTO);
    }

    /**
     * {@code DELETE  /persona-beneficiarias/:id} : delete the "id" personaBeneficiaria.
     *
     * @param id the id of the personaBeneficiariaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/persona-beneficiarias/{id}")
    public ResponseEntity<Void> deletePersonaBeneficiaria(@PathVariable Long id) {
        log.debug("REST request to delete PersonaBeneficiaria : {}", id);
        personaBeneficiariaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
