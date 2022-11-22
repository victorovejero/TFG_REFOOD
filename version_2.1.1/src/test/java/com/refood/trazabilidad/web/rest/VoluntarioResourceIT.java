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

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_BAJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_BAJA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_REFOOD = "AAAAAAAAAA";
    private static final String UPDATED_DIA_REFOOD = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANIPULADOR_ALIMENTOS = false;
    private static final Boolean UPDATED_MANIPULADOR_ALIMENTOS = true;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

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
            .nombre(DEFAULT_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .email(DEFAULT_EMAIL)
            .telefonoContacto(DEFAULT_TELEFONO_CONTACTO)
            .dni(DEFAULT_DNI)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .sexo(DEFAULT_SEXO)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaBaja(DEFAULT_FECHA_BAJA)
            .perfil(DEFAULT_PERFIL)
            .diaRefood(DEFAULT_DIA_REFOOD)
            .origen(DEFAULT_ORIGEN)
            .manipuladorAlimentos(DEFAULT_MANIPULADOR_ALIMENTOS)
            .direccion(DEFAULT_DIRECCION)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .activo(DEFAULT_ACTIVO);
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
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .perfil(UPDATED_PERFIL)
            .diaRefood(UPDATED_DIA_REFOOD)
            .origen(UPDATED_ORIGEN)
            .manipuladorAlimentos(UPDATED_MANIPULADOR_ALIMENTOS)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .activo(UPDATED_ACTIVO);
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
        assertThat(testVoluntario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testVoluntario.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testVoluntario.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testVoluntario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVoluntario.getTelefonoContacto()).isEqualTo(DEFAULT_TELEFONO_CONTACTO);
        assertThat(testVoluntario.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testVoluntario.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testVoluntario.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testVoluntario.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testVoluntario.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testVoluntario.getPerfil()).isEqualTo(DEFAULT_PERFIL);
        assertThat(testVoluntario.getDiaRefood()).isEqualTo(DEFAULT_DIA_REFOOD);
        assertThat(testVoluntario.getOrigen()).isEqualTo(DEFAULT_ORIGEN);
        assertThat(testVoluntario.getManipuladorAlimentos()).isEqualTo(DEFAULT_MANIPULADOR_ALIMENTOS);
        assertThat(testVoluntario.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testVoluntario.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testVoluntario.getActivo()).isEqualTo(DEFAULT_ACTIVO);
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
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setNombre(null);

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
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setEmail(null);

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
    void checkTelefonoContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setTelefonoContacto(null);

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
    void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setFechaNacimiento(null);

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
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setSexo(null);

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
    void checkPerfilIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setPerfil(null);

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
    void checkDiaRefoodIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setDiaRefood(null);

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
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setDireccion(null);

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
    void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voluntarioRepository.findAll().size();
        // set the field null
        voluntario.setActivo(null);

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
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefonoContacto").value(hasItem(DEFAULT_TELEFONO_CONTACTO)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())))
            .andExpect(jsonPath("$.[*].perfil").value(hasItem(DEFAULT_PERFIL)))
            .andExpect(jsonPath("$.[*].diaRefood").value(hasItem(DEFAULT_DIA_REFOOD)))
            .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN)))
            .andExpect(jsonPath("$.[*].manipuladorAlimentos").value(hasItem(DEFAULT_MANIPULADOR_ALIMENTOS.booleanValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
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
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefonoContacto").value(DEFAULT_TELEFONO_CONTACTO))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()))
            .andExpect(jsonPath("$.perfil").value(DEFAULT_PERFIL))
            .andExpect(jsonPath("$.diaRefood").value(DEFAULT_DIA_REFOOD))
            .andExpect(jsonPath("$.origen").value(DEFAULT_ORIGEN))
            .andExpect(jsonPath("$.manipuladorAlimentos").value(DEFAULT_MANIPULADOR_ALIMENTOS.booleanValue()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
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
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .perfil(UPDATED_PERFIL)
            .diaRefood(UPDATED_DIA_REFOOD)
            .origen(UPDATED_ORIGEN)
            .manipuladorAlimentos(UPDATED_MANIPULADOR_ALIMENTOS)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .activo(UPDATED_ACTIVO);
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
        assertThat(testVoluntario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testVoluntario.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testVoluntario.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testVoluntario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVoluntario.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testVoluntario.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testVoluntario.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testVoluntario.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testVoluntario.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testVoluntario.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testVoluntario.getPerfil()).isEqualTo(UPDATED_PERFIL);
        assertThat(testVoluntario.getDiaRefood()).isEqualTo(UPDATED_DIA_REFOOD);
        assertThat(testVoluntario.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testVoluntario.getManipuladorAlimentos()).isEqualTo(UPDATED_MANIPULADOR_ALIMENTOS);
        assertThat(testVoluntario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testVoluntario.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testVoluntario.getActivo()).isEqualTo(UPDATED_ACTIVO);
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
            .nombre(UPDATED_NOMBRE)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .diaRefood(UPDATED_DIA_REFOOD)
            .origen(UPDATED_ORIGEN)
            .direccion(UPDATED_DIRECCION)
            .activo(UPDATED_ACTIVO);

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
        assertThat(testVoluntario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testVoluntario.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testVoluntario.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testVoluntario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVoluntario.getTelefonoContacto()).isEqualTo(DEFAULT_TELEFONO_CONTACTO);
        assertThat(testVoluntario.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testVoluntario.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testVoluntario.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testVoluntario.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testVoluntario.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testVoluntario.getPerfil()).isEqualTo(DEFAULT_PERFIL);
        assertThat(testVoluntario.getDiaRefood()).isEqualTo(UPDATED_DIA_REFOOD);
        assertThat(testVoluntario.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testVoluntario.getManipuladorAlimentos()).isEqualTo(DEFAULT_MANIPULADOR_ALIMENTOS);
        assertThat(testVoluntario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testVoluntario.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testVoluntario.getActivo()).isEqualTo(UPDATED_ACTIVO);
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
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .perfil(UPDATED_PERFIL)
            .diaRefood(UPDATED_DIA_REFOOD)
            .origen(UPDATED_ORIGEN)
            .manipuladorAlimentos(UPDATED_MANIPULADOR_ALIMENTOS)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .activo(UPDATED_ACTIVO);

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
        assertThat(testVoluntario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testVoluntario.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testVoluntario.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testVoluntario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVoluntario.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testVoluntario.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testVoluntario.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testVoluntario.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testVoluntario.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testVoluntario.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testVoluntario.getPerfil()).isEqualTo(UPDATED_PERFIL);
        assertThat(testVoluntario.getDiaRefood()).isEqualTo(UPDATED_DIA_REFOOD);
        assertThat(testVoluntario.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testVoluntario.getManipuladorAlimentos()).isEqualTo(UPDATED_MANIPULADOR_ALIMENTOS);
        assertThat(testVoluntario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testVoluntario.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testVoluntario.getActivo()).isEqualTo(UPDATED_ACTIVO);
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
