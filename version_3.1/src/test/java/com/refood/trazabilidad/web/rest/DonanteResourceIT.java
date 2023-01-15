package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Donante;
import com.refood.trazabilidad.repository.DonanteRepository;
import com.refood.trazabilidad.service.dto.DonanteDTO;
import com.refood.trazabilidad.service.mapper.DonanteMapper;
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
 * Integration tests for the {@link DonanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DonanteResourceIT {

    private static final String DEFAULT_ID_DONANTE = "AAAAAAAAAA";
    private static final String UPDATED_ID_DONANTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_BAJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_BAJA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMENTARIOS = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIOS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/donantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonanteRepository donanteRepository;

    @Autowired
    private DonanteMapper donanteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonanteMockMvc;

    private Donante donante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donante createEntity(EntityManager em) {
        Donante donante = new Donante()
            .idDonante(DEFAULT_ID_DONANTE)
            .nombre(DEFAULT_NOMBRE)
            .categoria(DEFAULT_CATEGORIA)
            .direccion(DEFAULT_DIRECCION)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .provincia(DEFAULT_PROVINCIA)
            .telefono(DEFAULT_TELEFONO)
            .email(DEFAULT_EMAIL)
            .responsable(DEFAULT_RESPONSABLE)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaBaja(DEFAULT_FECHA_BAJA)
            .comentarios(DEFAULT_COMENTARIOS)
            .activo(DEFAULT_ACTIVO);
        return donante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donante createUpdatedEntity(EntityManager em) {
        Donante donante = new Donante()
            .idDonante(UPDATED_ID_DONANTE)
            .nombre(UPDATED_NOMBRE)
            .categoria(UPDATED_CATEGORIA)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .responsable(UPDATED_RESPONSABLE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .comentarios(UPDATED_COMENTARIOS)
            .activo(UPDATED_ACTIVO);
        return donante;
    }

    @BeforeEach
    public void initTest() {
        donante = createEntity(em);
    }

    @Test
    @Transactional
    void createDonante() throws Exception {
        int databaseSizeBeforeCreate = donanteRepository.findAll().size();
        // Create the Donante
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);
        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isCreated());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeCreate + 1);
        Donante testDonante = donanteList.get(donanteList.size() - 1);
        assertThat(testDonante.getIdDonante()).isEqualTo(DEFAULT_ID_DONANTE);
        assertThat(testDonante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDonante.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testDonante.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testDonante.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testDonante.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testDonante.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testDonante.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDonante.getResponsable()).isEqualTo(DEFAULT_RESPONSABLE);
        assertThat(testDonante.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testDonante.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testDonante.getComentarios()).isEqualTo(DEFAULT_COMENTARIOS);
        assertThat(testDonante.getActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    void createDonanteWithExistingId() throws Exception {
        // Create the Donante with an existing ID
        donante.setId(1L);
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        int databaseSizeBeforeCreate = donanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdDonanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setIdDonante(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setNombre(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setCategoria(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setDireccion(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoPostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setCodigoPostal(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setProvincia(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setTelefono(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setEmail(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResponsableIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setResponsable(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setFechaAlta(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setActivo(null);

        // Create the Donante, which fails.
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        restDonanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonantes() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        // Get all the donanteList
        restDonanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donante.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDonante").value(hasItem(DEFAULT_ID_DONANTE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].responsable").value(hasItem(DEFAULT_RESPONSABLE)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())))
            .andExpect(jsonPath("$.[*].comentarios").value(hasItem(DEFAULT_COMENTARIOS)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getDonante() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        // Get the donante
        restDonanteMockMvc
            .perform(get(ENTITY_API_URL_ID, donante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donante.getId().intValue()))
            .andExpect(jsonPath("$.idDonante").value(DEFAULT_ID_DONANTE))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.responsable").value(DEFAULT_RESPONSABLE))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()))
            .andExpect(jsonPath("$.comentarios").value(DEFAULT_COMENTARIOS))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDonante() throws Exception {
        // Get the donante
        restDonanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDonante() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();

        // Update the donante
        Donante updatedDonante = donanteRepository.findById(donante.getId()).get();
        // Disconnect from session so that the updates on updatedDonante are not directly saved in db
        em.detach(updatedDonante);
        updatedDonante
            .idDonante(UPDATED_ID_DONANTE)
            .nombre(UPDATED_NOMBRE)
            .categoria(UPDATED_CATEGORIA)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .responsable(UPDATED_RESPONSABLE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .comentarios(UPDATED_COMENTARIOS)
            .activo(UPDATED_ACTIVO);
        DonanteDTO donanteDTO = donanteMapper.toDto(updatedDonante);

        restDonanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donanteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donanteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
        Donante testDonante = donanteList.get(donanteList.size() - 1);
        assertThat(testDonante.getIdDonante()).isEqualTo(UPDATED_ID_DONANTE);
        assertThat(testDonante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDonante.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testDonante.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testDonante.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testDonante.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testDonante.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDonante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDonante.getResponsable()).isEqualTo(UPDATED_RESPONSABLE);
        assertThat(testDonante.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testDonante.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testDonante.getComentarios()).isEqualTo(UPDATED_COMENTARIOS);
        assertThat(testDonante.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void putNonExistingDonante() throws Exception {
        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();
        donante.setId(count.incrementAndGet());

        // Create the Donante
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donanteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonante() throws Exception {
        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();
        donante.setId(count.incrementAndGet());

        // Create the Donante
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonante() throws Exception {
        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();
        donante.setId(count.incrementAndGet());

        // Create the Donante
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonanteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donanteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonanteWithPatch() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();

        // Update the donante using partial update
        Donante partialUpdatedDonante = new Donante();
        partialUpdatedDonante.setId(donante.getId());

        partialUpdatedDonante
            .idDonante(UPDATED_ID_DONANTE)
            .nombre(UPDATED_NOMBRE)
            .categoria(UPDATED_CATEGORIA)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .activo(UPDATED_ACTIVO);

        restDonanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonante))
            )
            .andExpect(status().isOk());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
        Donante testDonante = donanteList.get(donanteList.size() - 1);
        assertThat(testDonante.getIdDonante()).isEqualTo(UPDATED_ID_DONANTE);
        assertThat(testDonante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDonante.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testDonante.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testDonante.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testDonante.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testDonante.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDonante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDonante.getResponsable()).isEqualTo(DEFAULT_RESPONSABLE);
        assertThat(testDonante.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testDonante.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testDonante.getComentarios()).isEqualTo(DEFAULT_COMENTARIOS);
        assertThat(testDonante.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void fullUpdateDonanteWithPatch() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();

        // Update the donante using partial update
        Donante partialUpdatedDonante = new Donante();
        partialUpdatedDonante.setId(donante.getId());

        partialUpdatedDonante
            .idDonante(UPDATED_ID_DONANTE)
            .nombre(UPDATED_NOMBRE)
            .categoria(UPDATED_CATEGORIA)
            .direccion(UPDATED_DIRECCION)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .responsable(UPDATED_RESPONSABLE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .comentarios(UPDATED_COMENTARIOS)
            .activo(UPDATED_ACTIVO);

        restDonanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonante))
            )
            .andExpect(status().isOk());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
        Donante testDonante = donanteList.get(donanteList.size() - 1);
        assertThat(testDonante.getIdDonante()).isEqualTo(UPDATED_ID_DONANTE);
        assertThat(testDonante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDonante.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testDonante.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testDonante.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testDonante.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testDonante.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDonante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDonante.getResponsable()).isEqualTo(UPDATED_RESPONSABLE);
        assertThat(testDonante.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testDonante.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testDonante.getComentarios()).isEqualTo(UPDATED_COMENTARIOS);
        assertThat(testDonante.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void patchNonExistingDonante() throws Exception {
        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();
        donante.setId(count.incrementAndGet());

        // Create the Donante
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donanteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonante() throws Exception {
        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();
        donante.setId(count.incrementAndGet());

        // Create the Donante
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonante() throws Exception {
        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();
        donante.setId(count.incrementAndGet());

        // Create the Donante
        DonanteDTO donanteDTO = donanteMapper.toDto(donante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonanteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(donanteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonante() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        int databaseSizeBeforeDelete = donanteRepository.findAll().size();

        // Delete the donante
        restDonanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, donante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
