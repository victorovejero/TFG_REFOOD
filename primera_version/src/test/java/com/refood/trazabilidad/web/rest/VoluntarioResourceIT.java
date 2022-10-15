package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Voluntario;
import com.refood.trazabilidad.repository.VoluntarioRepository;
import com.refood.trazabilidad.service.dto.VoluntarioDTO;
import com.refood.trazabilidad.service.mapper.VoluntarioMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VoluntarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoluntarioResourceIT {

    private static final String DEFAULT_NOMBRE_VOLUNTARIO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_VOLUNTARIO = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_VOLUNTARIO = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_VOLUNTARIO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO_VOLUNTARIO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO_VOLUNTARIO = "BBBBBBBBBB";

    private static final String DEFAULT_DNI_VOLUNTARIO = "AAAAAAAAAA";
    private static final String UPDATED_DNI_VOLUNTARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO_VOLUNTARIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO_VOLUNTARIO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEXO_VOLUNTARIO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO_VOLUNTARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_BAJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_BAJA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIPO_VOLUNTARIO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_VOLUNTARIO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_TURNO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_TURNO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RESPONSABLE_DIA = false;
    private static final Boolean UPDATED_RESPONSABLE_DIA = true;

    private static final String DEFAULT_ORIGEN_VOLUNTARIO = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEN_VOLUNTARIO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANIPULADOR_ALIMENTOS = false;
    private static final Boolean UPDATED_MANIPULADOR_ALIMENTOS = true;

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voluntarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private VoluntarioMapper voluntarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoluntarioMockMvc;

    private Voluntario voluntario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voluntario createEntity(EntityManager em) {
        Voluntario voluntario = new Voluntario()
            .nombreVoluntario(DEFAULT_NOMBRE_VOLUNTARIO)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .emailVoluntario(DEFAULT_EMAIL_VOLUNTARIO)
            .telefonoContactoVoluntario(DEFAULT_TELEFONO_CONTACTO_VOLUNTARIO)
            .dniVoluntario(DEFAULT_DNI_VOLUNTARIO)
            .fechaNacimientoVoluntario(DEFAULT_FECHA_NACIMIENTO_VOLUNTARIO)
            .sexoVoluntario(DEFAULT_SEXO_VOLUNTARIO)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaBaja(DEFAULT_FECHA_BAJA)
            .tipoVoluntario(DEFAULT_TIPO_VOLUNTARIO)
            .tipoTurno(DEFAULT_TIPO_TURNO)
            .responsableDia(DEFAULT_RESPONSABLE_DIA)
            .origenVoluntario(DEFAULT_ORIGEN_VOLUNTARIO)
            .manipuladorAlimentos(DEFAULT_MANIPULADOR_ALIMENTOS)
            .codigoPostal(DEFAULT_CODIGO_POSTAL);
        return voluntario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voluntario createUpdatedEntity(EntityManager em) {
        Voluntario voluntario = new Voluntario()
            .nombreVoluntario(UPDATED_NOMBRE_VOLUNTARIO)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .emailVoluntario(UPDATED_EMAIL_VOLUNTARIO)
            .telefonoContactoVoluntario(UPDATED_TELEFONO_CONTACTO_VOLUNTARIO)
            .dniVoluntario(UPDATED_DNI_VOLUNTARIO)
            .fechaNacimientoVoluntario(UPDATED_FECHA_NACIMIENTO_VOLUNTARIO)
            .sexoVoluntario(UPDATED_SEXO_VOLUNTARIO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .tipoVoluntario(UPDATED_TIPO_VOLUNTARIO)
            .tipoTurno(UPDATED_TIPO_TURNO)
            .responsableDia(UPDATED_RESPONSABLE_DIA)
            .origenVoluntario(UPDATED_ORIGEN_VOLUNTARIO)
            .manipuladorAlimentos(UPDATED_MANIPULADOR_ALIMENTOS)
            .codigoPostal(UPDATED_CODIGO_POSTAL);
        return voluntario;
    }

    @BeforeEach
    public void initTest() {
        voluntario = createEntity(em);
    }

    @Test
    @Transactional
    void createVoluntario() throws Exception {
        int databaseSizeBeforeCreate = voluntarioRepository.findAll().size();
        // Create the Voluntario
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);
        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeCreate + 1);
        Voluntario testVoluntario = voluntarioList.get(voluntarioList.size() - 1);
        assertThat(testVoluntario.getNombreVoluntario()).isEqualTo(DEFAULT_NOMBRE_VOLUNTARIO);
        assertThat(testVoluntario.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testVoluntario.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testVoluntario.getEmailVoluntario()).isEqualTo(DEFAULT_EMAIL_VOLUNTARIO);
        assertThat(testVoluntario.getTelefonoContactoVoluntario()).isEqualTo(DEFAULT_TELEFONO_CONTACTO_VOLUNTARIO);
        assertThat(testVoluntario.getDniVoluntario()).isEqualTo(DEFAULT_DNI_VOLUNTARIO);
        assertThat(testVoluntario.getFechaNacimientoVoluntario()).isEqualTo(DEFAULT_FECHA_NACIMIENTO_VOLUNTARIO);
        assertThat(testVoluntario.getSexoVoluntario()).isEqualTo(DEFAULT_SEXO_VOLUNTARIO);
        assertThat(testVoluntario.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testVoluntario.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testVoluntario.getTipoVoluntario()).isEqualTo(DEFAULT_TIPO_VOLUNTARIO);
        assertThat(testVoluntario.getTipoTurno()).isEqualTo(DEFAULT_TIPO_TURNO);
        assertThat(testVoluntario.getResponsableDia()).isEqualTo(DEFAULT_RESPONSABLE_DIA);
        assertThat(testVoluntario.getOrigenVoluntario()).isEqualTo(DEFAULT_ORIGEN_VOLUNTARIO);
        assertThat(testVoluntario.getManipuladorAlimentos()).isEqualTo(DEFAULT_MANIPULADOR_ALIMENTOS);
        assertThat(testVoluntario.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void createVoluntarioWithExistingId() throws Exception {
        // Create the Voluntario with an existing ID
        voluntario.setId(1L);
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        int databaseSizeBeforeCreate = voluntarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreVoluntarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setNombreVoluntario(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimerApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setPrimerApellido(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailVoluntarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setEmailVoluntario(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoContactoVoluntarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setTelefonoContactoVoluntario(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaNacimientoVoluntarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setFechaNacimientoVoluntario(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoVoluntarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setSexoVoluntario(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setFechaAlta(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoVoluntarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setTipoVoluntario(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkManipuladorAlimentosIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setManipuladorAlimentos(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoPostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setCodigoPostal(null);

        // Create the Voluntario, which fails.
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        restVoluntarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isBadRequest());

        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVoluntarios() throws Exception {
        // Initialize the database
        voluntarioRepository.saveAndFlush(voluntario);

        // Get all the voluntarioList
        restVoluntarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voluntario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreVoluntario").value(hasItem(DEFAULT_NOMBRE_VOLUNTARIO)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].emailVoluntario").value(hasItem(DEFAULT_EMAIL_VOLUNTARIO)))
            .andExpect(jsonPath("$.[*].telefonoContactoVoluntario").value(hasItem(DEFAULT_TELEFONO_CONTACTO_VOLUNTARIO)))
            .andExpect(jsonPath("$.[*].dniVoluntario").value(hasItem(DEFAULT_DNI_VOLUNTARIO)))
            .andExpect(jsonPath("$.[*].fechaNacimientoVoluntario").value(hasItem(DEFAULT_FECHA_NACIMIENTO_VOLUNTARIO.toString())))
            .andExpect(jsonPath("$.[*].sexoVoluntario").value(hasItem(DEFAULT_SEXO_VOLUNTARIO)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())))
            .andExpect(jsonPath("$.[*].tipoVoluntario").value(hasItem(DEFAULT_TIPO_VOLUNTARIO)))
            .andExpect(jsonPath("$.[*].tipoTurno").value(hasItem(DEFAULT_TIPO_TURNO)))
            .andExpect(jsonPath("$.[*].responsableDia").value(hasItem(DEFAULT_RESPONSABLE_DIA.booleanValue())))
            .andExpect(jsonPath("$.[*].origenVoluntario").value(hasItem(DEFAULT_ORIGEN_VOLUNTARIO)))
            .andExpect(jsonPath("$.[*].manipuladorAlimentos").value(hasItem(DEFAULT_MANIPULADOR_ALIMENTOS.booleanValue())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)));
    }

    @Test
    @Transactional
    void getVoluntario() throws Exception {
        // Initialize the database
        voluntarioRepository.saveAndFlush(voluntario);

        // Get the voluntario
        restVoluntarioMockMvc
            .perform(get(ENTITY_API_URL_ID, voluntario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voluntario.getId().intValue()))
            .andExpect(jsonPath("$.nombreVoluntario").value(DEFAULT_NOMBRE_VOLUNTARIO))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.emailVoluntario").value(DEFAULT_EMAIL_VOLUNTARIO))
            .andExpect(jsonPath("$.telefonoContactoVoluntario").value(DEFAULT_TELEFONO_CONTACTO_VOLUNTARIO))
            .andExpect(jsonPath("$.dniVoluntario").value(DEFAULT_DNI_VOLUNTARIO))
            .andExpect(jsonPath("$.fechaNacimientoVoluntario").value(DEFAULT_FECHA_NACIMIENTO_VOLUNTARIO.toString()))
            .andExpect(jsonPath("$.sexoVoluntario").value(DEFAULT_SEXO_VOLUNTARIO))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()))
            .andExpect(jsonPath("$.tipoVoluntario").value(DEFAULT_TIPO_VOLUNTARIO))
            .andExpect(jsonPath("$.tipoTurno").value(DEFAULT_TIPO_TURNO))
            .andExpect(jsonPath("$.responsableDia").value(DEFAULT_RESPONSABLE_DIA.booleanValue()))
            .andExpect(jsonPath("$.origenVoluntario").value(DEFAULT_ORIGEN_VOLUNTARIO))
            .andExpect(jsonPath("$.manipuladorAlimentos").value(DEFAULT_MANIPULADOR_ALIMENTOS.booleanValue()))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL));
    }

    @Test
    @Transactional
    void getNonExistingVoluntario() throws Exception {
        // Get the voluntario
        restVoluntarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoluntario() throws Exception {
        // Initialize the database
        voluntarioRepository.saveAndFlush(voluntario);

        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();

        // Update the voluntario
        Voluntario updatedVoluntario = voluntarioRepository.findById(voluntario.getId()).get();
        // Disconnect from session so that the updates on updatedVoluntario are not directly saved in db
        em.detach(updatedVoluntario);
        updatedVoluntario
            .nombreVoluntario(UPDATED_NOMBRE_VOLUNTARIO)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .emailVoluntario(UPDATED_EMAIL_VOLUNTARIO)
            .telefonoContactoVoluntario(UPDATED_TELEFONO_CONTACTO_VOLUNTARIO)
            .dniVoluntario(UPDATED_DNI_VOLUNTARIO)
            .fechaNacimientoVoluntario(UPDATED_FECHA_NACIMIENTO_VOLUNTARIO)
            .sexoVoluntario(UPDATED_SEXO_VOLUNTARIO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .tipoVoluntario(UPDATED_TIPO_VOLUNTARIO)
            .tipoTurno(UPDATED_TIPO_TURNO)
            .responsableDia(UPDATED_RESPONSABLE_DIA)
            .origenVoluntario(UPDATED_ORIGEN_VOLUNTARIO)
            .manipuladorAlimentos(UPDATED_MANIPULADOR_ALIMENTOS)
            .codigoPostal(UPDATED_CODIGO_POSTAL);
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(updatedVoluntario);

        restVoluntarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voluntarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voluntarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
        Voluntario testVoluntario = voluntarioList.get(voluntarioList.size() - 1);
        assertThat(testVoluntario.getNombreVoluntario()).isEqualTo(UPDATED_NOMBRE_VOLUNTARIO);
        assertThat(testVoluntario.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testVoluntario.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testVoluntario.getEmailVoluntario()).isEqualTo(UPDATED_EMAIL_VOLUNTARIO);
        assertThat(testVoluntario.getTelefonoContactoVoluntario()).isEqualTo(UPDATED_TELEFONO_CONTACTO_VOLUNTARIO);
        assertThat(testVoluntario.getDniVoluntario()).isEqualTo(UPDATED_DNI_VOLUNTARIO);
        assertThat(testVoluntario.getFechaNacimientoVoluntario()).isEqualTo(UPDATED_FECHA_NACIMIENTO_VOLUNTARIO);
        assertThat(testVoluntario.getSexoVoluntario()).isEqualTo(UPDATED_SEXO_VOLUNTARIO);
        assertThat(testVoluntario.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testVoluntario.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testVoluntario.getTipoVoluntario()).isEqualTo(UPDATED_TIPO_VOLUNTARIO);
        assertThat(testVoluntario.getTipoTurno()).isEqualTo(UPDATED_TIPO_TURNO);
        assertThat(testVoluntario.getResponsableDia()).isEqualTo(UPDATED_RESPONSABLE_DIA);
        assertThat(testVoluntario.getOrigenVoluntario()).isEqualTo(UPDATED_ORIGEN_VOLUNTARIO);
        assertThat(testVoluntario.getManipuladorAlimentos()).isEqualTo(UPDATED_MANIPULADOR_ALIMENTOS);
        assertThat(testVoluntario.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void putNonExistingVoluntario() throws Exception {
        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();
        voluntario.setId(count.incrementAndGet());

        // Create the Voluntario
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoluntarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voluntarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voluntarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoluntario() throws Exception {
        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();
        voluntario.setId(count.incrementAndGet());

        // Create the Voluntario
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoluntarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voluntarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoluntario() throws Exception {
        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();
        voluntario.setId(count.incrementAndGet());

        // Create the Voluntario
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoluntarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voluntarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoluntarioWithPatch() throws Exception {
        // Initialize the database
        voluntarioRepository.saveAndFlush(voluntario);

        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();

        // Update the voluntario using partial update
        Voluntario partialUpdatedVoluntario = new Voluntario();
        partialUpdatedVoluntario.setId(voluntario.getId());

        partialUpdatedVoluntario
            .nombreVoluntario(UPDATED_NOMBRE_VOLUNTARIO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .dniVoluntario(UPDATED_DNI_VOLUNTARIO)
            .fechaNacimientoVoluntario(UPDATED_FECHA_NACIMIENTO_VOLUNTARIO)
            .tipoTurno(UPDATED_TIPO_TURNO)
            .responsableDia(UPDATED_RESPONSABLE_DIA)
            .manipuladorAlimentos(UPDATED_MANIPULADOR_ALIMENTOS);

        restVoluntarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoluntario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoluntario))
            )
            .andExpect(status().isOk());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
        Voluntario testVoluntario = voluntarioList.get(voluntarioList.size() - 1);
        assertThat(testVoluntario.getNombreVoluntario()).isEqualTo(UPDATED_NOMBRE_VOLUNTARIO);
        assertThat(testVoluntario.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testVoluntario.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testVoluntario.getEmailVoluntario()).isEqualTo(DEFAULT_EMAIL_VOLUNTARIO);
        assertThat(testVoluntario.getTelefonoContactoVoluntario()).isEqualTo(DEFAULT_TELEFONO_CONTACTO_VOLUNTARIO);
        assertThat(testVoluntario.getDniVoluntario()).isEqualTo(UPDATED_DNI_VOLUNTARIO);
        assertThat(testVoluntario.getFechaNacimientoVoluntario()).isEqualTo(UPDATED_FECHA_NACIMIENTO_VOLUNTARIO);
        assertThat(testVoluntario.getSexoVoluntario()).isEqualTo(DEFAULT_SEXO_VOLUNTARIO);
        assertThat(testVoluntario.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testVoluntario.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testVoluntario.getTipoVoluntario()).isEqualTo(DEFAULT_TIPO_VOLUNTARIO);
        assertThat(testVoluntario.getTipoTurno()).isEqualTo(UPDATED_TIPO_TURNO);
        assertThat(testVoluntario.getResponsableDia()).isEqualTo(UPDATED_RESPONSABLE_DIA);
        assertThat(testVoluntario.getOrigenVoluntario()).isEqualTo(DEFAULT_ORIGEN_VOLUNTARIO);
        assertThat(testVoluntario.getManipuladorAlimentos()).isEqualTo(UPDATED_MANIPULADOR_ALIMENTOS);
        assertThat(testVoluntario.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void fullUpdateVoluntarioWithPatch() throws Exception {
        // Initialize the database
        voluntarioRepository.saveAndFlush(voluntario);

        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();

        // Update the voluntario using partial update
        Voluntario partialUpdatedVoluntario = new Voluntario();
        partialUpdatedVoluntario.setId(voluntario.getId());

        partialUpdatedVoluntario
            .nombreVoluntario(UPDATED_NOMBRE_VOLUNTARIO)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .emailVoluntario(UPDATED_EMAIL_VOLUNTARIO)
            .telefonoContactoVoluntario(UPDATED_TELEFONO_CONTACTO_VOLUNTARIO)
            .dniVoluntario(UPDATED_DNI_VOLUNTARIO)
            .fechaNacimientoVoluntario(UPDATED_FECHA_NACIMIENTO_VOLUNTARIO)
            .sexoVoluntario(UPDATED_SEXO_VOLUNTARIO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .tipoVoluntario(UPDATED_TIPO_VOLUNTARIO)
            .tipoTurno(UPDATED_TIPO_TURNO)
            .responsableDia(UPDATED_RESPONSABLE_DIA)
            .origenVoluntario(UPDATED_ORIGEN_VOLUNTARIO)
            .manipuladorAlimentos(UPDATED_MANIPULADOR_ALIMENTOS)
            .codigoPostal(UPDATED_CODIGO_POSTAL);

        restVoluntarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoluntario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoluntario))
            )
            .andExpect(status().isOk());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
        Voluntario testVoluntario = voluntarioList.get(voluntarioList.size() - 1);
        assertThat(testVoluntario.getNombreVoluntario()).isEqualTo(UPDATED_NOMBRE_VOLUNTARIO);
        assertThat(testVoluntario.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testVoluntario.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testVoluntario.getEmailVoluntario()).isEqualTo(UPDATED_EMAIL_VOLUNTARIO);
        assertThat(testVoluntario.getTelefonoContactoVoluntario()).isEqualTo(UPDATED_TELEFONO_CONTACTO_VOLUNTARIO);
        assertThat(testVoluntario.getDniVoluntario()).isEqualTo(UPDATED_DNI_VOLUNTARIO);
        assertThat(testVoluntario.getFechaNacimientoVoluntario()).isEqualTo(UPDATED_FECHA_NACIMIENTO_VOLUNTARIO);
        assertThat(testVoluntario.getSexoVoluntario()).isEqualTo(UPDATED_SEXO_VOLUNTARIO);
        assertThat(testVoluntario.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testVoluntario.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testVoluntario.getTipoVoluntario()).isEqualTo(UPDATED_TIPO_VOLUNTARIO);
        assertThat(testVoluntario.getTipoTurno()).isEqualTo(UPDATED_TIPO_TURNO);
        assertThat(testVoluntario.getResponsableDia()).isEqualTo(UPDATED_RESPONSABLE_DIA);
        assertThat(testVoluntario.getOrigenVoluntario()).isEqualTo(UPDATED_ORIGEN_VOLUNTARIO);
        assertThat(testVoluntario.getManipuladorAlimentos()).isEqualTo(UPDATED_MANIPULADOR_ALIMENTOS);
        assertThat(testVoluntario.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    void patchNonExistingVoluntario() throws Exception {
        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();
        voluntario.setId(count.incrementAndGet());

        // Create the Voluntario
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoluntarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voluntarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voluntarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoluntario() throws Exception {
        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();
        voluntario.setId(count.incrementAndGet());

        // Create the Voluntario
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoluntarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voluntarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoluntario() throws Exception {
        int databaseSizeBeforeUpdate = voluntarioRepository.findAll().size();
        voluntario.setId(count.incrementAndGet());

        // Create the Voluntario
        VoluntarioDTO voluntarioDTO = voluntarioMapper.toDto(voluntario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoluntarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voluntarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voluntario in the database
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoluntario() throws Exception {
        // Initialize the database
        voluntarioRepository.saveAndFlush(voluntario);

        int databaseSizeBeforeDelete = voluntarioRepository.findAll().size();

        // Delete the voluntario
        restVoluntarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, voluntario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voluntario> voluntarioList = voluntarioRepository.findAll();
        assertThat(voluntarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
