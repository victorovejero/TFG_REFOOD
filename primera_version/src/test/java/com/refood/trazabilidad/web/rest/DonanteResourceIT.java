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

    private static final String DEFAULT_NOMBRE_DONANTE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DONANTE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_DONANTE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DONANTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_RUTA = 1;
    private static final Integer UPDATED_RUTA = 2;

    private static final String DEFAULT_DIRECCION_DONANTE = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_DONANTE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_DONANTE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_DONANTE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_DONANTE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_DONANTE = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE_DONANTE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE_DONANTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_BAJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_BAJA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMENTARIOS = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIOS = "BBBBBBBBBB";

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
            .nombreDonante(DEFAULT_NOMBRE_DONANTE)
            .tipoDonante(DEFAULT_TIPO_DONANTE)
            .ruta(DEFAULT_RUTA)
            .direccionDonante(DEFAULT_DIRECCION_DONANTE)
            .telefonoDonante(DEFAULT_TELEFONO_DONANTE)
            .emailDonante(DEFAULT_EMAIL_DONANTE)
            .responsableDonante(DEFAULT_RESPONSABLE_DONANTE)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaBaja(DEFAULT_FECHA_BAJA)
            .comentarios(DEFAULT_COMENTARIOS);
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
            .nombreDonante(UPDATED_NOMBRE_DONANTE)
            .tipoDonante(UPDATED_TIPO_DONANTE)
            .ruta(UPDATED_RUTA)
            .direccionDonante(UPDATED_DIRECCION_DONANTE)
            .telefonoDonante(UPDATED_TELEFONO_DONANTE)
            .emailDonante(UPDATED_EMAIL_DONANTE)
            .responsableDonante(UPDATED_RESPONSABLE_DONANTE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .comentarios(UPDATED_COMENTARIOS);
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
        assertThat(testDonante.getNombreDonante()).isEqualTo(DEFAULT_NOMBRE_DONANTE);
        assertThat(testDonante.getTipoDonante()).isEqualTo(DEFAULT_TIPO_DONANTE);
        assertThat(testDonante.getRuta()).isEqualTo(DEFAULT_RUTA);
        assertThat(testDonante.getDireccionDonante()).isEqualTo(DEFAULT_DIRECCION_DONANTE);
        assertThat(testDonante.getTelefonoDonante()).isEqualTo(DEFAULT_TELEFONO_DONANTE);
        assertThat(testDonante.getEmailDonante()).isEqualTo(DEFAULT_EMAIL_DONANTE);
        assertThat(testDonante.getResponsableDonante()).isEqualTo(DEFAULT_RESPONSABLE_DONANTE);
        assertThat(testDonante.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testDonante.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testDonante.getComentarios()).isEqualTo(DEFAULT_COMENTARIOS);
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
    void checkNombreDonanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setNombreDonante(null);

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
    void checkTipoDonanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setTipoDonante(null);

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
    void checkRutaIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setRuta(null);

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
    void checkDireccionDonanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setDireccionDonante(null);

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
    void checkTelefonoDonanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setTelefonoDonante(null);

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
    void checkEmailDonanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setEmailDonante(null);

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
    void checkResponsableDonanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setResponsableDonante(null);

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
    void getAllDonantes() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        // Get all the donanteList
        restDonanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreDonante").value(hasItem(DEFAULT_NOMBRE_DONANTE)))
            .andExpect(jsonPath("$.[*].tipoDonante").value(hasItem(DEFAULT_TIPO_DONANTE)))
            .andExpect(jsonPath("$.[*].ruta").value(hasItem(DEFAULT_RUTA)))
            .andExpect(jsonPath("$.[*].direccionDonante").value(hasItem(DEFAULT_DIRECCION_DONANTE)))
            .andExpect(jsonPath("$.[*].telefonoDonante").value(hasItem(DEFAULT_TELEFONO_DONANTE)))
            .andExpect(jsonPath("$.[*].emailDonante").value(hasItem(DEFAULT_EMAIL_DONANTE)))
            .andExpect(jsonPath("$.[*].responsableDonante").value(hasItem(DEFAULT_RESPONSABLE_DONANTE)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())))
            .andExpect(jsonPath("$.[*].comentarios").value(hasItem(DEFAULT_COMENTARIOS)));
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
            .andExpect(jsonPath("$.nombreDonante").value(DEFAULT_NOMBRE_DONANTE))
            .andExpect(jsonPath("$.tipoDonante").value(DEFAULT_TIPO_DONANTE))
            .andExpect(jsonPath("$.ruta").value(DEFAULT_RUTA))
            .andExpect(jsonPath("$.direccionDonante").value(DEFAULT_DIRECCION_DONANTE))
            .andExpect(jsonPath("$.telefonoDonante").value(DEFAULT_TELEFONO_DONANTE))
            .andExpect(jsonPath("$.emailDonante").value(DEFAULT_EMAIL_DONANTE))
            .andExpect(jsonPath("$.responsableDonante").value(DEFAULT_RESPONSABLE_DONANTE))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()))
            .andExpect(jsonPath("$.comentarios").value(DEFAULT_COMENTARIOS));
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
            .nombreDonante(UPDATED_NOMBRE_DONANTE)
            .tipoDonante(UPDATED_TIPO_DONANTE)
            .ruta(UPDATED_RUTA)
            .direccionDonante(UPDATED_DIRECCION_DONANTE)
            .telefonoDonante(UPDATED_TELEFONO_DONANTE)
            .emailDonante(UPDATED_EMAIL_DONANTE)
            .responsableDonante(UPDATED_RESPONSABLE_DONANTE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .comentarios(UPDATED_COMENTARIOS);
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
        assertThat(testDonante.getNombreDonante()).isEqualTo(UPDATED_NOMBRE_DONANTE);
        assertThat(testDonante.getTipoDonante()).isEqualTo(UPDATED_TIPO_DONANTE);
        assertThat(testDonante.getRuta()).isEqualTo(UPDATED_RUTA);
        assertThat(testDonante.getDireccionDonante()).isEqualTo(UPDATED_DIRECCION_DONANTE);
        assertThat(testDonante.getTelefonoDonante()).isEqualTo(UPDATED_TELEFONO_DONANTE);
        assertThat(testDonante.getEmailDonante()).isEqualTo(UPDATED_EMAIL_DONANTE);
        assertThat(testDonante.getResponsableDonante()).isEqualTo(UPDATED_RESPONSABLE_DONANTE);
        assertThat(testDonante.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testDonante.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testDonante.getComentarios()).isEqualTo(UPDATED_COMENTARIOS);
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
            .nombreDonante(UPDATED_NOMBRE_DONANTE)
            .tipoDonante(UPDATED_TIPO_DONANTE)
            .ruta(UPDATED_RUTA)
            .responsableDonante(UPDATED_RESPONSABLE_DONANTE)
            .fechaAlta(UPDATED_FECHA_ALTA);

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
        assertThat(testDonante.getNombreDonante()).isEqualTo(UPDATED_NOMBRE_DONANTE);
        assertThat(testDonante.getTipoDonante()).isEqualTo(UPDATED_TIPO_DONANTE);
        assertThat(testDonante.getRuta()).isEqualTo(UPDATED_RUTA);
        assertThat(testDonante.getDireccionDonante()).isEqualTo(DEFAULT_DIRECCION_DONANTE);
        assertThat(testDonante.getTelefonoDonante()).isEqualTo(DEFAULT_TELEFONO_DONANTE);
        assertThat(testDonante.getEmailDonante()).isEqualTo(DEFAULT_EMAIL_DONANTE);
        assertThat(testDonante.getResponsableDonante()).isEqualTo(UPDATED_RESPONSABLE_DONANTE);
        assertThat(testDonante.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testDonante.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testDonante.getComentarios()).isEqualTo(DEFAULT_COMENTARIOS);
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
            .nombreDonante(UPDATED_NOMBRE_DONANTE)
            .tipoDonante(UPDATED_TIPO_DONANTE)
            .ruta(UPDATED_RUTA)
            .direccionDonante(UPDATED_DIRECCION_DONANTE)
            .telefonoDonante(UPDATED_TELEFONO_DONANTE)
            .emailDonante(UPDATED_EMAIL_DONANTE)
            .responsableDonante(UPDATED_RESPONSABLE_DONANTE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .comentarios(UPDATED_COMENTARIOS);

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
        assertThat(testDonante.getNombreDonante()).isEqualTo(UPDATED_NOMBRE_DONANTE);
        assertThat(testDonante.getTipoDonante()).isEqualTo(UPDATED_TIPO_DONANTE);
        assertThat(testDonante.getRuta()).isEqualTo(UPDATED_RUTA);
        assertThat(testDonante.getDireccionDonante()).isEqualTo(UPDATED_DIRECCION_DONANTE);
        assertThat(testDonante.getTelefonoDonante()).isEqualTo(UPDATED_TELEFONO_DONANTE);
        assertThat(testDonante.getEmailDonante()).isEqualTo(UPDATED_EMAIL_DONANTE);
        assertThat(testDonante.getResponsableDonante()).isEqualTo(UPDATED_RESPONSABLE_DONANTE);
        assertThat(testDonante.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testDonante.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testDonante.getComentarios()).isEqualTo(UPDATED_COMENTARIOS);
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
