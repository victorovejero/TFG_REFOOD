package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.AlSal;
import com.refood.trazabilidad.repository.AlSalRepository;
import com.refood.trazabilidad.service.dto.AlSalDTO;
import com.refood.trazabilidad.service.mapper.AlSalMapper;
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
 * Integration tests for the {@link AlSalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlSalResourceIT {

    private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/al-sals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlSalRepository alSalRepository;

    @Autowired
    private AlSalMapper alSalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlSalMockMvc;

    private AlSal alSal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlSal createEntity(EntityManager em) {
        AlSal alSal = new AlSal().fechaSalida(DEFAULT_FECHA_SALIDA);
        return alSal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlSal createUpdatedEntity(EntityManager em) {
        AlSal alSal = new AlSal().fechaSalida(UPDATED_FECHA_SALIDA);
        return alSal;
    }

    @BeforeEach
    public void initTest() {
        alSal = createEntity(em);
    }

    @Test
    @Transactional
    void createAlSal() throws Exception {
        int databaseSizeBeforeCreate = alSalRepository.findAll().size();
        // Create the AlSal
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);
        restAlSalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alSalDTO)))
            .andExpect(status().isCreated());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeCreate + 1);
        AlSal testAlSal = alSalList.get(alSalList.size() - 1);
        assertThat(testAlSal.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
    }

    @Test
    @Transactional
    void createAlSalWithExistingId() throws Exception {
        // Create the AlSal with an existing ID
        alSal.setId(1L);
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);

        int databaseSizeBeforeCreate = alSalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlSalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alSalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaSalidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alSalRepository.findAll().size();
        // set the field null
        alSal.setFechaSalida(null);

        // Create the AlSal, which fails.
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);

        restAlSalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alSalDTO)))
            .andExpect(status().isBadRequest());

        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlSals() throws Exception {
        // Initialize the database
        alSalRepository.saveAndFlush(alSal);

        // Get all the alSalList
        restAlSalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alSal.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())));
    }

    @Test
    @Transactional
    void getAlSal() throws Exception {
        // Initialize the database
        alSalRepository.saveAndFlush(alSal);

        // Get the alSal
        restAlSalMockMvc
            .perform(get(ENTITY_API_URL_ID, alSal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alSal.getId().intValue()))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAlSal() throws Exception {
        // Get the alSal
        restAlSalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlSal() throws Exception {
        // Initialize the database
        alSalRepository.saveAndFlush(alSal);

        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();

        // Update the alSal
        AlSal updatedAlSal = alSalRepository.findById(alSal.getId()).get();
        // Disconnect from session so that the updates on updatedAlSal are not directly saved in db
        em.detach(updatedAlSal);
        updatedAlSal.fechaSalida(UPDATED_FECHA_SALIDA);
        AlSalDTO alSalDTO = alSalMapper.toDto(updatedAlSal);

        restAlSalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alSalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alSalDTO))
            )
            .andExpect(status().isOk());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
        AlSal testAlSal = alSalList.get(alSalList.size() - 1);
        assertThat(testAlSal.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
    }

    @Test
    @Transactional
    void putNonExistingAlSal() throws Exception {
        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();
        alSal.setId(count.incrementAndGet());

        // Create the AlSal
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlSalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alSalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alSalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlSal() throws Exception {
        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();
        alSal.setId(count.incrementAndGet());

        // Create the AlSal
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlSalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alSalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlSal() throws Exception {
        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();
        alSal.setId(count.incrementAndGet());

        // Create the AlSal
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlSalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alSalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlSalWithPatch() throws Exception {
        // Initialize the database
        alSalRepository.saveAndFlush(alSal);

        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();

        // Update the alSal using partial update
        AlSal partialUpdatedAlSal = new AlSal();
        partialUpdatedAlSal.setId(alSal.getId());

        restAlSalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlSal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlSal))
            )
            .andExpect(status().isOk());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
        AlSal testAlSal = alSalList.get(alSalList.size() - 1);
        assertThat(testAlSal.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
    }

    @Test
    @Transactional
    void fullUpdateAlSalWithPatch() throws Exception {
        // Initialize the database
        alSalRepository.saveAndFlush(alSal);

        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();

        // Update the alSal using partial update
        AlSal partialUpdatedAlSal = new AlSal();
        partialUpdatedAlSal.setId(alSal.getId());

        partialUpdatedAlSal.fechaSalida(UPDATED_FECHA_SALIDA);

        restAlSalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlSal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlSal))
            )
            .andExpect(status().isOk());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
        AlSal testAlSal = alSalList.get(alSalList.size() - 1);
        assertThat(testAlSal.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
    }

    @Test
    @Transactional
    void patchNonExistingAlSal() throws Exception {
        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();
        alSal.setId(count.incrementAndGet());

        // Create the AlSal
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlSalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alSalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alSalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlSal() throws Exception {
        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();
        alSal.setId(count.incrementAndGet());

        // Create the AlSal
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlSalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alSalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlSal() throws Exception {
        int databaseSizeBeforeUpdate = alSalRepository.findAll().size();
        alSal.setId(count.incrementAndGet());

        // Create the AlSal
        AlSalDTO alSalDTO = alSalMapper.toDto(alSal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlSalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alSalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlSal in the database
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlSal() throws Exception {
        // Initialize the database
        alSalRepository.saveAndFlush(alSal);

        int databaseSizeBeforeDelete = alSalRepository.findAll().size();

        // Delete the alSal
        restAlSalMockMvc
            .perform(delete(ENTITY_API_URL_ID, alSal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlSal> alSalList = alSalRepository.findAll();
        assertThat(alSalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
