package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.FrutaYVerdura;
import com.refood.trazabilidad.repository.FrutaYVerduraRepository;
import com.refood.trazabilidad.service.dto.FrutaYVerduraDTO;
import com.refood.trazabilidad.service.mapper.FrutaYVerduraMapper;
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
 * Integration tests for the {@link FrutaYVerduraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FrutaYVerduraResourceIT {

    private static final String DEFAULT_NOMBRE_ALIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ALIMENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fruta-y-verduras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FrutaYVerduraRepository frutaYVerduraRepository;

    @Autowired
    private FrutaYVerduraMapper frutaYVerduraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFrutaYVerduraMockMvc;

    private FrutaYVerdura frutaYVerdura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FrutaYVerdura createEntity(EntityManager em) {
        FrutaYVerdura frutaYVerdura = new FrutaYVerdura().nombreAlimento(DEFAULT_NOMBRE_ALIMENTO);
        return frutaYVerdura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FrutaYVerdura createUpdatedEntity(EntityManager em) {
        FrutaYVerdura frutaYVerdura = new FrutaYVerdura().nombreAlimento(UPDATED_NOMBRE_ALIMENTO);
        return frutaYVerdura;
    }

    @BeforeEach
    public void initTest() {
        frutaYVerdura = createEntity(em);
    }

    @Test
    @Transactional
    void createFrutaYVerdura() throws Exception {
        int databaseSizeBeforeCreate = frutaYVerduraRepository.findAll().size();
        // Create the FrutaYVerdura
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);
        restFrutaYVerduraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeCreate + 1);
        FrutaYVerdura testFrutaYVerdura = frutaYVerduraList.get(frutaYVerduraList.size() - 1);
        assertThat(testFrutaYVerdura.getNombreAlimento()).isEqualTo(DEFAULT_NOMBRE_ALIMENTO);
    }

    @Test
    @Transactional
    void createFrutaYVerduraWithExistingId() throws Exception {
        // Create the FrutaYVerdura with an existing ID
        frutaYVerdura.setId(1L);
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);

        int databaseSizeBeforeCreate = frutaYVerduraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFrutaYVerduraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreAlimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = frutaYVerduraRepository.findAll().size();
        // set the field null
        frutaYVerdura.setNombreAlimento(null);

        // Create the FrutaYVerdura, which fails.
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);

        restFrutaYVerduraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isBadRequest());

        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFrutaYVerduras() throws Exception {
        // Initialize the database
        frutaYVerduraRepository.saveAndFlush(frutaYVerdura);

        // Get all the frutaYVerduraList
        restFrutaYVerduraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frutaYVerdura.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreAlimento").value(hasItem(DEFAULT_NOMBRE_ALIMENTO)));
    }

    @Test
    @Transactional
    void getFrutaYVerdura() throws Exception {
        // Initialize the database
        frutaYVerduraRepository.saveAndFlush(frutaYVerdura);

        // Get the frutaYVerdura
        restFrutaYVerduraMockMvc
            .perform(get(ENTITY_API_URL_ID, frutaYVerdura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(frutaYVerdura.getId().intValue()))
            .andExpect(jsonPath("$.nombreAlimento").value(DEFAULT_NOMBRE_ALIMENTO));
    }

    @Test
    @Transactional
    void getNonExistingFrutaYVerdura() throws Exception {
        // Get the frutaYVerdura
        restFrutaYVerduraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFrutaYVerdura() throws Exception {
        // Initialize the database
        frutaYVerduraRepository.saveAndFlush(frutaYVerdura);

        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();

        // Update the frutaYVerdura
        FrutaYVerdura updatedFrutaYVerdura = frutaYVerduraRepository.findById(frutaYVerdura.getId()).get();
        // Disconnect from session so that the updates on updatedFrutaYVerdura are not directly saved in db
        em.detach(updatedFrutaYVerdura);
        updatedFrutaYVerdura.nombreAlimento(UPDATED_NOMBRE_ALIMENTO);
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(updatedFrutaYVerdura);

        restFrutaYVerduraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frutaYVerduraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isOk());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
        FrutaYVerdura testFrutaYVerdura = frutaYVerduraList.get(frutaYVerduraList.size() - 1);
        assertThat(testFrutaYVerdura.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
    }

    @Test
    @Transactional
    void putNonExistingFrutaYVerdura() throws Exception {
        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();
        frutaYVerdura.setId(count.incrementAndGet());

        // Create the FrutaYVerdura
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrutaYVerduraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frutaYVerduraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFrutaYVerdura() throws Exception {
        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();
        frutaYVerdura.setId(count.incrementAndGet());

        // Create the FrutaYVerdura
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrutaYVerduraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFrutaYVerdura() throws Exception {
        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();
        frutaYVerdura.setId(count.incrementAndGet());

        // Create the FrutaYVerdura
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrutaYVerduraMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFrutaYVerduraWithPatch() throws Exception {
        // Initialize the database
        frutaYVerduraRepository.saveAndFlush(frutaYVerdura);

        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();

        // Update the frutaYVerdura using partial update
        FrutaYVerdura partialUpdatedFrutaYVerdura = new FrutaYVerdura();
        partialUpdatedFrutaYVerdura.setId(frutaYVerdura.getId());

        partialUpdatedFrutaYVerdura.nombreAlimento(UPDATED_NOMBRE_ALIMENTO);

        restFrutaYVerduraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrutaYVerdura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrutaYVerdura))
            )
            .andExpect(status().isOk());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
        FrutaYVerdura testFrutaYVerdura = frutaYVerduraList.get(frutaYVerduraList.size() - 1);
        assertThat(testFrutaYVerdura.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
    }

    @Test
    @Transactional
    void fullUpdateFrutaYVerduraWithPatch() throws Exception {
        // Initialize the database
        frutaYVerduraRepository.saveAndFlush(frutaYVerdura);

        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();

        // Update the frutaYVerdura using partial update
        FrutaYVerdura partialUpdatedFrutaYVerdura = new FrutaYVerdura();
        partialUpdatedFrutaYVerdura.setId(frutaYVerdura.getId());

        partialUpdatedFrutaYVerdura.nombreAlimento(UPDATED_NOMBRE_ALIMENTO);

        restFrutaYVerduraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrutaYVerdura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrutaYVerdura))
            )
            .andExpect(status().isOk());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
        FrutaYVerdura testFrutaYVerdura = frutaYVerduraList.get(frutaYVerduraList.size() - 1);
        assertThat(testFrutaYVerdura.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingFrutaYVerdura() throws Exception {
        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();
        frutaYVerdura.setId(count.incrementAndGet());

        // Create the FrutaYVerdura
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrutaYVerduraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, frutaYVerduraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFrutaYVerdura() throws Exception {
        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();
        frutaYVerdura.setId(count.incrementAndGet());

        // Create the FrutaYVerdura
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrutaYVerduraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFrutaYVerdura() throws Exception {
        int databaseSizeBeforeUpdate = frutaYVerduraRepository.findAll().size();
        frutaYVerdura.setId(count.incrementAndGet());

        // Create the FrutaYVerdura
        FrutaYVerduraDTO frutaYVerduraDTO = frutaYVerduraMapper.toDto(frutaYVerdura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrutaYVerduraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frutaYVerduraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FrutaYVerdura in the database
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFrutaYVerdura() throws Exception {
        // Initialize the database
        frutaYVerduraRepository.saveAndFlush(frutaYVerdura);

        int databaseSizeBeforeDelete = frutaYVerduraRepository.findAll().size();

        // Delete the frutaYVerdura
        restFrutaYVerduraMockMvc
            .perform(delete(ENTITY_API_URL_ID, frutaYVerdura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FrutaYVerdura> frutaYVerduraList = frutaYVerduraRepository.findAll();
        assertThat(frutaYVerduraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
