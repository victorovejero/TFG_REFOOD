package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.PersonaBeneficiaria;
import com.refood.trazabilidad.repository.PersonaBeneficiariaRepository;
import com.refood.trazabilidad.service.PersonaBeneficiariaService;
import com.refood.trazabilidad.service.dto.PersonaBeneficiariaDTO;
import com.refood.trazabilidad.service.mapper.PersonaBeneficiariaMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonaBeneficiariaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PersonaBeneficiariaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_NACIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_NACIMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_PARENTESCO = "BBBBBBBBBB";

    private static final String DEFAULT_SITUACION_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_SITUACION_PROFESIONAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/persona-beneficiarias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonaBeneficiariaRepository personaBeneficiariaRepository;

    @Mock
    private PersonaBeneficiariaRepository personaBeneficiariaRepositoryMock;

    @Autowired
    private PersonaBeneficiariaMapper personaBeneficiariaMapper;

    @Mock
    private PersonaBeneficiariaService personaBeneficiariaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonaBeneficiariaMockMvc;

    private PersonaBeneficiaria personaBeneficiaria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonaBeneficiaria createEntity(EntityManager em) {
        PersonaBeneficiaria personaBeneficiaria = new PersonaBeneficiaria()
            .nombre(DEFAULT_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .sexo(DEFAULT_SEXO)
            .parentesco(DEFAULT_PARENTESCO)
            .situacionProfesional(DEFAULT_SITUACION_PROFESIONAL);
        return personaBeneficiaria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonaBeneficiaria createUpdatedEntity(EntityManager em) {
        PersonaBeneficiaria personaBeneficiaria = new PersonaBeneficiaria()
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .situacionProfesional(UPDATED_SITUACION_PROFESIONAL);
        return personaBeneficiaria;
    }

    @BeforeEach
    public void initTest() {
        personaBeneficiaria = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonaBeneficiaria() throws Exception {
        int databaseSizeBeforeCreate = personaBeneficiariaRepository.findAll().size();
        // Create the PersonaBeneficiaria
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);
        restPersonaBeneficiariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeCreate + 1);
        PersonaBeneficiaria testPersonaBeneficiaria = personaBeneficiariaList.get(personaBeneficiariaList.size() - 1);
        assertThat(testPersonaBeneficiaria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersonaBeneficiaria.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testPersonaBeneficiaria.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testPersonaBeneficiaria.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPersonaBeneficiaria.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPersonaBeneficiaria.getParentesco()).isEqualTo(DEFAULT_PARENTESCO);
        assertThat(testPersonaBeneficiaria.getSituacionProfesional()).isEqualTo(DEFAULT_SITUACION_PROFESIONAL);
    }

    @Test
    @Transactional
    void createPersonaBeneficiariaWithExistingId() throws Exception {
        // Create the PersonaBeneficiaria with an existing ID
        personaBeneficiaria.setId(1L);
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        int databaseSizeBeforeCreate = personaBeneficiariaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaBeneficiariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaBeneficiariaRepository.findAll().size();
        // set the field null
        personaBeneficiaria.setNombre(null);

        // Create the PersonaBeneficiaria, which fails.
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        restPersonaBeneficiariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimerApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaBeneficiariaRepository.findAll().size();
        // set the field null
        personaBeneficiaria.setPrimerApellido(null);

        // Create the PersonaBeneficiaria, which fails.
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        restPersonaBeneficiariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaBeneficiariaRepository.findAll().size();
        // set the field null
        personaBeneficiaria.setFechaNacimiento(null);

        // Create the PersonaBeneficiaria, which fails.
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        restPersonaBeneficiariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaBeneficiariaRepository.findAll().size();
        // set the field null
        personaBeneficiaria.setSexo(null);

        // Create the PersonaBeneficiaria, which fails.
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        restPersonaBeneficiariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkParentescoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaBeneficiariaRepository.findAll().size();
        // set the field null
        personaBeneficiaria.setParentesco(null);

        // Create the PersonaBeneficiaria, which fails.
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        restPersonaBeneficiariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSituacionProfesionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaBeneficiariaRepository.findAll().size();
        // set the field null
        personaBeneficiaria.setSituacionProfesional(null);

        // Create the PersonaBeneficiaria, which fails.
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        restPersonaBeneficiariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonaBeneficiarias() throws Exception {
        // Initialize the database
        personaBeneficiariaRepository.saveAndFlush(personaBeneficiaria);

        // Get all the personaBeneficiariaList
        restPersonaBeneficiariaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personaBeneficiaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].parentesco").value(hasItem(DEFAULT_PARENTESCO)))
            .andExpect(jsonPath("$.[*].situacionProfesional").value(hasItem(DEFAULT_SITUACION_PROFESIONAL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPersonaBeneficiariasWithEagerRelationshipsIsEnabled() throws Exception {
        when(personaBeneficiariaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonaBeneficiariaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(personaBeneficiariaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPersonaBeneficiariasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(personaBeneficiariaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonaBeneficiariaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(personaBeneficiariaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPersonaBeneficiaria() throws Exception {
        // Initialize the database
        personaBeneficiariaRepository.saveAndFlush(personaBeneficiaria);

        // Get the personaBeneficiaria
        restPersonaBeneficiariaMockMvc
            .perform(get(ENTITY_API_URL_ID, personaBeneficiaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personaBeneficiaria.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.parentesco").value(DEFAULT_PARENTESCO))
            .andExpect(jsonPath("$.situacionProfesional").value(DEFAULT_SITUACION_PROFESIONAL));
    }

    @Test
    @Transactional
    void getNonExistingPersonaBeneficiaria() throws Exception {
        // Get the personaBeneficiaria
        restPersonaBeneficiariaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonaBeneficiaria() throws Exception {
        // Initialize the database
        personaBeneficiariaRepository.saveAndFlush(personaBeneficiaria);

        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();

        // Update the personaBeneficiaria
        PersonaBeneficiaria updatedPersonaBeneficiaria = personaBeneficiariaRepository.findById(personaBeneficiaria.getId()).get();
        // Disconnect from session so that the updates on updatedPersonaBeneficiaria are not directly saved in db
        em.detach(updatedPersonaBeneficiaria);
        updatedPersonaBeneficiaria
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .situacionProfesional(UPDATED_SITUACION_PROFESIONAL);
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(updatedPersonaBeneficiaria);

        restPersonaBeneficiariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personaBeneficiariaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
        PersonaBeneficiaria testPersonaBeneficiaria = personaBeneficiariaList.get(personaBeneficiariaList.size() - 1);
        assertThat(testPersonaBeneficiaria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonaBeneficiaria.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testPersonaBeneficiaria.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testPersonaBeneficiaria.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPersonaBeneficiaria.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPersonaBeneficiaria.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testPersonaBeneficiaria.getSituacionProfesional()).isEqualTo(UPDATED_SITUACION_PROFESIONAL);
    }

    @Test
    @Transactional
    void putNonExistingPersonaBeneficiaria() throws Exception {
        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();
        personaBeneficiaria.setId(count.incrementAndGet());

        // Create the PersonaBeneficiaria
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaBeneficiariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personaBeneficiariaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonaBeneficiaria() throws Exception {
        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();
        personaBeneficiaria.setId(count.incrementAndGet());

        // Create the PersonaBeneficiaria
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaBeneficiariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonaBeneficiaria() throws Exception {
        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();
        personaBeneficiaria.setId(count.incrementAndGet());

        // Create the PersonaBeneficiaria
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaBeneficiariaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonaBeneficiariaWithPatch() throws Exception {
        // Initialize the database
        personaBeneficiariaRepository.saveAndFlush(personaBeneficiaria);

        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();

        // Update the personaBeneficiaria using partial update
        PersonaBeneficiaria partialUpdatedPersonaBeneficiaria = new PersonaBeneficiaria();
        partialUpdatedPersonaBeneficiaria.setId(personaBeneficiaria.getId());

        partialUpdatedPersonaBeneficiaria
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .situacionProfesional(UPDATED_SITUACION_PROFESIONAL);

        restPersonaBeneficiariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonaBeneficiaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonaBeneficiaria))
            )
            .andExpect(status().isOk());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
        PersonaBeneficiaria testPersonaBeneficiaria = personaBeneficiariaList.get(personaBeneficiariaList.size() - 1);
        assertThat(testPersonaBeneficiaria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersonaBeneficiaria.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testPersonaBeneficiaria.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testPersonaBeneficiaria.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPersonaBeneficiaria.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPersonaBeneficiaria.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testPersonaBeneficiaria.getSituacionProfesional()).isEqualTo(UPDATED_SITUACION_PROFESIONAL);
    }

    @Test
    @Transactional
    void fullUpdatePersonaBeneficiariaWithPatch() throws Exception {
        // Initialize the database
        personaBeneficiariaRepository.saveAndFlush(personaBeneficiaria);

        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();

        // Update the personaBeneficiaria using partial update
        PersonaBeneficiaria partialUpdatedPersonaBeneficiaria = new PersonaBeneficiaria();
        partialUpdatedPersonaBeneficiaria.setId(personaBeneficiaria.getId());

        partialUpdatedPersonaBeneficiaria
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .situacionProfesional(UPDATED_SITUACION_PROFESIONAL);

        restPersonaBeneficiariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonaBeneficiaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonaBeneficiaria))
            )
            .andExpect(status().isOk());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
        PersonaBeneficiaria testPersonaBeneficiaria = personaBeneficiariaList.get(personaBeneficiariaList.size() - 1);
        assertThat(testPersonaBeneficiaria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonaBeneficiaria.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testPersonaBeneficiaria.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testPersonaBeneficiaria.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPersonaBeneficiaria.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPersonaBeneficiaria.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testPersonaBeneficiaria.getSituacionProfesional()).isEqualTo(UPDATED_SITUACION_PROFESIONAL);
    }

    @Test
    @Transactional
    void patchNonExistingPersonaBeneficiaria() throws Exception {
        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();
        personaBeneficiaria.setId(count.incrementAndGet());

        // Create the PersonaBeneficiaria
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaBeneficiariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personaBeneficiariaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonaBeneficiaria() throws Exception {
        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();
        personaBeneficiaria.setId(count.incrementAndGet());

        // Create the PersonaBeneficiaria
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaBeneficiariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonaBeneficiaria() throws Exception {
        int databaseSizeBeforeUpdate = personaBeneficiariaRepository.findAll().size();
        personaBeneficiaria.setId(count.incrementAndGet());

        // Create the PersonaBeneficiaria
        PersonaBeneficiariaDTO personaBeneficiariaDTO = personaBeneficiariaMapper.toDto(personaBeneficiaria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaBeneficiariaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personaBeneficiariaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonaBeneficiaria in the database
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonaBeneficiaria() throws Exception {
        // Initialize the database
        personaBeneficiariaRepository.saveAndFlush(personaBeneficiaria);

        int databaseSizeBeforeDelete = personaBeneficiariaRepository.findAll().size();

        // Delete the personaBeneficiaria
        restPersonaBeneficiariaMockMvc
            .perform(delete(ENTITY_API_URL_ID, personaBeneficiaria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonaBeneficiaria> personaBeneficiariaList = personaBeneficiariaRepository.findAll();
        assertThat(personaBeneficiariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
