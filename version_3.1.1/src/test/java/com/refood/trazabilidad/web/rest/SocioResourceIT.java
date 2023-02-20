package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Socio;
import com.refood.trazabilidad.repository.SocioRepository;
import com.refood.trazabilidad.service.dto.SocioDTO;
import com.refood.trazabilidad.service.mapper.SocioMapper;
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
 * Integration tests for the {@link SocioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocioResourceIT {

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

    private static final String DEFAULT_I_BAN = "AAAAAAAAAA";
    private static final String UPDATED_I_BAN = "BBBBBBBBBB";

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

    private static final Double DEFAULT_CONTRIBUCION_MENSUAL = 1D;
    private static final Double UPDATED_CONTRIBUCION_MENSUAL = 2D;

    private static final String DEFAULT_PERIODO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO_PAGO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String DEFAULT_NUCLEO_ASOCIADO = "AAAAAAAAAA";
    private static final String UPDATED_NUCLEO_ASOCIADO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COMUNICACION = false;
    private static final Boolean UPDATED_COMUNICACION = true;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/socios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private SocioMapper socioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocioMockMvc;

    private Socio socio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Socio createEntity(EntityManager em) {
        Socio socio = new Socio()
            .nombre(DEFAULT_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .email(DEFAULT_EMAIL)
            .telefonoContacto(DEFAULT_TELEFONO_CONTACTO)
            .iBAN(DEFAULT_I_BAN)
            .dni(DEFAULT_DNI)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .sexo(DEFAULT_SEXO)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaBaja(DEFAULT_FECHA_BAJA)
            .contribucionMensual(DEFAULT_CONTRIBUCION_MENSUAL)
            .periodoPago(DEFAULT_PERIODO_PAGO)
            .activo(DEFAULT_ACTIVO)
            .nucleoAsociado(DEFAULT_NUCLEO_ASOCIADO)
            .comunicacion(DEFAULT_COMUNICACION)
            .direccion(DEFAULT_DIRECCION)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .provincia(DEFAULT_PROVINCIA)
            .pais(DEFAULT_PAIS);
        return socio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Socio createUpdatedEntity(EntityManager em) {
        Socio socio = new Socio()
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .iBAN(UPDATED_I_BAN)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .contribucionMensual(UPDATED_CONTRIBUCION_MENSUAL)
            .periodoPago(UPDATED_PERIODO_PAGO)
            .activo(UPDATED_ACTIVO)
            .nucleoAsociado(UPDATED_NUCLEO_ASOCIADO)
            .comunicacion(UPDATED_COMUNICACION)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .pais(UPDATED_PAIS);
        return socio;
    }

    @BeforeEach
    public void initTest() {
        socio = createEntity(em);
    }

    @Test
    @Transactional
    void createSocio() throws Exception {
        int databaseSizeBeforeCreate = socioRepository.findAll().size();
        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);
        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isCreated());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeCreate + 1);
        Socio testSocio = socioList.get(socioList.size() - 1);
        assertThat(testSocio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSocio.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testSocio.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testSocio.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSocio.getTelefonoContacto()).isEqualTo(DEFAULT_TELEFONO_CONTACTO);
        assertThat(testSocio.getiBAN()).isEqualTo(DEFAULT_I_BAN);
        assertThat(testSocio.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testSocio.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testSocio.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testSocio.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testSocio.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testSocio.getContribucionMensual()).isEqualTo(DEFAULT_CONTRIBUCION_MENSUAL);
        assertThat(testSocio.getPeriodoPago()).isEqualTo(DEFAULT_PERIODO_PAGO);
        assertThat(testSocio.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testSocio.getNucleoAsociado()).isEqualTo(DEFAULT_NUCLEO_ASOCIADO);
        assertThat(testSocio.getComunicacion()).isEqualTo(DEFAULT_COMUNICACION);
        assertThat(testSocio.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testSocio.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testSocio.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testSocio.getPais()).isEqualTo(DEFAULT_PAIS);
    }

    @Test
    @Transactional
    void createSocioWithExistingId() throws Exception {
        // Create the Socio with an existing ID
        socio.setId(1L);
        SocioDTO socioDTO = socioMapper.toDto(socio);

        int databaseSizeBeforeCreate = socioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setNombre(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimerApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setPrimerApellido(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setEmail(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setTelefonoContacto(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkiBANIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setiBAN(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setDni(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setFechaNacimiento(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setSexo(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setFechaAlta(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContribucionMensualIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setContribucionMensual(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPeriodoPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setPeriodoPago(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setActivo(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkComunicacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setComunicacion(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setDireccion(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoPostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setCodigoPostal(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocios() throws Exception {
        // Initialize the database
        socioRepository.saveAndFlush(socio);

        // Get all the socioList
        restSocioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefonoContacto").value(hasItem(DEFAULT_TELEFONO_CONTACTO)))
            .andExpect(jsonPath("$.[*].iBAN").value(hasItem(DEFAULT_I_BAN)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())))
            .andExpect(jsonPath("$.[*].contribucionMensual").value(hasItem(DEFAULT_CONTRIBUCION_MENSUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].periodoPago").value(hasItem(DEFAULT_PERIODO_PAGO)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].nucleoAsociado").value(hasItem(DEFAULT_NUCLEO_ASOCIADO)))
            .andExpect(jsonPath("$.[*].comunicacion").value(hasItem(DEFAULT_COMUNICACION.booleanValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)));
    }

    @Test
    @Transactional
    void getSocio() throws Exception {
        // Initialize the database
        socioRepository.saveAndFlush(socio);

        // Get the socio
        restSocioMockMvc
            .perform(get(ENTITY_API_URL_ID, socio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefonoContacto").value(DEFAULT_TELEFONO_CONTACTO))
            .andExpect(jsonPath("$.iBAN").value(DEFAULT_I_BAN))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()))
            .andExpect(jsonPath("$.contribucionMensual").value(DEFAULT_CONTRIBUCION_MENSUAL.doubleValue()))
            .andExpect(jsonPath("$.periodoPago").value(DEFAULT_PERIODO_PAGO))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.nucleoAsociado").value(DEFAULT_NUCLEO_ASOCIADO))
            .andExpect(jsonPath("$.comunicacion").value(DEFAULT_COMUNICACION.booleanValue()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS));
    }

    @Test
    @Transactional
    void getNonExistingSocio() throws Exception {
        // Get the socio
        restSocioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSocio() throws Exception {
        // Initialize the database
        socioRepository.saveAndFlush(socio);

        int databaseSizeBeforeUpdate = socioRepository.findAll().size();

        // Update the socio
        Socio updatedSocio = socioRepository.findById(socio.getId()).get();
        // Disconnect from session so that the updates on updatedSocio are not directly saved in db
        em.detach(updatedSocio);
        updatedSocio
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .iBAN(UPDATED_I_BAN)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .contribucionMensual(UPDATED_CONTRIBUCION_MENSUAL)
            .periodoPago(UPDATED_PERIODO_PAGO)
            .activo(UPDATED_ACTIVO)
            .nucleoAsociado(UPDATED_NUCLEO_ASOCIADO)
            .comunicacion(UPDATED_COMUNICACION)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .pais(UPDATED_PAIS);
        SocioDTO socioDTO = socioMapper.toDto(updatedSocio);

        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
        Socio testSocio = socioList.get(socioList.size() - 1);
        assertThat(testSocio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSocio.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testSocio.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testSocio.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSocio.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testSocio.getiBAN()).isEqualTo(UPDATED_I_BAN);
        assertThat(testSocio.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testSocio.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testSocio.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testSocio.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testSocio.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testSocio.getContribucionMensual()).isEqualTo(UPDATED_CONTRIBUCION_MENSUAL);
        assertThat(testSocio.getPeriodoPago()).isEqualTo(UPDATED_PERIODO_PAGO);
        assertThat(testSocio.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testSocio.getNucleoAsociado()).isEqualTo(UPDATED_NUCLEO_ASOCIADO);
        assertThat(testSocio.getComunicacion()).isEqualTo(UPDATED_COMUNICACION);
        assertThat(testSocio.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testSocio.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testSocio.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testSocio.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    void putNonExistingSocio() throws Exception {
        int databaseSizeBeforeUpdate = socioRepository.findAll().size();
        socio.setId(count.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocio() throws Exception {
        int databaseSizeBeforeUpdate = socioRepository.findAll().size();
        socio.setId(count.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocio() throws Exception {
        int databaseSizeBeforeUpdate = socioRepository.findAll().size();
        socio.setId(count.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocioWithPatch() throws Exception {
        // Initialize the database
        socioRepository.saveAndFlush(socio);

        int databaseSizeBeforeUpdate = socioRepository.findAll().size();

        // Update the socio using partial update
        Socio partialUpdatedSocio = new Socio();
        partialUpdatedSocio.setId(socio.getId());

        partialUpdatedSocio
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .dni(UPDATED_DNI)
            .sexo(UPDATED_SEXO)
            .contribucionMensual(UPDATED_CONTRIBUCION_MENSUAL)
            .comunicacion(UPDATED_COMUNICACION)
            .direccion(UPDATED_DIRECCION)
            .pais(UPDATED_PAIS);

        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocio))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
        Socio testSocio = socioList.get(socioList.size() - 1);
        assertThat(testSocio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSocio.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testSocio.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testSocio.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSocio.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testSocio.getiBAN()).isEqualTo(DEFAULT_I_BAN);
        assertThat(testSocio.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testSocio.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testSocio.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testSocio.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testSocio.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testSocio.getContribucionMensual()).isEqualTo(UPDATED_CONTRIBUCION_MENSUAL);
        assertThat(testSocio.getPeriodoPago()).isEqualTo(DEFAULT_PERIODO_PAGO);
        assertThat(testSocio.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testSocio.getNucleoAsociado()).isEqualTo(DEFAULT_NUCLEO_ASOCIADO);
        assertThat(testSocio.getComunicacion()).isEqualTo(UPDATED_COMUNICACION);
        assertThat(testSocio.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testSocio.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testSocio.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testSocio.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    void fullUpdateSocioWithPatch() throws Exception {
        // Initialize the database
        socioRepository.saveAndFlush(socio);

        int databaseSizeBeforeUpdate = socioRepository.findAll().size();

        // Update the socio using partial update
        Socio partialUpdatedSocio = new Socio();
        partialUpdatedSocio.setId(socio.getId());

        partialUpdatedSocio
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .iBAN(UPDATED_I_BAN)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .contribucionMensual(UPDATED_CONTRIBUCION_MENSUAL)
            .periodoPago(UPDATED_PERIODO_PAGO)
            .activo(UPDATED_ACTIVO)
            .nucleoAsociado(UPDATED_NUCLEO_ASOCIADO)
            .comunicacion(UPDATED_COMUNICACION)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .pais(UPDATED_PAIS);

        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocio))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
        Socio testSocio = socioList.get(socioList.size() - 1);
        assertThat(testSocio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSocio.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testSocio.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testSocio.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSocio.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testSocio.getiBAN()).isEqualTo(UPDATED_I_BAN);
        assertThat(testSocio.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testSocio.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testSocio.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testSocio.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testSocio.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testSocio.getContribucionMensual()).isEqualTo(UPDATED_CONTRIBUCION_MENSUAL);
        assertThat(testSocio.getPeriodoPago()).isEqualTo(UPDATED_PERIODO_PAGO);
        assertThat(testSocio.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testSocio.getNucleoAsociado()).isEqualTo(UPDATED_NUCLEO_ASOCIADO);
        assertThat(testSocio.getComunicacion()).isEqualTo(UPDATED_COMUNICACION);
        assertThat(testSocio.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testSocio.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testSocio.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testSocio.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    void patchNonExistingSocio() throws Exception {
        int databaseSizeBeforeUpdate = socioRepository.findAll().size();
        socio.setId(count.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocio() throws Exception {
        int databaseSizeBeforeUpdate = socioRepository.findAll().size();
        socio.setId(count.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocio() throws Exception {
        int databaseSizeBeforeUpdate = socioRepository.findAll().size();
        socio.setId(count.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(socioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Socio in the database
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocio() throws Exception {
        // Initialize the database
        socioRepository.saveAndFlush(socio);

        int databaseSizeBeforeDelete = socioRepository.findAll().size();

        // Delete the socio
        restSocioMockMvc
            .perform(delete(ENTITY_API_URL_ID, socio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Socio> socioList = socioRepository.findAll();
        assertThat(socioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
