package com.refood.trazabilidad.web.rest;

import static com.refood.trazabilidad.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.AlimentoDeEntrada;
import com.refood.trazabilidad.repository.AlimentoDeEntradaRepository;
import com.refood.trazabilidad.service.dto.AlimentoDeEntradaDTO;
import com.refood.trazabilidad.service.mapper.AlimentoDeEntradaMapper;
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
 * Integration tests for the {@link AlimentoDeEntradaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlimentoDeEntradaResourceIT {

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final LocalDate DEFAULT_FECHA_ENTRADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTRADA = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_LOG = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_LOG = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_RECOGIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_RECOGIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_PREPARACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_PREPARACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/alimento-de-entradas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlimentoDeEntradaRepository alimentoDeEntradaRepository;

    @Autowired
    private AlimentoDeEntradaMapper alimentoDeEntradaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlimentoDeEntradaMockMvc;

    private AlimentoDeEntrada alimentoDeEntrada;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlimentoDeEntrada createEntity(EntityManager em) {
        AlimentoDeEntrada alimentoDeEntrada = new AlimentoDeEntrada()
            .peso(DEFAULT_PESO)
            .fechaEntrada(DEFAULT_FECHA_ENTRADA)
            .fechaYHoraLog(DEFAULT_FECHA_Y_HORA_LOG)
            .fechaYHoraRecogida(DEFAULT_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(DEFAULT_FECHA_Y_HORA_PREPARACION);
        return alimentoDeEntrada;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlimentoDeEntrada createUpdatedEntity(EntityManager em) {
        AlimentoDeEntrada alimentoDeEntrada = new AlimentoDeEntrada()
            .peso(UPDATED_PESO)
            .fechaEntrada(UPDATED_FECHA_ENTRADA)
            .fechaYHoraLog(UPDATED_FECHA_Y_HORA_LOG)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION);
        return alimentoDeEntrada;
    }

    @BeforeEach
    public void initTest() {
        alimentoDeEntrada = createEntity(em);
    }

    @Test
    @Transactional
    void createAlimentoDeEntrada() throws Exception {
        int databaseSizeBeforeCreate = alimentoDeEntradaRepository.findAll().size();
        // Create the AlimentoDeEntrada
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);
        restAlimentoDeEntradaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeCreate + 1);
        AlimentoDeEntrada testAlimentoDeEntrada = alimentoDeEntradaList.get(alimentoDeEntradaList.size() - 1);
        assertThat(testAlimentoDeEntrada.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testAlimentoDeEntrada.getFechaEntrada()).isEqualTo(DEFAULT_FECHA_ENTRADA);
        assertThat(testAlimentoDeEntrada.getFechaYHoraLog()).isEqualTo(DEFAULT_FECHA_Y_HORA_LOG);
        assertThat(testAlimentoDeEntrada.getFechaYHoraRecogida()).isEqualTo(DEFAULT_FECHA_Y_HORA_RECOGIDA);
        assertThat(testAlimentoDeEntrada.getFechaYHoraPreparacion()).isEqualTo(DEFAULT_FECHA_Y_HORA_PREPARACION);
    }

    @Test
    @Transactional
    void createAlimentoDeEntradaWithExistingId() throws Exception {
        // Create the AlimentoDeEntrada with an existing ID
        alimentoDeEntrada.setId(1L);
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        int databaseSizeBeforeCreate = alimentoDeEntradaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlimentoDeEntradaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alimentoDeEntradaRepository.findAll().size();
        // set the field null
        alimentoDeEntrada.setPeso(null);

        // Create the AlimentoDeEntrada, which fails.
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        restAlimentoDeEntradaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isBadRequest());

        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alimentoDeEntradaRepository.findAll().size();
        // set the field null
        alimentoDeEntrada.setFechaEntrada(null);

        // Create the AlimentoDeEntrada, which fails.
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        restAlimentoDeEntradaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isBadRequest());

        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaYHoraLogIsRequired() throws Exception {
        int databaseSizeBeforeTest = alimentoDeEntradaRepository.findAll().size();
        // set the field null
        alimentoDeEntrada.setFechaYHoraLog(null);

        // Create the AlimentoDeEntrada, which fails.
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        restAlimentoDeEntradaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isBadRequest());

        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlimentoDeEntradas() throws Exception {
        // Initialize the database
        alimentoDeEntradaRepository.saveAndFlush(alimentoDeEntrada);

        // Get all the alimentoDeEntradaList
        restAlimentoDeEntradaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alimentoDeEntrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaEntrada").value(hasItem(DEFAULT_FECHA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].fechaYHoraLog").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_LOG))))
            .andExpect(jsonPath("$.[*].fechaYHoraRecogida").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_RECOGIDA))))
            .andExpect(jsonPath("$.[*].fechaYHoraPreparacion").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_PREPARACION))));
    }

    @Test
    @Transactional
    void getAlimentoDeEntrada() throws Exception {
        // Initialize the database
        alimentoDeEntradaRepository.saveAndFlush(alimentoDeEntrada);

        // Get the alimentoDeEntrada
        restAlimentoDeEntradaMockMvc
            .perform(get(ENTITY_API_URL_ID, alimentoDeEntrada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alimentoDeEntrada.getId().intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.fechaEntrada").value(DEFAULT_FECHA_ENTRADA.toString()))
            .andExpect(jsonPath("$.fechaYHoraLog").value(sameInstant(DEFAULT_FECHA_Y_HORA_LOG)))
            .andExpect(jsonPath("$.fechaYHoraRecogida").value(sameInstant(DEFAULT_FECHA_Y_HORA_RECOGIDA)))
            .andExpect(jsonPath("$.fechaYHoraPreparacion").value(sameInstant(DEFAULT_FECHA_Y_HORA_PREPARACION)));
    }

    @Test
    @Transactional
    void getNonExistingAlimentoDeEntrada() throws Exception {
        // Get the alimentoDeEntrada
        restAlimentoDeEntradaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlimentoDeEntrada() throws Exception {
        // Initialize the database
        alimentoDeEntradaRepository.saveAndFlush(alimentoDeEntrada);

        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();

        // Update the alimentoDeEntrada
        AlimentoDeEntrada updatedAlimentoDeEntrada = alimentoDeEntradaRepository.findById(alimentoDeEntrada.getId()).get();
        // Disconnect from session so that the updates on updatedAlimentoDeEntrada are not directly saved in db
        em.detach(updatedAlimentoDeEntrada);
        updatedAlimentoDeEntrada
            .peso(UPDATED_PESO)
            .fechaEntrada(UPDATED_FECHA_ENTRADA)
            .fechaYHoraLog(UPDATED_FECHA_Y_HORA_LOG)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION);
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(updatedAlimentoDeEntrada);

        restAlimentoDeEntradaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alimentoDeEntradaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
        AlimentoDeEntrada testAlimentoDeEntrada = alimentoDeEntradaList.get(alimentoDeEntradaList.size() - 1);
        assertThat(testAlimentoDeEntrada.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAlimentoDeEntrada.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testAlimentoDeEntrada.getFechaYHoraLog()).isEqualTo(UPDATED_FECHA_Y_HORA_LOG);
        assertThat(testAlimentoDeEntrada.getFechaYHoraRecogida()).isEqualTo(UPDATED_FECHA_Y_HORA_RECOGIDA);
        assertThat(testAlimentoDeEntrada.getFechaYHoraPreparacion()).isEqualTo(UPDATED_FECHA_Y_HORA_PREPARACION);
    }

    @Test
    @Transactional
    void putNonExistingAlimentoDeEntrada() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();
        alimentoDeEntrada.setId(count.incrementAndGet());

        // Create the AlimentoDeEntrada
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlimentoDeEntradaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alimentoDeEntradaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlimentoDeEntrada() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();
        alimentoDeEntrada.setId(count.incrementAndGet());

        // Create the AlimentoDeEntrada
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlimentoDeEntradaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlimentoDeEntrada() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();
        alimentoDeEntrada.setId(count.incrementAndGet());

        // Create the AlimentoDeEntrada
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlimentoDeEntradaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlimentoDeEntradaWithPatch() throws Exception {
        // Initialize the database
        alimentoDeEntradaRepository.saveAndFlush(alimentoDeEntrada);

        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();

        // Update the alimentoDeEntrada using partial update
        AlimentoDeEntrada partialUpdatedAlimentoDeEntrada = new AlimentoDeEntrada();
        partialUpdatedAlimentoDeEntrada.setId(alimentoDeEntrada.getId());

        partialUpdatedAlimentoDeEntrada
            .peso(UPDATED_PESO)
            .fechaEntrada(UPDATED_FECHA_ENTRADA)
            .fechaYHoraLog(UPDATED_FECHA_Y_HORA_LOG)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION);

        restAlimentoDeEntradaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlimentoDeEntrada.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlimentoDeEntrada))
            )
            .andExpect(status().isOk());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
        AlimentoDeEntrada testAlimentoDeEntrada = alimentoDeEntradaList.get(alimentoDeEntradaList.size() - 1);
        assertThat(testAlimentoDeEntrada.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAlimentoDeEntrada.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testAlimentoDeEntrada.getFechaYHoraLog()).isEqualTo(UPDATED_FECHA_Y_HORA_LOG);
        assertThat(testAlimentoDeEntrada.getFechaYHoraRecogida()).isEqualTo(UPDATED_FECHA_Y_HORA_RECOGIDA);
        assertThat(testAlimentoDeEntrada.getFechaYHoraPreparacion()).isEqualTo(UPDATED_FECHA_Y_HORA_PREPARACION);
    }

    @Test
    @Transactional
    void fullUpdateAlimentoDeEntradaWithPatch() throws Exception {
        // Initialize the database
        alimentoDeEntradaRepository.saveAndFlush(alimentoDeEntrada);

        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();

        // Update the alimentoDeEntrada using partial update
        AlimentoDeEntrada partialUpdatedAlimentoDeEntrada = new AlimentoDeEntrada();
        partialUpdatedAlimentoDeEntrada.setId(alimentoDeEntrada.getId());

        partialUpdatedAlimentoDeEntrada
            .peso(UPDATED_PESO)
            .fechaEntrada(UPDATED_FECHA_ENTRADA)
            .fechaYHoraLog(UPDATED_FECHA_Y_HORA_LOG)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION);

        restAlimentoDeEntradaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlimentoDeEntrada.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlimentoDeEntrada))
            )
            .andExpect(status().isOk());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
        AlimentoDeEntrada testAlimentoDeEntrada = alimentoDeEntradaList.get(alimentoDeEntradaList.size() - 1);
        assertThat(testAlimentoDeEntrada.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAlimentoDeEntrada.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testAlimentoDeEntrada.getFechaYHoraLog()).isEqualTo(UPDATED_FECHA_Y_HORA_LOG);
        assertThat(testAlimentoDeEntrada.getFechaYHoraRecogida()).isEqualTo(UPDATED_FECHA_Y_HORA_RECOGIDA);
        assertThat(testAlimentoDeEntrada.getFechaYHoraPreparacion()).isEqualTo(UPDATED_FECHA_Y_HORA_PREPARACION);
    }

    @Test
    @Transactional
    void patchNonExistingAlimentoDeEntrada() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();
        alimentoDeEntrada.setId(count.incrementAndGet());

        // Create the AlimentoDeEntrada
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlimentoDeEntradaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alimentoDeEntradaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlimentoDeEntrada() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();
        alimentoDeEntrada.setId(count.incrementAndGet());

        // Create the AlimentoDeEntrada
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlimentoDeEntradaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlimentoDeEntrada() throws Exception {
        int databaseSizeBeforeUpdate = alimentoDeEntradaRepository.findAll().size();
        alimentoDeEntrada.setId(count.incrementAndGet());

        // Create the AlimentoDeEntrada
        AlimentoDeEntradaDTO alimentoDeEntradaDTO = alimentoDeEntradaMapper.toDto(alimentoDeEntrada);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlimentoDeEntradaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alimentoDeEntradaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlimentoDeEntrada in the database
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlimentoDeEntrada() throws Exception {
        // Initialize the database
        alimentoDeEntradaRepository.saveAndFlush(alimentoDeEntrada);

        int databaseSizeBeforeDelete = alimentoDeEntradaRepository.findAll().size();

        // Delete the alimentoDeEntrada
        restAlimentoDeEntradaMockMvc
            .perform(delete(ENTITY_API_URL_ID, alimentoDeEntrada.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlimentoDeEntrada> alimentoDeEntradaList = alimentoDeEntradaRepository.findAll();
        assertThat(alimentoDeEntradaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
