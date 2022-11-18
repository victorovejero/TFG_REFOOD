package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.TipoDeAlimento;
import com.refood.trazabilidad.repository.TipoDeAlimentoRepository;
import com.refood.trazabilidad.service.TipoDeAlimentoService;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import com.refood.trazabilidad.service.mapper.TipoDeAlimentoMapper;
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
 * Integration tests for the {@link TipoDeAlimentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TipoDeAlimentoResourceIT {

    private static final String DEFAULT_NOMBRE_ALIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ALIMENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-de-alimentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoDeAlimentoRepository tipoDeAlimentoRepository;

    @Mock
    private TipoDeAlimentoRepository tipoDeAlimentoRepositoryMock;

    @Autowired
    private TipoDeAlimentoMapper tipoDeAlimentoMapper;

    @Mock
    private TipoDeAlimentoService tipoDeAlimentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDeAlimentoMockMvc;

    private TipoDeAlimento tipoDeAlimento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDeAlimento createEntity(EntityManager em) {
        TipoDeAlimento tipoDeAlimento = new TipoDeAlimento().nombreAlimento(DEFAULT_NOMBRE_ALIMENTO);
        return tipoDeAlimento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDeAlimento createUpdatedEntity(EntityManager em) {
        TipoDeAlimento tipoDeAlimento = new TipoDeAlimento().nombreAlimento(UPDATED_NOMBRE_ALIMENTO);
        return tipoDeAlimento;
    }

    @BeforeEach
    public void initTest() {
        tipoDeAlimento = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoDeAlimento() throws Exception {
        int databaseSizeBeforeCreate = tipoDeAlimentoRepository.findAll().size();
        // Create the TipoDeAlimento
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);
        restTipoDeAlimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeAlimento testTipoDeAlimento = tipoDeAlimentoList.get(tipoDeAlimentoList.size() - 1);
        assertThat(testTipoDeAlimento.getNombreAlimento()).isEqualTo(DEFAULT_NOMBRE_ALIMENTO);
    }

    @Test
    @Transactional
    void createTipoDeAlimentoWithExistingId() throws Exception {
        // Create the TipoDeAlimento with an existing ID
        tipoDeAlimento.setId(1L);
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);

        int databaseSizeBeforeCreate = tipoDeAlimentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDeAlimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreAlimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeAlimentoRepository.findAll().size();
        // set the field null
        tipoDeAlimento.setNombreAlimento(null);

        // Create the TipoDeAlimento, which fails.
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);

        restTipoDeAlimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoDeAlimentos() throws Exception {
        // Initialize the database
        tipoDeAlimentoRepository.saveAndFlush(tipoDeAlimento);

        // Get all the tipoDeAlimentoList
        restTipoDeAlimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeAlimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreAlimento").value(hasItem(DEFAULT_NOMBRE_ALIMENTO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTipoDeAlimentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(tipoDeAlimentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTipoDeAlimentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tipoDeAlimentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTipoDeAlimentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tipoDeAlimentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTipoDeAlimentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tipoDeAlimentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTipoDeAlimento() throws Exception {
        // Initialize the database
        tipoDeAlimentoRepository.saveAndFlush(tipoDeAlimento);

        // Get the tipoDeAlimento
        restTipoDeAlimentoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoDeAlimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeAlimento.getId().intValue()))
            .andExpect(jsonPath("$.nombreAlimento").value(DEFAULT_NOMBRE_ALIMENTO));
    }

    @Test
    @Transactional
    void getNonExistingTipoDeAlimento() throws Exception {
        // Get the tipoDeAlimento
        restTipoDeAlimentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoDeAlimento() throws Exception {
        // Initialize the database
        tipoDeAlimentoRepository.saveAndFlush(tipoDeAlimento);

        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();

        // Update the tipoDeAlimento
        TipoDeAlimento updatedTipoDeAlimento = tipoDeAlimentoRepository.findById(tipoDeAlimento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeAlimento are not directly saved in db
        em.detach(updatedTipoDeAlimento);
        updatedTipoDeAlimento.nombreAlimento(UPDATED_NOMBRE_ALIMENTO);
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(updatedTipoDeAlimento);

        restTipoDeAlimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoDeAlimentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoDeAlimento testTipoDeAlimento = tipoDeAlimentoList.get(tipoDeAlimentoList.size() - 1);
        assertThat(testTipoDeAlimento.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
    }

    @Test
    @Transactional
    void putNonExistingTipoDeAlimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();
        tipoDeAlimento.setId(count.incrementAndGet());

        // Create the TipoDeAlimento
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeAlimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoDeAlimentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoDeAlimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();
        tipoDeAlimento.setId(count.incrementAndGet());

        // Create the TipoDeAlimento
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDeAlimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoDeAlimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();
        tipoDeAlimento.setId(count.incrementAndGet());

        // Create the TipoDeAlimento
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDeAlimentoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoDeAlimentoWithPatch() throws Exception {
        // Initialize the database
        tipoDeAlimentoRepository.saveAndFlush(tipoDeAlimento);

        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();

        // Update the tipoDeAlimento using partial update
        TipoDeAlimento partialUpdatedTipoDeAlimento = new TipoDeAlimento();
        partialUpdatedTipoDeAlimento.setId(tipoDeAlimento.getId());

        partialUpdatedTipoDeAlimento.nombreAlimento(UPDATED_NOMBRE_ALIMENTO);

        restTipoDeAlimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoDeAlimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoDeAlimento))
            )
            .andExpect(status().isOk());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoDeAlimento testTipoDeAlimento = tipoDeAlimentoList.get(tipoDeAlimentoList.size() - 1);
        assertThat(testTipoDeAlimento.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
    }

    @Test
    @Transactional
    void fullUpdateTipoDeAlimentoWithPatch() throws Exception {
        // Initialize the database
        tipoDeAlimentoRepository.saveAndFlush(tipoDeAlimento);

        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();

        // Update the tipoDeAlimento using partial update
        TipoDeAlimento partialUpdatedTipoDeAlimento = new TipoDeAlimento();
        partialUpdatedTipoDeAlimento.setId(tipoDeAlimento.getId());

        partialUpdatedTipoDeAlimento.nombreAlimento(UPDATED_NOMBRE_ALIMENTO);

        restTipoDeAlimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoDeAlimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoDeAlimento))
            )
            .andExpect(status().isOk());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoDeAlimento testTipoDeAlimento = tipoDeAlimentoList.get(tipoDeAlimentoList.size() - 1);
        assertThat(testTipoDeAlimento.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoDeAlimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();
        tipoDeAlimento.setId(count.incrementAndGet());

        // Create the TipoDeAlimento
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeAlimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoDeAlimentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoDeAlimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();
        tipoDeAlimento.setId(count.incrementAndGet());

        // Create the TipoDeAlimento
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDeAlimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoDeAlimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeAlimentoRepository.findAll().size();
        tipoDeAlimento.setId(count.incrementAndGet());

        // Create the TipoDeAlimento
        TipoDeAlimentoDTO tipoDeAlimentoDTO = tipoDeAlimentoMapper.toDto(tipoDeAlimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDeAlimentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoDeAlimentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoDeAlimento in the database
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoDeAlimento() throws Exception {
        // Initialize the database
        tipoDeAlimentoRepository.saveAndFlush(tipoDeAlimento);

        int databaseSizeBeforeDelete = tipoDeAlimentoRepository.findAll().size();

        // Delete the tipoDeAlimento
        restTipoDeAlimentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoDeAlimento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDeAlimento> tipoDeAlimentoList = tipoDeAlimentoRepository.findAll();
        assertThat(tipoDeAlimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
