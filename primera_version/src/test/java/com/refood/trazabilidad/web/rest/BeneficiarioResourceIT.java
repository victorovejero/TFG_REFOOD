package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Beneficiario;
import com.refood.trazabilidad.repository.BeneficiarioRepository;
import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
import com.refood.trazabilidad.service.mapper.BeneficiarioMapper;
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
 * Integration tests for the {@link BeneficiarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeneficiarioResourceIT {

    private static final String DEFAULT_NOMBRE_BENEFICIARIO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_BENEFICIARIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_PERSONAS = 1;
    private static final Integer UPDATED_NUM_PERSONAS = 2;

    private static final Integer DEFAULT_NUM_NINIOS = 1;
    private static final Integer UPDATED_NUM_NINIOS = 2;

    private static final String DEFAULT_ID_DUAL = "AAAAAAAAAA";
    private static final String UPDATED_ID_DUAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beneficiarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Autowired
    private BeneficiarioMapper beneficiarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeneficiarioMockMvc;

    private Beneficiario beneficiario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficiario createEntity(EntityManager em) {
        Beneficiario beneficiario = new Beneficiario()
            .nombreBeneficiario(DEFAULT_NOMBRE_BENEFICIARIO)
            .numPersonas(DEFAULT_NUM_PERSONAS)
            .numNinios(DEFAULT_NUM_NINIOS)
            .idDual(DEFAULT_ID_DUAL);
        return beneficiario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficiario createUpdatedEntity(EntityManager em) {
        Beneficiario beneficiario = new Beneficiario()
            .nombreBeneficiario(UPDATED_NOMBRE_BENEFICIARIO)
            .numPersonas(UPDATED_NUM_PERSONAS)
            .numNinios(UPDATED_NUM_NINIOS)
            .idDual(UPDATED_ID_DUAL);
        return beneficiario;
    }

    @BeforeEach
    public void initTest() {
        beneficiario = createEntity(em);
    }

    @Test
    @Transactional
    void createBeneficiario() throws Exception {
        int databaseSizeBeforeCreate = beneficiarioRepository.findAll().size();
        // Create the Beneficiario
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);
        restBeneficiarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeCreate + 1);
        Beneficiario testBeneficiario = beneficiarioList.get(beneficiarioList.size() - 1);
        assertThat(testBeneficiario.getNombreBeneficiario()).isEqualTo(DEFAULT_NOMBRE_BENEFICIARIO);
        assertThat(testBeneficiario.getNumPersonas()).isEqualTo(DEFAULT_NUM_PERSONAS);
        assertThat(testBeneficiario.getNumNinios()).isEqualTo(DEFAULT_NUM_NINIOS);
        assertThat(testBeneficiario.getIdDual()).isEqualTo(DEFAULT_ID_DUAL);
    }

    @Test
    @Transactional
    void createBeneficiarioWithExistingId() throws Exception {
        // Create the Beneficiario with an existing ID
        beneficiario.setId(1L);
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        int databaseSizeBeforeCreate = beneficiarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreBeneficiarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiarioRepository.findAll().size();
        // set the field null
        beneficiario.setNombreBeneficiario(null);

        // Create the Beneficiario, which fails.
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        restBeneficiarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumPersonasIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiarioRepository.findAll().size();
        // set the field null
        beneficiario.setNumPersonas(null);

        // Create the Beneficiario, which fails.
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        restBeneficiarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumNiniosIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiarioRepository.findAll().size();
        // set the field null
        beneficiario.setNumNinios(null);

        // Create the Beneficiario, which fails.
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        restBeneficiarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBeneficiarios() throws Exception {
        // Initialize the database
        beneficiarioRepository.saveAndFlush(beneficiario);

        // Get all the beneficiarioList
        restBeneficiarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreBeneficiario").value(hasItem(DEFAULT_NOMBRE_BENEFICIARIO)))
            .andExpect(jsonPath("$.[*].numPersonas").value(hasItem(DEFAULT_NUM_PERSONAS)))
            .andExpect(jsonPath("$.[*].numNinios").value(hasItem(DEFAULT_NUM_NINIOS)))
            .andExpect(jsonPath("$.[*].idDual").value(hasItem(DEFAULT_ID_DUAL)));
    }

    @Test
    @Transactional
    void getBeneficiario() throws Exception {
        // Initialize the database
        beneficiarioRepository.saveAndFlush(beneficiario);

        // Get the beneficiario
        restBeneficiarioMockMvc
            .perform(get(ENTITY_API_URL_ID, beneficiario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiario.getId().intValue()))
            .andExpect(jsonPath("$.nombreBeneficiario").value(DEFAULT_NOMBRE_BENEFICIARIO))
            .andExpect(jsonPath("$.numPersonas").value(DEFAULT_NUM_PERSONAS))
            .andExpect(jsonPath("$.numNinios").value(DEFAULT_NUM_NINIOS))
            .andExpect(jsonPath("$.idDual").value(DEFAULT_ID_DUAL));
    }

    @Test
    @Transactional
    void getNonExistingBeneficiario() throws Exception {
        // Get the beneficiario
        restBeneficiarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeneficiario() throws Exception {
        // Initialize the database
        beneficiarioRepository.saveAndFlush(beneficiario);

        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();

        // Update the beneficiario
        Beneficiario updatedBeneficiario = beneficiarioRepository.findById(beneficiario.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiario are not directly saved in db
        em.detach(updatedBeneficiario);
        updatedBeneficiario
            .nombreBeneficiario(UPDATED_NOMBRE_BENEFICIARIO)
            .numPersonas(UPDATED_NUM_PERSONAS)
            .numNinios(UPDATED_NUM_NINIOS)
            .idDual(UPDATED_ID_DUAL);
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(updatedBeneficiario);

        restBeneficiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beneficiarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
        Beneficiario testBeneficiario = beneficiarioList.get(beneficiarioList.size() - 1);
        assertThat(testBeneficiario.getNombreBeneficiario()).isEqualTo(UPDATED_NOMBRE_BENEFICIARIO);
        assertThat(testBeneficiario.getNumPersonas()).isEqualTo(UPDATED_NUM_PERSONAS);
        assertThat(testBeneficiario.getNumNinios()).isEqualTo(UPDATED_NUM_NINIOS);
        assertThat(testBeneficiario.getIdDual()).isEqualTo(UPDATED_ID_DUAL);
    }

    @Test
    @Transactional
    void putNonExistingBeneficiario() throws Exception {
        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();
        beneficiario.setId(count.incrementAndGet());

        // Create the Beneficiario
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beneficiarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeneficiario() throws Exception {
        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();
        beneficiario.setId(count.incrementAndGet());

        // Create the Beneficiario
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeneficiarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeneficiario() throws Exception {
        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();
        beneficiario.setId(count.incrementAndGet());

        // Create the Beneficiario
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeneficiarioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeneficiarioWithPatch() throws Exception {
        // Initialize the database
        beneficiarioRepository.saveAndFlush(beneficiario);

        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();

        // Update the beneficiario using partial update
        Beneficiario partialUpdatedBeneficiario = new Beneficiario();
        partialUpdatedBeneficiario.setId(beneficiario.getId());

        partialUpdatedBeneficiario.nombreBeneficiario(UPDATED_NOMBRE_BENEFICIARIO).idDual(UPDATED_ID_DUAL);

        restBeneficiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeneficiario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBeneficiario))
            )
            .andExpect(status().isOk());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
        Beneficiario testBeneficiario = beneficiarioList.get(beneficiarioList.size() - 1);
        assertThat(testBeneficiario.getNombreBeneficiario()).isEqualTo(UPDATED_NOMBRE_BENEFICIARIO);
        assertThat(testBeneficiario.getNumPersonas()).isEqualTo(DEFAULT_NUM_PERSONAS);
        assertThat(testBeneficiario.getNumNinios()).isEqualTo(DEFAULT_NUM_NINIOS);
        assertThat(testBeneficiario.getIdDual()).isEqualTo(UPDATED_ID_DUAL);
    }

    @Test
    @Transactional
    void fullUpdateBeneficiarioWithPatch() throws Exception {
        // Initialize the database
        beneficiarioRepository.saveAndFlush(beneficiario);

        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();

        // Update the beneficiario using partial update
        Beneficiario partialUpdatedBeneficiario = new Beneficiario();
        partialUpdatedBeneficiario.setId(beneficiario.getId());

        partialUpdatedBeneficiario
            .nombreBeneficiario(UPDATED_NOMBRE_BENEFICIARIO)
            .numPersonas(UPDATED_NUM_PERSONAS)
            .numNinios(UPDATED_NUM_NINIOS)
            .idDual(UPDATED_ID_DUAL);

        restBeneficiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeneficiario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBeneficiario))
            )
            .andExpect(status().isOk());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
        Beneficiario testBeneficiario = beneficiarioList.get(beneficiarioList.size() - 1);
        assertThat(testBeneficiario.getNombreBeneficiario()).isEqualTo(UPDATED_NOMBRE_BENEFICIARIO);
        assertThat(testBeneficiario.getNumPersonas()).isEqualTo(UPDATED_NUM_PERSONAS);
        assertThat(testBeneficiario.getNumNinios()).isEqualTo(UPDATED_NUM_NINIOS);
        assertThat(testBeneficiario.getIdDual()).isEqualTo(UPDATED_ID_DUAL);
    }

    @Test
    @Transactional
    void patchNonExistingBeneficiario() throws Exception {
        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();
        beneficiario.setId(count.incrementAndGet());

        // Create the Beneficiario
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beneficiarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeneficiario() throws Exception {
        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();
        beneficiario.setId(count.incrementAndGet());

        // Create the Beneficiario
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeneficiarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeneficiario() throws Exception {
        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();
        beneficiario.setId(count.incrementAndGet());

        // Create the Beneficiario
        BeneficiarioDTO beneficiarioDTO = beneficiarioMapper.toDto(beneficiario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeneficiarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(beneficiarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeneficiario() throws Exception {
        // Initialize the database
        beneficiarioRepository.saveAndFlush(beneficiario);

        int databaseSizeBeforeDelete = beneficiarioRepository.findAll().size();

        // Delete the beneficiario
        restBeneficiarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, beneficiario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beneficiario> beneficiarioList = beneficiarioRepository.findAll();
        assertThat(beneficiarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
