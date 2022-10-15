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

    private static final String DEFAULT_NOMBRE_SOCIO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_SOCIO = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO_SOCIO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO_SOCIO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO_SOCIO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO_SOCIO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SOCIO = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SOCIO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO_SOCIO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO_SOCIO = "BBBBBBBBBB";

    private static final String DEFAULT_DNI_SOCIO = "AAAAAAAAAA";
    private static final String UPDATED_DNI_SOCIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO_SOCIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO_SOCIO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEXO_SOCIO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO_SOCIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA_SOCIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA_SOCIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_BAJA_SOCIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_BAJA_SOCIO = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CONTRIBUCION_MENSUAL = 1D;
    private static final Double UPDATED_CONTRIBUCION_MENSUAL = 2D;

    private static final String DEFAULT_PERIODO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO_PAGO = "BBBBBBBBBB";

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
            .nombreSocio(DEFAULT_NOMBRE_SOCIO)
            .primerApellidoSocio(DEFAULT_PRIMER_APELLIDO_SOCIO)
            .segundoApellidoSocio(DEFAULT_SEGUNDO_APELLIDO_SOCIO)
            .emailSocio(DEFAULT_EMAIL_SOCIO)
            .telefonoContactoSocio(DEFAULT_TELEFONO_CONTACTO_SOCIO)
            .dniSocio(DEFAULT_DNI_SOCIO)
            .fechaNacimientoSocio(DEFAULT_FECHA_NACIMIENTO_SOCIO)
            .sexoSocio(DEFAULT_SEXO_SOCIO)
            .fechaAltaSocio(DEFAULT_FECHA_ALTA_SOCIO)
            .fechaBajaSocio(DEFAULT_FECHA_BAJA_SOCIO)
            .contribucionMensual(DEFAULT_CONTRIBUCION_MENSUAL)
            .periodoPago(DEFAULT_PERIODO_PAGO);
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
            .nombreSocio(UPDATED_NOMBRE_SOCIO)
            .primerApellidoSocio(UPDATED_PRIMER_APELLIDO_SOCIO)
            .segundoApellidoSocio(UPDATED_SEGUNDO_APELLIDO_SOCIO)
            .emailSocio(UPDATED_EMAIL_SOCIO)
            .telefonoContactoSocio(UPDATED_TELEFONO_CONTACTO_SOCIO)
            .dniSocio(UPDATED_DNI_SOCIO)
            .fechaNacimientoSocio(UPDATED_FECHA_NACIMIENTO_SOCIO)
            .sexoSocio(UPDATED_SEXO_SOCIO)
            .fechaAltaSocio(UPDATED_FECHA_ALTA_SOCIO)
            .fechaBajaSocio(UPDATED_FECHA_BAJA_SOCIO)
            .contribucionMensual(UPDATED_CONTRIBUCION_MENSUAL)
            .periodoPago(UPDATED_PERIODO_PAGO);
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
        assertThat(testSocio.getNombreSocio()).isEqualTo(DEFAULT_NOMBRE_SOCIO);
        assertThat(testSocio.getPrimerApellidoSocio()).isEqualTo(DEFAULT_PRIMER_APELLIDO_SOCIO);
        assertThat(testSocio.getSegundoApellidoSocio()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO_SOCIO);
        assertThat(testSocio.getEmailSocio()).isEqualTo(DEFAULT_EMAIL_SOCIO);
        assertThat(testSocio.getTelefonoContactoSocio()).isEqualTo(DEFAULT_TELEFONO_CONTACTO_SOCIO);
        assertThat(testSocio.getDniSocio()).isEqualTo(DEFAULT_DNI_SOCIO);
        assertThat(testSocio.getFechaNacimientoSocio()).isEqualTo(DEFAULT_FECHA_NACIMIENTO_SOCIO);
        assertThat(testSocio.getSexoSocio()).isEqualTo(DEFAULT_SEXO_SOCIO);
        assertThat(testSocio.getFechaAltaSocio()).isEqualTo(DEFAULT_FECHA_ALTA_SOCIO);
        assertThat(testSocio.getFechaBajaSocio()).isEqualTo(DEFAULT_FECHA_BAJA_SOCIO);
        assertThat(testSocio.getContribucionMensual()).isEqualTo(DEFAULT_CONTRIBUCION_MENSUAL);
        assertThat(testSocio.getPeriodoPago()).isEqualTo(DEFAULT_PERIODO_PAGO);
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
    void checkNombreSocioIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setNombreSocio(null);

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
    void checkPrimerApellidoSocioIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setPrimerApellidoSocio(null);

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
    void checkEmailSocioIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setEmailSocio(null);

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
    void checkTelefonoContactoSocioIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setTelefonoContactoSocio(null);

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
    void checkDniSocioIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setDniSocio(null);

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
    void checkFechaNacimientoSocioIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setFechaNacimientoSocio(null);

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
    void checkSexoSocioIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setSexoSocio(null);

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
    void checkFechaAltaSocioIsRequired() throws Exception {
        int databaseSizeBeforeTest = socioRepository.findAll().size();
        // set the field null
        socio.setFechaAltaSocio(null);

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
    void getAllSocios() throws Exception {
        // Initialize the database
        socioRepository.saveAndFlush(socio);

        // Get all the socioList
        restSocioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreSocio").value(hasItem(DEFAULT_NOMBRE_SOCIO)))
            .andExpect(jsonPath("$.[*].primerApellidoSocio").value(hasItem(DEFAULT_PRIMER_APELLIDO_SOCIO)))
            .andExpect(jsonPath("$.[*].segundoApellidoSocio").value(hasItem(DEFAULT_SEGUNDO_APELLIDO_SOCIO)))
            .andExpect(jsonPath("$.[*].emailSocio").value(hasItem(DEFAULT_EMAIL_SOCIO)))
            .andExpect(jsonPath("$.[*].telefonoContactoSocio").value(hasItem(DEFAULT_TELEFONO_CONTACTO_SOCIO)))
            .andExpect(jsonPath("$.[*].dniSocio").value(hasItem(DEFAULT_DNI_SOCIO)))
            .andExpect(jsonPath("$.[*].fechaNacimientoSocio").value(hasItem(DEFAULT_FECHA_NACIMIENTO_SOCIO.toString())))
            .andExpect(jsonPath("$.[*].sexoSocio").value(hasItem(DEFAULT_SEXO_SOCIO)))
            .andExpect(jsonPath("$.[*].fechaAltaSocio").value(hasItem(DEFAULT_FECHA_ALTA_SOCIO.toString())))
            .andExpect(jsonPath("$.[*].fechaBajaSocio").value(hasItem(DEFAULT_FECHA_BAJA_SOCIO.toString())))
            .andExpect(jsonPath("$.[*].contribucionMensual").value(hasItem(DEFAULT_CONTRIBUCION_MENSUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].periodoPago").value(hasItem(DEFAULT_PERIODO_PAGO)));
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
            .andExpect(jsonPath("$.nombreSocio").value(DEFAULT_NOMBRE_SOCIO))
            .andExpect(jsonPath("$.primerApellidoSocio").value(DEFAULT_PRIMER_APELLIDO_SOCIO))
            .andExpect(jsonPath("$.segundoApellidoSocio").value(DEFAULT_SEGUNDO_APELLIDO_SOCIO))
            .andExpect(jsonPath("$.emailSocio").value(DEFAULT_EMAIL_SOCIO))
            .andExpect(jsonPath("$.telefonoContactoSocio").value(DEFAULT_TELEFONO_CONTACTO_SOCIO))
            .andExpect(jsonPath("$.dniSocio").value(DEFAULT_DNI_SOCIO))
            .andExpect(jsonPath("$.fechaNacimientoSocio").value(DEFAULT_FECHA_NACIMIENTO_SOCIO.toString()))
            .andExpect(jsonPath("$.sexoSocio").value(DEFAULT_SEXO_SOCIO))
            .andExpect(jsonPath("$.fechaAltaSocio").value(DEFAULT_FECHA_ALTA_SOCIO.toString()))
            .andExpect(jsonPath("$.fechaBajaSocio").value(DEFAULT_FECHA_BAJA_SOCIO.toString()))
            .andExpect(jsonPath("$.contribucionMensual").value(DEFAULT_CONTRIBUCION_MENSUAL.doubleValue()))
            .andExpect(jsonPath("$.periodoPago").value(DEFAULT_PERIODO_PAGO));
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
            .nombreSocio(UPDATED_NOMBRE_SOCIO)
            .primerApellidoSocio(UPDATED_PRIMER_APELLIDO_SOCIO)
            .segundoApellidoSocio(UPDATED_SEGUNDO_APELLIDO_SOCIO)
            .emailSocio(UPDATED_EMAIL_SOCIO)
            .telefonoContactoSocio(UPDATED_TELEFONO_CONTACTO_SOCIO)
            .dniSocio(UPDATED_DNI_SOCIO)
            .fechaNacimientoSocio(UPDATED_FECHA_NACIMIENTO_SOCIO)
            .sexoSocio(UPDATED_SEXO_SOCIO)
            .fechaAltaSocio(UPDATED_FECHA_ALTA_SOCIO)
            .fechaBajaSocio(UPDATED_FECHA_BAJA_SOCIO)
            .contribucionMensual(UPDATED_CONTRIBUCION_MENSUAL)
            .periodoPago(UPDATED_PERIODO_PAGO);
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
        assertThat(testSocio.getNombreSocio()).isEqualTo(UPDATED_NOMBRE_SOCIO);
        assertThat(testSocio.getPrimerApellidoSocio()).isEqualTo(UPDATED_PRIMER_APELLIDO_SOCIO);
        assertThat(testSocio.getSegundoApellidoSocio()).isEqualTo(UPDATED_SEGUNDO_APELLIDO_SOCIO);
        assertThat(testSocio.getEmailSocio()).isEqualTo(UPDATED_EMAIL_SOCIO);
        assertThat(testSocio.getTelefonoContactoSocio()).isEqualTo(UPDATED_TELEFONO_CONTACTO_SOCIO);
        assertThat(testSocio.getDniSocio()).isEqualTo(UPDATED_DNI_SOCIO);
        assertThat(testSocio.getFechaNacimientoSocio()).isEqualTo(UPDATED_FECHA_NACIMIENTO_SOCIO);
        assertThat(testSocio.getSexoSocio()).isEqualTo(UPDATED_SEXO_SOCIO);
        assertThat(testSocio.getFechaAltaSocio()).isEqualTo(UPDATED_FECHA_ALTA_SOCIO);
        assertThat(testSocio.getFechaBajaSocio()).isEqualTo(UPDATED_FECHA_BAJA_SOCIO);
        assertThat(testSocio.getContribucionMensual()).isEqualTo(UPDATED_CONTRIBUCION_MENSUAL);
        assertThat(testSocio.getPeriodoPago()).isEqualTo(UPDATED_PERIODO_PAGO);
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
            .primerApellidoSocio(UPDATED_PRIMER_APELLIDO_SOCIO)
            .segundoApellidoSocio(UPDATED_SEGUNDO_APELLIDO_SOCIO)
            .emailSocio(UPDATED_EMAIL_SOCIO)
            .telefonoContactoSocio(UPDATED_TELEFONO_CONTACTO_SOCIO)
            .fechaNacimientoSocio(UPDATED_FECHA_NACIMIENTO_SOCIO)
            .fechaAltaSocio(UPDATED_FECHA_ALTA_SOCIO)
            .periodoPago(UPDATED_PERIODO_PAGO);

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
        assertThat(testSocio.getNombreSocio()).isEqualTo(DEFAULT_NOMBRE_SOCIO);
        assertThat(testSocio.getPrimerApellidoSocio()).isEqualTo(UPDATED_PRIMER_APELLIDO_SOCIO);
        assertThat(testSocio.getSegundoApellidoSocio()).isEqualTo(UPDATED_SEGUNDO_APELLIDO_SOCIO);
        assertThat(testSocio.getEmailSocio()).isEqualTo(UPDATED_EMAIL_SOCIO);
        assertThat(testSocio.getTelefonoContactoSocio()).isEqualTo(UPDATED_TELEFONO_CONTACTO_SOCIO);
        assertThat(testSocio.getDniSocio()).isEqualTo(DEFAULT_DNI_SOCIO);
        assertThat(testSocio.getFechaNacimientoSocio()).isEqualTo(UPDATED_FECHA_NACIMIENTO_SOCIO);
        assertThat(testSocio.getSexoSocio()).isEqualTo(DEFAULT_SEXO_SOCIO);
        assertThat(testSocio.getFechaAltaSocio()).isEqualTo(UPDATED_FECHA_ALTA_SOCIO);
        assertThat(testSocio.getFechaBajaSocio()).isEqualTo(DEFAULT_FECHA_BAJA_SOCIO);
        assertThat(testSocio.getContribucionMensual()).isEqualTo(DEFAULT_CONTRIBUCION_MENSUAL);
        assertThat(testSocio.getPeriodoPago()).isEqualTo(UPDATED_PERIODO_PAGO);
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
            .nombreSocio(UPDATED_NOMBRE_SOCIO)
            .primerApellidoSocio(UPDATED_PRIMER_APELLIDO_SOCIO)
            .segundoApellidoSocio(UPDATED_SEGUNDO_APELLIDO_SOCIO)
            .emailSocio(UPDATED_EMAIL_SOCIO)
            .telefonoContactoSocio(UPDATED_TELEFONO_CONTACTO_SOCIO)
            .dniSocio(UPDATED_DNI_SOCIO)
            .fechaNacimientoSocio(UPDATED_FECHA_NACIMIENTO_SOCIO)
            .sexoSocio(UPDATED_SEXO_SOCIO)
            .fechaAltaSocio(UPDATED_FECHA_ALTA_SOCIO)
            .fechaBajaSocio(UPDATED_FECHA_BAJA_SOCIO)
            .contribucionMensual(UPDATED_CONTRIBUCION_MENSUAL)
            .periodoPago(UPDATED_PERIODO_PAGO);

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
        assertThat(testSocio.getNombreSocio()).isEqualTo(UPDATED_NOMBRE_SOCIO);
        assertThat(testSocio.getPrimerApellidoSocio()).isEqualTo(UPDATED_PRIMER_APELLIDO_SOCIO);
        assertThat(testSocio.getSegundoApellidoSocio()).isEqualTo(UPDATED_SEGUNDO_APELLIDO_SOCIO);
        assertThat(testSocio.getEmailSocio()).isEqualTo(UPDATED_EMAIL_SOCIO);
        assertThat(testSocio.getTelefonoContactoSocio()).isEqualTo(UPDATED_TELEFONO_CONTACTO_SOCIO);
        assertThat(testSocio.getDniSocio()).isEqualTo(UPDATED_DNI_SOCIO);
        assertThat(testSocio.getFechaNacimientoSocio()).isEqualTo(UPDATED_FECHA_NACIMIENTO_SOCIO);
        assertThat(testSocio.getSexoSocio()).isEqualTo(UPDATED_SEXO_SOCIO);
        assertThat(testSocio.getFechaAltaSocio()).isEqualTo(UPDATED_FECHA_ALTA_SOCIO);
        assertThat(testSocio.getFechaBajaSocio()).isEqualTo(UPDATED_FECHA_BAJA_SOCIO);
        assertThat(testSocio.getContribucionMensual()).isEqualTo(UPDATED_CONTRIBUCION_MENSUAL);
        assertThat(testSocio.getPeriodoPago()).isEqualTo(UPDATED_PERIODO_PAGO);
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
