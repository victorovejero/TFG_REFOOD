package com.refood.trazabilidad.web.rest;

import static com.refood.trazabilidad.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.AlEnt;
import com.refood.trazabilidad.repository.AlEntRepository;
import com.refood.trazabilidad.service.dto.AlEntDTO;
import com.refood.trazabilidad.service.mapper.AlEntMapper;
import java.time.Instant;
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
 * Integration tests for the {@link AlEntResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlEntResourceIT {

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final Boolean DEFAULT_FRUTA_Y_VERDURA = false;
    private static final Boolean UPDATED_FRUTA_Y_VERDURA = true;

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_ENTRADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_ENTRADA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_RECOGIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_RECOGIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_Y_HORA_PREPARACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_Y_HORA_PREPARACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/al-ents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlEntRepository alEntRepository;

    @Autowired
    private AlEntMapper alEntMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlEntMockMvc;

    private AlEnt alEnt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlEnt createEntity(EntityManager em) {
        AlEnt alEnt = new AlEnt()
            .peso(DEFAULT_PESO)
            .frutaYVerdura(DEFAULT_FRUTA_Y_VERDURA)
            .fechaYHoraEntrada(DEFAULT_FECHA_Y_HORA_ENTRADA)
            .fechaYHoraRecogida(DEFAULT_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(DEFAULT_FECHA_Y_HORA_PREPARACION);
        return alEnt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlEnt createUpdatedEntity(EntityManager em) {
        AlEnt alEnt = new AlEnt()
            .peso(UPDATED_PESO)
            .frutaYVerdura(UPDATED_FRUTA_Y_VERDURA)
            .fechaYHoraEntrada(UPDATED_FECHA_Y_HORA_ENTRADA)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION);
        return alEnt;
    }

    @BeforeEach
    public void initTest() {
        alEnt = createEntity(em);
    }

    @Test
    @Transactional
    void createAlEnt() throws Exception {
        int databaseSizeBeforeCreate = alEntRepository.findAll().size();
        // Create the AlEnt
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);
        restAlEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alEntDTO)))
            .andExpect(status().isCreated());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeCreate + 1);
        AlEnt testAlEnt = alEntList.get(alEntList.size() - 1);
        assertThat(testAlEnt.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testAlEnt.getFrutaYVerdura()).isEqualTo(DEFAULT_FRUTA_Y_VERDURA);
        assertThat(testAlEnt.getFechaYHoraEntrada()).isEqualTo(DEFAULT_FECHA_Y_HORA_ENTRADA);
        assertThat(testAlEnt.getFechaYHoraRecogida()).isEqualTo(DEFAULT_FECHA_Y_HORA_RECOGIDA);
        assertThat(testAlEnt.getFechaYHoraPreparacion()).isEqualTo(DEFAULT_FECHA_Y_HORA_PREPARACION);
    }

    @Test
    @Transactional
    void createAlEntWithExistingId() throws Exception {
        // Create the AlEnt with an existing ID
        alEnt.setId(1L);
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        int databaseSizeBeforeCreate = alEntRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alEntDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alEntRepository.findAll().size();
        // set the field null
        alEnt.setPeso(null);

        // Create the AlEnt, which fails.
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        restAlEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alEntDTO)))
            .andExpect(status().isBadRequest());

        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFrutaYVerduraIsRequired() throws Exception {
        int databaseSizeBeforeTest = alEntRepository.findAll().size();
        // set the field null
        alEnt.setFrutaYVerdura(null);

        // Create the AlEnt, which fails.
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        restAlEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alEntDTO)))
            .andExpect(status().isBadRequest());

        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaYHoraEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alEntRepository.findAll().size();
        // set the field null
        alEnt.setFechaYHoraEntrada(null);

        // Create the AlEnt, which fails.
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        restAlEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alEntDTO)))
            .andExpect(status().isBadRequest());

        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlEnts() throws Exception {
        // Initialize the database
        alEntRepository.saveAndFlush(alEnt);

        // Get all the alEntList
        restAlEntMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alEnt.getId().intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].frutaYVerdura").value(hasItem(DEFAULT_FRUTA_Y_VERDURA.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaYHoraEntrada").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_ENTRADA))))
            .andExpect(jsonPath("$.[*].fechaYHoraRecogida").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_RECOGIDA))))
            .andExpect(jsonPath("$.[*].fechaYHoraPreparacion").value(hasItem(sameInstant(DEFAULT_FECHA_Y_HORA_PREPARACION))));
    }

    @Test
    @Transactional
    void getAlEnt() throws Exception {
        // Initialize the database
        alEntRepository.saveAndFlush(alEnt);

        // Get the alEnt
        restAlEntMockMvc
            .perform(get(ENTITY_API_URL_ID, alEnt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alEnt.getId().intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.frutaYVerdura").value(DEFAULT_FRUTA_Y_VERDURA.booleanValue()))
            .andExpect(jsonPath("$.fechaYHoraEntrada").value(sameInstant(DEFAULT_FECHA_Y_HORA_ENTRADA)))
            .andExpect(jsonPath("$.fechaYHoraRecogida").value(sameInstant(DEFAULT_FECHA_Y_HORA_RECOGIDA)))
            .andExpect(jsonPath("$.fechaYHoraPreparacion").value(sameInstant(DEFAULT_FECHA_Y_HORA_PREPARACION)));
    }

    @Test
    @Transactional
    void getNonExistingAlEnt() throws Exception {
        // Get the alEnt
        restAlEntMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlEnt() throws Exception {
        // Initialize the database
        alEntRepository.saveAndFlush(alEnt);

        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();

        // Update the alEnt
        AlEnt updatedAlEnt = alEntRepository.findById(alEnt.getId()).get();
        // Disconnect from session so that the updates on updatedAlEnt are not directly saved in db
        em.detach(updatedAlEnt);
        updatedAlEnt
            .peso(UPDATED_PESO)
            .frutaYVerdura(UPDATED_FRUTA_Y_VERDURA)
            .fechaYHoraEntrada(UPDATED_FECHA_Y_HORA_ENTRADA)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION);
        AlEntDTO alEntDTO = alEntMapper.toDto(updatedAlEnt);

        restAlEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alEntDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alEntDTO))
            )
            .andExpect(status().isOk());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
        AlEnt testAlEnt = alEntList.get(alEntList.size() - 1);
        assertThat(testAlEnt.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAlEnt.getFrutaYVerdura()).isEqualTo(UPDATED_FRUTA_Y_VERDURA);
        assertThat(testAlEnt.getFechaYHoraEntrada()).isEqualTo(UPDATED_FECHA_Y_HORA_ENTRADA);
        assertThat(testAlEnt.getFechaYHoraRecogida()).isEqualTo(UPDATED_FECHA_Y_HORA_RECOGIDA);
        assertThat(testAlEnt.getFechaYHoraPreparacion()).isEqualTo(UPDATED_FECHA_Y_HORA_PREPARACION);
    }

    @Test
    @Transactional
    void putNonExistingAlEnt() throws Exception {
        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();
        alEnt.setId(count.incrementAndGet());

        // Create the AlEnt
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alEntDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlEnt() throws Exception {
        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();
        alEnt.setId(count.incrementAndGet());

        // Create the AlEnt
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlEnt() throws Exception {
        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();
        alEnt.setId(count.incrementAndGet());

        // Create the AlEnt
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlEntMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alEntDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlEntWithPatch() throws Exception {
        // Initialize the database
        alEntRepository.saveAndFlush(alEnt);

        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();

        // Update the alEnt using partial update
        AlEnt partialUpdatedAlEnt = new AlEnt();
        partialUpdatedAlEnt.setId(alEnt.getId());

        partialUpdatedAlEnt
            .peso(UPDATED_PESO)
            .fechaYHoraEntrada(UPDATED_FECHA_Y_HORA_ENTRADA)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION);

        restAlEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlEnt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlEnt))
            )
            .andExpect(status().isOk());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
        AlEnt testAlEnt = alEntList.get(alEntList.size() - 1);
        assertThat(testAlEnt.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAlEnt.getFrutaYVerdura()).isEqualTo(DEFAULT_FRUTA_Y_VERDURA);
        assertThat(testAlEnt.getFechaYHoraEntrada()).isEqualTo(UPDATED_FECHA_Y_HORA_ENTRADA);
        assertThat(testAlEnt.getFechaYHoraRecogida()).isEqualTo(DEFAULT_FECHA_Y_HORA_RECOGIDA);
        assertThat(testAlEnt.getFechaYHoraPreparacion()).isEqualTo(UPDATED_FECHA_Y_HORA_PREPARACION);
    }

    @Test
    @Transactional
    void fullUpdateAlEntWithPatch() throws Exception {
        // Initialize the database
        alEntRepository.saveAndFlush(alEnt);

        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();

        // Update the alEnt using partial update
        AlEnt partialUpdatedAlEnt = new AlEnt();
        partialUpdatedAlEnt.setId(alEnt.getId());

        partialUpdatedAlEnt
            .peso(UPDATED_PESO)
            .frutaYVerdura(UPDATED_FRUTA_Y_VERDURA)
            .fechaYHoraEntrada(UPDATED_FECHA_Y_HORA_ENTRADA)
            .fechaYHoraRecogida(UPDATED_FECHA_Y_HORA_RECOGIDA)
            .fechaYHoraPreparacion(UPDATED_FECHA_Y_HORA_PREPARACION);

        restAlEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlEnt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlEnt))
            )
            .andExpect(status().isOk());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
        AlEnt testAlEnt = alEntList.get(alEntList.size() - 1);
        assertThat(testAlEnt.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAlEnt.getFrutaYVerdura()).isEqualTo(UPDATED_FRUTA_Y_VERDURA);
        assertThat(testAlEnt.getFechaYHoraEntrada()).isEqualTo(UPDATED_FECHA_Y_HORA_ENTRADA);
        assertThat(testAlEnt.getFechaYHoraRecogida()).isEqualTo(UPDATED_FECHA_Y_HORA_RECOGIDA);
        assertThat(testAlEnt.getFechaYHoraPreparacion()).isEqualTo(UPDATED_FECHA_Y_HORA_PREPARACION);
    }

    @Test
    @Transactional
    void patchNonExistingAlEnt() throws Exception {
        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();
        alEnt.setId(count.incrementAndGet());

        // Create the AlEnt
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alEntDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlEnt() throws Exception {
        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();
        alEnt.setId(count.incrementAndGet());

        // Create the AlEnt
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlEnt() throws Exception {
        int databaseSizeBeforeUpdate = alEntRepository.findAll().size();
        alEnt.setId(count.incrementAndGet());

        // Create the AlEnt
        AlEntDTO alEntDTO = alEntMapper.toDto(alEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlEntMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alEntDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlEnt in the database
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlEnt() throws Exception {
        // Initialize the database
        alEntRepository.saveAndFlush(alEnt);

        int databaseSizeBeforeDelete = alEntRepository.findAll().size();

        // Delete the alEnt
        restAlEntMockMvc
            .perform(delete(ENTITY_API_URL_ID, alEnt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlEnt> alEntList = alEntRepository.findAll();
        assertThat(alEntList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
