package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Benef;
import com.refood.trazabilidad.repository.BenefRepository;
import com.refood.trazabilidad.service.BenefService;
import com.refood.trazabilidad.service.dto.BenefDTO;
import com.refood.trazabilidad.service.mapper.BenefMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link BenefResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BenefResourceIT {

    private static final String DEFAULT_ID_BENEFICIARIO = "AAAAAAAAAA";
    private static final String UPDATED_ID_BENEFICIARIO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_REPRESENTANTE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_REPRESENTANTE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO_REPRESENTANTE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO_REPRESENTANTE = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO_REPRESENTANTE = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO_REPRESENTANTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_PERSONAS = 1;
    private static final Integer UPDATED_NUMERO_PERSONAS = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_SECUNDARIO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_SECUNDARIO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_BAJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_BAJA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMERO_NINIOS = 1;
    private static final Integer UPDATED_NUMERO_NINIOS = 2;

    private static final String DEFAULT_ID_DUAL = "AAAAAAAAAA";
    private static final String UPDATED_ID_DUAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/benefs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BenefRepository benefRepository;

    @Mock
    private BenefRepository benefRepositoryMock;

    @Autowired
    private BenefMapper benefMapper;

    @Mock
    private BenefService benefServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenefMockMvc;

    private Benef benef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benef createEntity(EntityManager em) {
        Benef benef = new Benef()
            .idBeneficiario(DEFAULT_ID_BENEFICIARIO)
            .nombreRepresentante(DEFAULT_NOMBRE_REPRESENTANTE)
            .primerApellidoRepresentante(DEFAULT_PRIMER_APELLIDO_REPRESENTANTE)
            .segundoApellidoRepresentante(DEFAULT_SEGUNDO_APELLIDO_REPRESENTANTE)
            .numeroPersonas(DEFAULT_NUMERO_PERSONAS)
            .email(DEFAULT_EMAIL)
            .telefono(DEFAULT_TELEFONO)
            .telefonoSecundario(DEFAULT_TELEFONO_SECUNDARIO)
            .direccion(DEFAULT_DIRECCION)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaBaja(DEFAULT_FECHA_BAJA)
            .numeroNinios(DEFAULT_NUMERO_NINIOS)
            .idDual(DEFAULT_ID_DUAL)
            .activo(DEFAULT_ACTIVO);
        return benef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benef createUpdatedEntity(EntityManager em) {
        Benef benef = new Benef()
            .idBeneficiario(UPDATED_ID_BENEFICIARIO)
            .nombreRepresentante(UPDATED_NOMBRE_REPRESENTANTE)
            .primerApellidoRepresentante(UPDATED_PRIMER_APELLIDO_REPRESENTANTE)
            .segundoApellidoRepresentante(UPDATED_SEGUNDO_APELLIDO_REPRESENTANTE)
            .numeroPersonas(UPDATED_NUMERO_PERSONAS)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .telefonoSecundario(UPDATED_TELEFONO_SECUNDARIO)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .numeroNinios(UPDATED_NUMERO_NINIOS)
            .idDual(UPDATED_ID_DUAL)
            .activo(UPDATED_ACTIVO);
        return benef;
    }

    @BeforeEach
    public void initTest() {
        benef = createEntity(em);
    }

    @Test
    @Transactional
    void createBenef() throws Exception {
        int databaseSizeBeforeCreate = benefRepository.findAll().size();
        // Create the Benef
        BenefDTO benefDTO = benefMapper.toDto(benef);
        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isCreated());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeCreate + 1);
        Benef testBenef = benefList.get(benefList.size() - 1);
        assertThat(testBenef.getIdBeneficiario()).isEqualTo(DEFAULT_ID_BENEFICIARIO);
        assertThat(testBenef.getNombreRepresentante()).isEqualTo(DEFAULT_NOMBRE_REPRESENTANTE);
        assertThat(testBenef.getPrimerApellidoRepresentante()).isEqualTo(DEFAULT_PRIMER_APELLIDO_REPRESENTANTE);
        assertThat(testBenef.getSegundoApellidoRepresentante()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO_REPRESENTANTE);
        assertThat(testBenef.getNumeroPersonas()).isEqualTo(DEFAULT_NUMERO_PERSONAS);
        assertThat(testBenef.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBenef.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testBenef.getTelefonoSecundario()).isEqualTo(DEFAULT_TELEFONO_SECUNDARIO);
        assertThat(testBenef.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testBenef.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testBenef.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testBenef.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testBenef.getNumeroNinios()).isEqualTo(DEFAULT_NUMERO_NINIOS);
        assertThat(testBenef.getIdDual()).isEqualTo(DEFAULT_ID_DUAL);
        assertThat(testBenef.getActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    void createBenefWithExistingId() throws Exception {
        // Create the Benef with an existing ID
        benef.setId(1L);
        BenefDTO benefDTO = benefMapper.toDto(benef);

        int databaseSizeBeforeCreate = benefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdBeneficiarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setIdBeneficiario(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreRepresentanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setNombreRepresentante(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimerApellidoRepresentanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setPrimerApellidoRepresentante(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroPersonasIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setNumeroPersonas(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setEmail(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setTelefono(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoSecundarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setTelefonoSecundario(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setDireccion(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoPostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setCodigoPostal(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setFechaAlta(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroNiniosIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setNumeroNinios(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefRepository.findAll().size();
        // set the field null
        benef.setActivo(null);

        // Create the Benef, which fails.
        BenefDTO benefDTO = benefMapper.toDto(benef);

        restBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isBadRequest());

        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBenefs() throws Exception {
        // Initialize the database
        benefRepository.saveAndFlush(benef);

        // Get all the benefList
        restBenefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benef.getId().intValue())))
            .andExpect(jsonPath("$.[*].idBeneficiario").value(hasItem(DEFAULT_ID_BENEFICIARIO)))
            .andExpect(jsonPath("$.[*].nombreRepresentante").value(hasItem(DEFAULT_NOMBRE_REPRESENTANTE)))
            .andExpect(jsonPath("$.[*].primerApellidoRepresentante").value(hasItem(DEFAULT_PRIMER_APELLIDO_REPRESENTANTE)))
            .andExpect(jsonPath("$.[*].segundoApellidoRepresentante").value(hasItem(DEFAULT_SEGUNDO_APELLIDO_REPRESENTANTE)))
            .andExpect(jsonPath("$.[*].numeroPersonas").value(hasItem(DEFAULT_NUMERO_PERSONAS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].telefonoSecundario").value(hasItem(DEFAULT_TELEFONO_SECUNDARIO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())))
            .andExpect(jsonPath("$.[*].numeroNinios").value(hasItem(DEFAULT_NUMERO_NINIOS)))
            .andExpect(jsonPath("$.[*].idDual").value(hasItem(DEFAULT_ID_DUAL)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBenefsWithEagerRelationshipsIsEnabled() throws Exception {
        when(benefServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBenefMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(benefServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBenefsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(benefServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBenefMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(benefRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBenef() throws Exception {
        // Initialize the database
        benefRepository.saveAndFlush(benef);

        // Get the benef
        restBenefMockMvc
            .perform(get(ENTITY_API_URL_ID, benef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benef.getId().intValue()))
            .andExpect(jsonPath("$.idBeneficiario").value(DEFAULT_ID_BENEFICIARIO))
            .andExpect(jsonPath("$.nombreRepresentante").value(DEFAULT_NOMBRE_REPRESENTANTE))
            .andExpect(jsonPath("$.primerApellidoRepresentante").value(DEFAULT_PRIMER_APELLIDO_REPRESENTANTE))
            .andExpect(jsonPath("$.segundoApellidoRepresentante").value(DEFAULT_SEGUNDO_APELLIDO_REPRESENTANTE))
            .andExpect(jsonPath("$.numeroPersonas").value(DEFAULT_NUMERO_PERSONAS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.telefonoSecundario").value(DEFAULT_TELEFONO_SECUNDARIO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()))
            .andExpect(jsonPath("$.numeroNinios").value(DEFAULT_NUMERO_NINIOS))
            .andExpect(jsonPath("$.idDual").value(DEFAULT_ID_DUAL))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBenef() throws Exception {
        // Get the benef
        restBenefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenef() throws Exception {
        // Initialize the database
        benefRepository.saveAndFlush(benef);

        int databaseSizeBeforeUpdate = benefRepository.findAll().size();

        // Update the benef
        Benef updatedBenef = benefRepository.findById(benef.getId()).get();
        // Disconnect from session so that the updates on updatedBenef are not directly saved in db
        em.detach(updatedBenef);
        updatedBenef
            .idBeneficiario(UPDATED_ID_BENEFICIARIO)
            .nombreRepresentante(UPDATED_NOMBRE_REPRESENTANTE)
            .primerApellidoRepresentante(UPDATED_PRIMER_APELLIDO_REPRESENTANTE)
            .segundoApellidoRepresentante(UPDATED_SEGUNDO_APELLIDO_REPRESENTANTE)
            .numeroPersonas(UPDATED_NUMERO_PERSONAS)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .telefonoSecundario(UPDATED_TELEFONO_SECUNDARIO)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .numeroNinios(UPDATED_NUMERO_NINIOS)
            .idDual(UPDATED_ID_DUAL)
            .activo(UPDATED_ACTIVO);
        BenefDTO benefDTO = benefMapper.toDto(updatedBenef);

        restBenefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefDTO))
            )
            .andExpect(status().isOk());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
        Benef testBenef = benefList.get(benefList.size() - 1);
        assertThat(testBenef.getIdBeneficiario()).isEqualTo(UPDATED_ID_BENEFICIARIO);
        assertThat(testBenef.getNombreRepresentante()).isEqualTo(UPDATED_NOMBRE_REPRESENTANTE);
        assertThat(testBenef.getPrimerApellidoRepresentante()).isEqualTo(UPDATED_PRIMER_APELLIDO_REPRESENTANTE);
        assertThat(testBenef.getSegundoApellidoRepresentante()).isEqualTo(UPDATED_SEGUNDO_APELLIDO_REPRESENTANTE);
        assertThat(testBenef.getNumeroPersonas()).isEqualTo(UPDATED_NUMERO_PERSONAS);
        assertThat(testBenef.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBenef.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testBenef.getTelefonoSecundario()).isEqualTo(UPDATED_TELEFONO_SECUNDARIO);
        assertThat(testBenef.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testBenef.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testBenef.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testBenef.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testBenef.getNumeroNinios()).isEqualTo(UPDATED_NUMERO_NINIOS);
        assertThat(testBenef.getIdDual()).isEqualTo(UPDATED_ID_DUAL);
        assertThat(testBenef.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void putNonExistingBenef() throws Exception {
        int databaseSizeBeforeUpdate = benefRepository.findAll().size();
        benef.setId(count.incrementAndGet());

        // Create the Benef
        BenefDTO benefDTO = benefMapper.toDto(benef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenef() throws Exception {
        int databaseSizeBeforeUpdate = benefRepository.findAll().size();
        benef.setId(count.incrementAndGet());

        // Create the Benef
        BenefDTO benefDTO = benefMapper.toDto(benef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenef() throws Exception {
        int databaseSizeBeforeUpdate = benefRepository.findAll().size();
        benef.setId(count.incrementAndGet());

        // Create the Benef
        BenefDTO benefDTO = benefMapper.toDto(benef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenefWithPatch() throws Exception {
        // Initialize the database
        benefRepository.saveAndFlush(benef);

        int databaseSizeBeforeUpdate = benefRepository.findAll().size();

        // Update the benef using partial update
        Benef partialUpdatedBenef = new Benef();
        partialUpdatedBenef.setId(benef.getId());

        partialUpdatedBenef
            .primerApellidoRepresentante(UPDATED_PRIMER_APELLIDO_REPRESENTANTE)
            .segundoApellidoRepresentante(UPDATED_SEGUNDO_APELLIDO_REPRESENTANTE)
            .telefono(UPDATED_TELEFONO)
            .telefonoSecundario(UPDATED_TELEFONO_SECUNDARIO)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .numeroNinios(UPDATED_NUMERO_NINIOS)
            .idDual(UPDATED_ID_DUAL);

        restBenefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenef))
            )
            .andExpect(status().isOk());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
        Benef testBenef = benefList.get(benefList.size() - 1);
        assertThat(testBenef.getIdBeneficiario()).isEqualTo(DEFAULT_ID_BENEFICIARIO);
        assertThat(testBenef.getNombreRepresentante()).isEqualTo(DEFAULT_NOMBRE_REPRESENTANTE);
        assertThat(testBenef.getPrimerApellidoRepresentante()).isEqualTo(UPDATED_PRIMER_APELLIDO_REPRESENTANTE);
        assertThat(testBenef.getSegundoApellidoRepresentante()).isEqualTo(UPDATED_SEGUNDO_APELLIDO_REPRESENTANTE);
        assertThat(testBenef.getNumeroPersonas()).isEqualTo(DEFAULT_NUMERO_PERSONAS);
        assertThat(testBenef.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBenef.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testBenef.getTelefonoSecundario()).isEqualTo(UPDATED_TELEFONO_SECUNDARIO);
        assertThat(testBenef.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testBenef.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testBenef.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testBenef.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testBenef.getNumeroNinios()).isEqualTo(UPDATED_NUMERO_NINIOS);
        assertThat(testBenef.getIdDual()).isEqualTo(UPDATED_ID_DUAL);
        assertThat(testBenef.getActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    void fullUpdateBenefWithPatch() throws Exception {
        // Initialize the database
        benefRepository.saveAndFlush(benef);

        int databaseSizeBeforeUpdate = benefRepository.findAll().size();

        // Update the benef using partial update
        Benef partialUpdatedBenef = new Benef();
        partialUpdatedBenef.setId(benef.getId());

        partialUpdatedBenef
            .idBeneficiario(UPDATED_ID_BENEFICIARIO)
            .nombreRepresentante(UPDATED_NOMBRE_REPRESENTANTE)
            .primerApellidoRepresentante(UPDATED_PRIMER_APELLIDO_REPRESENTANTE)
            .segundoApellidoRepresentante(UPDATED_SEGUNDO_APELLIDO_REPRESENTANTE)
            .numeroPersonas(UPDATED_NUMERO_PERSONAS)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .telefonoSecundario(UPDATED_TELEFONO_SECUNDARIO)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .numeroNinios(UPDATED_NUMERO_NINIOS)
            .idDual(UPDATED_ID_DUAL)
            .activo(UPDATED_ACTIVO);

        restBenefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenef))
            )
            .andExpect(status().isOk());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
        Benef testBenef = benefList.get(benefList.size() - 1);
        assertThat(testBenef.getIdBeneficiario()).isEqualTo(UPDATED_ID_BENEFICIARIO);
        assertThat(testBenef.getNombreRepresentante()).isEqualTo(UPDATED_NOMBRE_REPRESENTANTE);
        assertThat(testBenef.getPrimerApellidoRepresentante()).isEqualTo(UPDATED_PRIMER_APELLIDO_REPRESENTANTE);
        assertThat(testBenef.getSegundoApellidoRepresentante()).isEqualTo(UPDATED_SEGUNDO_APELLIDO_REPRESENTANTE);
        assertThat(testBenef.getNumeroPersonas()).isEqualTo(UPDATED_NUMERO_PERSONAS);
        assertThat(testBenef.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBenef.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testBenef.getTelefonoSecundario()).isEqualTo(UPDATED_TELEFONO_SECUNDARIO);
        assertThat(testBenef.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testBenef.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testBenef.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testBenef.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testBenef.getNumeroNinios()).isEqualTo(UPDATED_NUMERO_NINIOS);
        assertThat(testBenef.getIdDual()).isEqualTo(UPDATED_ID_DUAL);
        assertThat(testBenef.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void patchNonExistingBenef() throws Exception {
        int databaseSizeBeforeUpdate = benefRepository.findAll().size();
        benef.setId(count.incrementAndGet());

        // Create the Benef
        BenefDTO benefDTO = benefMapper.toDto(benef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenef() throws Exception {
        int databaseSizeBeforeUpdate = benefRepository.findAll().size();
        benef.setId(count.incrementAndGet());

        // Create the Benef
        BenefDTO benefDTO = benefMapper.toDto(benef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenef() throws Exception {
        int databaseSizeBeforeUpdate = benefRepository.findAll().size();
        benef.setId(count.incrementAndGet());

        // Create the Benef
        BenefDTO benefDTO = benefMapper.toDto(benef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(benefDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benef in the database
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenef() throws Exception {
        // Initialize the database
        benefRepository.saveAndFlush(benef);

        int databaseSizeBeforeDelete = benefRepository.findAll().size();

        // Delete the benef
        restBenefMockMvc
            .perform(delete(ENTITY_API_URL_ID, benef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Benef> benefList = benefRepository.findAll();
        assertThat(benefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
