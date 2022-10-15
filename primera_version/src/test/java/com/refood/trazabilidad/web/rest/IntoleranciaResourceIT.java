package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Intolerancia;
import com.refood.trazabilidad.repository.IntoleranciaRepository;
import com.refood.trazabilidad.service.IntoleranciaService;
import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
import com.refood.trazabilidad.service.mapper.IntoleranciaMapper;
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
 * Integration tests for the {@link IntoleranciaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class IntoleranciaResourceIT {

    private static final String DEFAULT_NOMBRE_INTOLERANCIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_INTOLERANCIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/intolerancias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntoleranciaRepository intoleranciaRepository;

    @Mock
    private IntoleranciaRepository intoleranciaRepositoryMock;

    @Autowired
    private IntoleranciaMapper intoleranciaMapper;

    @Mock
    private IntoleranciaService intoleranciaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntoleranciaMockMvc;

    private Intolerancia intolerancia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intolerancia createEntity(EntityManager em) {
        Intolerancia intolerancia = new Intolerancia().nombreIntolerancia(DEFAULT_NOMBRE_INTOLERANCIA);
        return intolerancia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intolerancia createUpdatedEntity(EntityManager em) {
        Intolerancia intolerancia = new Intolerancia().nombreIntolerancia(UPDATED_NOMBRE_INTOLERANCIA);
        return intolerancia;
    }

    @BeforeEach
    public void initTest() {
        intolerancia = createEntity(em);
    }

    @Test
    @Transactional
    void createIntolerancia() throws Exception {
        int databaseSizeBeforeCreate = intoleranciaRepository.findAll().size();
        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);
        restIntoleranciaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeCreate + 1);
        Intolerancia testIntolerancia = intoleranciaList.get(intoleranciaList.size() - 1);
        assertThat(testIntolerancia.getNombreIntolerancia()).isEqualTo(DEFAULT_NOMBRE_INTOLERANCIA);
    }

    @Test
    @Transactional
    void createIntoleranciaWithExistingId() throws Exception {
        // Create the Intolerancia with an existing ID
        intolerancia.setId(1L);
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        int databaseSizeBeforeCreate = intoleranciaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntoleranciaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntolerancias() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        // Get all the intoleranciaList
        restIntoleranciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intolerancia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreIntolerancia").value(hasItem(DEFAULT_NOMBRE_INTOLERANCIA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIntoleranciasWithEagerRelationshipsIsEnabled() throws Exception {
        when(intoleranciaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIntoleranciaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(intoleranciaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIntoleranciasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(intoleranciaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIntoleranciaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(intoleranciaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getIntolerancia() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        // Get the intolerancia
        restIntoleranciaMockMvc
            .perform(get(ENTITY_API_URL_ID, intolerancia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intolerancia.getId().intValue()))
            .andExpect(jsonPath("$.nombreIntolerancia").value(DEFAULT_NOMBRE_INTOLERANCIA));
    }

    @Test
    @Transactional
    void getNonExistingIntolerancia() throws Exception {
        // Get the intolerancia
        restIntoleranciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntolerancia() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();

        // Update the intolerancia
        Intolerancia updatedIntolerancia = intoleranciaRepository.findById(intolerancia.getId()).get();
        // Disconnect from session so that the updates on updatedIntolerancia are not directly saved in db
        em.detach(updatedIntolerancia);
        updatedIntolerancia.nombreIntolerancia(UPDATED_NOMBRE_INTOLERANCIA);
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(updatedIntolerancia);

        restIntoleranciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intoleranciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
        Intolerancia testIntolerancia = intoleranciaList.get(intoleranciaList.size() - 1);
        assertThat(testIntolerancia.getNombreIntolerancia()).isEqualTo(UPDATED_NOMBRE_INTOLERANCIA);
    }

    @Test
    @Transactional
    void putNonExistingIntolerancia() throws Exception {
        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();
        intolerancia.setId(count.incrementAndGet());

        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntoleranciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intoleranciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntolerancia() throws Exception {
        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();
        intolerancia.setId(count.incrementAndGet());

        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntoleranciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntolerancia() throws Exception {
        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();
        intolerancia.setId(count.incrementAndGet());

        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntoleranciaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntoleranciaWithPatch() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();

        // Update the intolerancia using partial update
        Intolerancia partialUpdatedIntolerancia = new Intolerancia();
        partialUpdatedIntolerancia.setId(intolerancia.getId());

        restIntoleranciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntolerancia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntolerancia))
            )
            .andExpect(status().isOk());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
        Intolerancia testIntolerancia = intoleranciaList.get(intoleranciaList.size() - 1);
        assertThat(testIntolerancia.getNombreIntolerancia()).isEqualTo(DEFAULT_NOMBRE_INTOLERANCIA);
    }

    @Test
    @Transactional
    void fullUpdateIntoleranciaWithPatch() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();

        // Update the intolerancia using partial update
        Intolerancia partialUpdatedIntolerancia = new Intolerancia();
        partialUpdatedIntolerancia.setId(intolerancia.getId());

        partialUpdatedIntolerancia.nombreIntolerancia(UPDATED_NOMBRE_INTOLERANCIA);

        restIntoleranciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntolerancia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntolerancia))
            )
            .andExpect(status().isOk());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
        Intolerancia testIntolerancia = intoleranciaList.get(intoleranciaList.size() - 1);
        assertThat(testIntolerancia.getNombreIntolerancia()).isEqualTo(UPDATED_NOMBRE_INTOLERANCIA);
    }

    @Test
    @Transactional
    void patchNonExistingIntolerancia() throws Exception {
        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();
        intolerancia.setId(count.incrementAndGet());

        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntoleranciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intoleranciaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntolerancia() throws Exception {
        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();
        intolerancia.setId(count.incrementAndGet());

        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntoleranciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntolerancia() throws Exception {
        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();
        intolerancia.setId(count.incrementAndGet());

        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntoleranciaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntolerancia() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        int databaseSizeBeforeDelete = intoleranciaRepository.findAll().size();

        // Delete the intolerancia
        restIntoleranciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, intolerancia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
