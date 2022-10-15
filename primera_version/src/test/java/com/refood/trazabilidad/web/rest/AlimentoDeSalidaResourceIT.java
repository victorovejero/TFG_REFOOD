package com.refood.trazabilidad.web.rest;

import static com.refood.trazabilidad.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.AlimentoDeSalida;
import com.refood.trazabilidad.repository.AlimentoDeSalidaRepository;
import com.refood.trazabilidad.service.dto.AlimentoDeSalidaDTO;
import com.refood.trazabilidad.service.mapper.AlimentoDeSalidaMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link AlimentoDeSalidaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlimentoDeSalidaResourceIT {

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final LocalDate DEFAULT_FECHA_ENTRADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTRADA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_LOG = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_LOG = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_PREPARACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_PREPARACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_RECOGIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_RECOGIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/alimento-de-salidas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlimentoDeSalidaRepository alimentoDeSalidaRepository;

    @Autowired
    private AlimentoDeSalidaMapper alimentoDeSalidaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlimentoDeSalidaMockMvc;

    private AlimentoDeSalida alimentoDeSalida;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlimentoDeSalida createEntity(EntityManager em) {
        AlimentoDeSalida alimentoDeSalida = new AlimentoDeSalida()
            .peso(DEFAULT_PESO)
            .fechaEntrada(DEFAULT_FECHA_ENTRADA)
            .fechaSalida(DEFAULT_FECHA_SALIDA)
            .fechaYHoraLog(DEFAULT_FECHA_Y_HORA_LOG)
            .fechaYHoraPreparacion(DEFAULT_FECHA_Y_HORA_PREPARACION)
            .fechaYHoraRecogida(DEFAULT_FECHA_Y_HORA_RECOGIDA);
        return alimentoDeSalida;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlimentoDeSalida createUpdatedEntity(EntityManager em) {
        AlimentoDeSalida alimentoDeSalida = new AlimentoDeSalida()
            .peso(UPDATED_PESO)
            .fechaEntrada(UPDATED_FECHA_ENTRADA)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .fechaYHoraLog(UPDATED_FECHA_Y_HORA_LOG)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA);
        return alimentoDeSalida;
    }

    @BeforeEach
    public void initTest() {
        alimentoDeSalida = createEntity(em);
    }

    @Test
    @Transactional
    void createAlimentoDeSalida() throws Exception {
        int databaseSizeBeforeCreate = alimentoDeSalidaRepository.findAll().size();
        // Create the AlimentoDeSalida
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);
        restAlimentoDeSalidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeCreate + 1);
        AlimentoDeSalida testAlimentoDeSalida = alimentoDeSalidaList.get(alimentoDeSalidaList.size() - 1);
        assertThat(testAlimentoDeSalida.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testAlimentoDeSalida.getFechaEntrada()).isEqualTo(DEFAULT_FECHA_ENTRADA);
        assertThat(testAlimentoDeSalida.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testAlimentoDeSalida.getFechaYHoraLog()).isEqualTo(DEFAULT_FECHA_Y_HORA_LOG);
        assertThat(testAlimentoDeSalida.getFechaYHoraPreparacion()).isEqualTo(DEFAULT_FECHA_Y_HORA_PREPARACION);
        assertThat(testAlimentoDeSalida.getFechaYHoraRecogida()).isEqualTo(DEFAULT_FECHA_Y_HORA_RECOGIDA);
    }

    @Test
    @Transactional
    void createAlimentoDeSalidaWithExistingId() throws Exception {
        // Create the AlimentoDeSalida with an existing ID
        alimentoDeSalida.setId(1L);
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        int databaseSizeBeforeCreate = alimentoDeSalidaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlimentoDeSalidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alimentoDeSalidaRepository.findAll().size();
        // set the field null
        alimentoDeSalida.setPeso(null);

        // Create the AlimentoDeSalida, which fails.
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        restAlimentoDeSalidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alimentoDeSalidaRepository.findAll().size();
        // set the field null
        alimentoDeSalida.setFechaEntrada(null);

        // Create the AlimentoDeSalida, which fails.
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        restAlimentoDeSalidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaSalidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alimentoDeSalidaRepository.findAll().size();
        // set the field null
        alimentoDeSalida.setFechaSalida(null);

        // Create the AlimentoDeSalida, which fails.
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        restAlimentoDeSalidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaYHoraLogIsRequired() throws Exception {
        int databaseSizeBeforeTest = alimentoDeSalidaRepository.findAll().size();
        // set the field null
        alimentoDeSalida.setFechaYHoraLog(null);

        // Create the AlimentoDeSalida, which fails.
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        restAlimentoDeSalidaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlimentoDeSalidas() throws Exception {
        // Initialize the database
        alimentoDeSalidaRepository.saveAndFlush(alimentoDeSalida);

        // Get all the alimentoDeSalidaList
        restAlimentoDeSalidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alimentoDeSalida.getId().intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaEntrada").value(hasItem(DEFAULT_FECHA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())))
            .andExpect(jsonPath("$.[*].fechaYHoraLog").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_LOG))))
            .andExpect(jsonPath("$.[*].fechaYHoraPreparacion").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_PREPARACION))))
            .andExpect(jsonPath("$.[*].fechaYHoraRecogida").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_RECOGIDA))));
    }

    @Test
    @Transactional
    void getAlimentoDeSalida() throws Exception {
        // Initialize the database
        alimentoDeSalidaRepository.saveAndFlush(alimentoDeSalida);

        // Get the alimentoDeSalida
        restAlimentoDeSalidaMockMvc
            .perform(get(ENTITY_API_URL_ID, alimentoDeSalida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alimentoDeSalida.getId().intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.fechaEntrada").value(DEFAULT_FECHA_ENTRADA.toString()))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()))
            .andExpect(jsonPath("$.fechaYHoraLog").value(sameInstant(DEFAULT_FECHA_Y_HORA_LOG)))
            .andExpect(jsonPath("$.fechaYHoraPreparacion").value(sameInstant(DEFAULT_FECHA_Y_HORA_PREPARACION)))
            .andExpect(jsonPath("$.fechaYHoraRecogida").value(sameInstant(DEFAULT_FECHA_Y_HORA_RECOGIDA)));
    }

    @Test
    @Transactional
    void getNonExistingAlimentoDeSalida() throws Exception {
        // Get the alimentoDeSalida
        restAlimentoDeSalidaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlimentoDeSalida() throws Exception {
        // Initialize the database
        alimentoDeSalidaRepository.saveAndFlush(alimentoDeSalida);

        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();

        // Update the alimentoDeSalida
        AlimentoDeSalida updatedAlimentoDeSalida = alimentoDeSalidaRepository.findById(alimentoDeSalida.getId()).get();
        // Disconnect from session so that the updates on updatedAlimentoDeSalida are not directly saved in db
        em.detach(updatedAlimentoDeSalida);
        updatedAlimentoDeSalida
            .peso(UPDATED_PESO)
            .fechaEntrada(UPDATED_FECHA_ENTRADA)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .fechaYHoraLog(UPDATED_FECHA_Y_HORA_LOG)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA);
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(updatedAlimentoDeSalida);

        restAlimentoDeSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alimentoDeSalidaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
        AlimentoDeSalida testAlimentoDeSalida = alimentoDeSalidaList.get(alimentoDeSalidaList.size() - 1);
        assertThat(testAlimentoDeSalida.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAlimentoDeSalida.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testAlimentoDeSalida.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testAlimentoDeSalida.getFechaYHoraLog()).isEqualTo(UPDATED_FECHA_Y_HORA_LOG);
        assertThat(testAlimentoDeSalida.getFechaYHoraPreparacion()).isEqualTo(UPDATED_FECHA_Y_HORA_PREPARACION);
        assertThat(testAlimentoDeSalida.getFechaYHoraRecogida()).isEqualTo(UPDATED_FECHA_Y_HORA_RECOGIDA);
    }

    @Test
    @Transactional
    void putNonExistingAlimentoDeSalida() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();
        alimentoDeSalida.setId(count.incrementAndGet());

        // Create the AlimentoDeSalida
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlimentoDeSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alimentoDeSalidaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlimentoDeSalida() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();
        alimentoDeSalida.setId(count.incrementAndGet());

        // Create the AlimentoDeSalida
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlimentoDeSalidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlimentoDeSalida() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();
        alimentoDeSalida.setId(count.incrementAndGet());

        // Create the AlimentoDeSalida
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlimentoDeSalidaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlimentoDeSalidaWithPatch() throws Exception {
        // Initialize the database
        alimentoDeSalidaRepository.saveAndFlush(alimentoDeSalida);

        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();

        // Update the alimentoDeSalida using partial update
        AlimentoDeSalida partialUpdatedAlimentoDeSalida = new AlimentoDeSalida();
        partialUpdatedAlimentoDeSalida.setId(alimentoDeSalida.getId());

        partialUpdatedAlimentoDeSalida.fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA);

        restAlimentoDeSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlimentoDeSalida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlimentoDeSalida))
            )
            .andExpect(status().isOk());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
        AlimentoDeSalida testAlimentoDeSalida = alimentoDeSalidaList.get(alimentoDeSalidaList.size() - 1);
        assertThat(testAlimentoDeSalida.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testAlimentoDeSalida.getFechaEntrada()).isEqualTo(DEFAULT_FECHA_ENTRADA);
        assertThat(testAlimentoDeSalida.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testAlimentoDeSalida.getFechaYHoraLog()).isEqualTo(DEFAULT_FECHA_Y_HORA_LOG);
        assertThat(testAlimentoDeSalida.getFechaYHoraPreparacion()).isEqualTo(DEFAULT_FECHA_Y_HORA_PREPARACION);
        assertThat(testAlimentoDeSalida.getFechaYHoraRecogida()).isEqualTo(UPDATED_FECHA_Y_HORA_RECOGIDA);
    }

    @Test
    @Transactional
    void fullUpdateAlimentoDeSalidaWithPatch() throws Exception {
        // Initialize the database
        alimentoDeSalidaRepository.saveAndFlush(alimentoDeSalida);

        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();

        // Update the alimentoDeSalida using partial update
        AlimentoDeSalida partialUpdatedAlimentoDeSalida = new AlimentoDeSalida();
        partialUpdatedAlimentoDeSalida.setId(alimentoDeSalida.getId());

        partialUpdatedAlimentoDeSalida
            .peso(UPDATED_PESO)
            .fechaEntrada(UPDATED_FECHA_ENTRADA)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .fechaYHoraLog(UPDATED_FECHA_Y_HORA_LOG)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA);

        restAlimentoDeSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlimentoDeSalida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlimentoDeSalida))
            )
            .andExpect(status().isOk());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
        AlimentoDeSalida testAlimentoDeSalida = alimentoDeSalidaList.get(alimentoDeSalidaList.size() - 1);
        assertThat(testAlimentoDeSalida.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAlimentoDeSalida.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testAlimentoDeSalida.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testAlimentoDeSalida.getFechaYHoraLog()).isEqualTo(UPDATED_FECHA_Y_HORA_LOG);
        assertThat(testAlimentoDeSalida.getFechaYHoraPreparacion()).isEqualTo(UPDATED_FECHA_Y_HORA_PREPARACION);
        assertThat(testAlimentoDeSalida.getFechaYHoraRecogida()).isEqualTo(UPDATED_FECHA_Y_HORA_RECOGIDA);
    }

    @Test
    @Transactional
    void patchNonExistingAlimentoDeSalida() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();
        alimentoDeSalida.setId(count.incrementAndGet());

        // Create the AlimentoDeSalida
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlimentoDeSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alimentoDeSalidaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlimentoDeSalida() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();
        alimentoDeSalida.setId(count.incrementAndGet());

        // Create the AlimentoDeSalida
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlimentoDeSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlimentoDeSalida() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeSalidaRepository.findAll().size();
        alimentoDeSalida.setId(count.incrementAndGet());

        // Create the AlimentoDeSalida
        AlimentoDeSalidaDTO alimentoDeSalidaDTO = alimentoDeSalidaMapper.toDto(alimentoDeSalida);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlimentoDeSalidaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeSalidaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlimentoDeSalida in the database
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlimentoDeSalida() throws Exception {
        // Initialize the database
        alimentoDeSalidaRepository.saveAndFlush(alimentoDeSalida);

        int databaseSizeBeforeDelete = alimentoDeSalidaRepository.findAll().size();

        // Delete the alimentoDeSalida
        restAlimentoDeSalidaMockMvc
            .perform(delete(ENTITY_API_URL_ID, alimentoDeSalida.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlimentoDeSalida> alimentoDeSalidaList = alimentoDeSalidaRepository.findAll();
        assertThat(alimentoDeSalidaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
