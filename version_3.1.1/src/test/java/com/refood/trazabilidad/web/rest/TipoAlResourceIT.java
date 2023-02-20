package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.TipoAl;
import com.refood.trazabilidad.repository.TipoAlRepository;
import com.refood.trazabilidad.service.TipoAlService;
import com.refood.trazabilidad.service.dto.TipoAlDTO;
import com.refood.trazabilidad.service.mapper.TipoAlMapper;
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
 * Integration tests for the {@link TipoAlResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TipoAlResourceIT {

    private static final String DEFAULT_NOMBRE_ALIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ALIMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FRUTA_Y_VERDURA = false;
    private static final Boolean UPDATED_FRUTA_Y_VERDURA = true;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-als";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoAlRepository tipoAlRepository;

    @Mock
    private TipoAlRepository tipoAlRepositoryMock;

    @Autowired
    private TipoAlMapper tipoAlMapper;

    @Mock
    private TipoAlService tipoAlServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoAlMockMvc;

    private TipoAl tipoAl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoAl createEntity(EntityManager em) {
        TipoAl tipoAl = new TipoAl()
            .nombreAlimento(DEFAULT_NOMBRE_ALIMENTO)
            .frutaYVerdura(DEFAULT_FRUTA_Y_VERDURA)
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoAl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoAl createUpdatedEntity(EntityManager em) {
        TipoAl tipoAl = new TipoAl()
            .nombreAlimento(UPDATED_NOMBRE_ALIMENTO)
            .frutaYVerdura(UPDATED_FRUTA_Y_VERDURA)
            .descripcion(UPDATED_DESCRIPCION);
        return tipoAl;
    }

    @BeforeEach
    public void initTest() {
        tipoAl = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoAl() throws Exception {
        int databaseSizeBeforeCreate = tipoAlRepository.findAll().size();
        // Create the TipoAl
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);
        restTipoAlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoAlDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeCreate + 1);
        TipoAl testTipoAl = tipoAlList.get(tipoAlList.size() - 1);
        assertThat(testTipoAl.getNombreAlimento()).isEqualTo(DEFAULT_NOMBRE_ALIMENTO);
        assertThat(testTipoAl.getFrutaYVerdura()).isEqualTo(DEFAULT_FRUTA_Y_VERDURA);
        assertThat(testTipoAl.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createTipoAlWithExistingId() throws Exception {
        // Create the TipoAl with an existing ID
        tipoAl.setId(1L);
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        int databaseSizeBeforeCreate = tipoAlRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoAlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoAlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreAlimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAlRepository.findAll().size();
        // set the field null
        tipoAl.setNombreAlimento(null);

        // Create the TipoAl, which fails.
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        restTipoAlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoAlDTO)))
            .andExpect(status().isBadRequest());

        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFrutaYVerduraIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAlRepository.findAll().size();
        // set the field null
        tipoAl.setFrutaYVerdura(null);

        // Create the TipoAl, which fails.
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        restTipoAlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoAlDTO)))
            .andExpect(status().isBadRequest());

        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoAls() throws Exception {
        // Initialize the database
        tipoAlRepository.saveAndFlush(tipoAl);

        // Get all the tipoAlList
        restTipoAlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAl.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreAlimento").value(hasItem(DEFAULT_NOMBRE_ALIMENTO)))
            .andExpect(jsonPath("$.[*].frutaYVerdura").value(hasItem(DEFAULT_FRUTA_Y_VERDURA.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTipoAlsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tipoAlServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTipoAlMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tipoAlServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTipoAlsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tipoAlServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTipoAlMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tipoAlRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTipoAl() throws Exception {
        // Initialize the database
        tipoAlRepository.saveAndFlush(tipoAl);

        // Get the tipoAl
        restTipoAlMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoAl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoAl.getId().intValue()))
            .andExpect(jsonPath("$.nombreAlimento").value(DEFAULT_NOMBRE_ALIMENTO))
            .andExpect(jsonPath("$.frutaYVerdura").value(DEFAULT_FRUTA_Y_VERDURA.booleanValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingTipoAl() throws Exception {
        // Get the tipoAl
        restTipoAlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoAl() throws Exception {
        // Initialize the database
        tipoAlRepository.saveAndFlush(tipoAl);

        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();

        // Update the tipoAl
        TipoAl updatedTipoAl = tipoAlRepository.findById(tipoAl.getId()).get();
        // Disconnect from session so that the updates on updatedTipoAl are not directly saved in db
        em.detach(updatedTipoAl);
        updatedTipoAl.nombreAlimento(UPDATED_NOMBRE_ALIMENTO).frutaYVerdura(UPDATED_FRUTA_Y_VERDURA).descripcion(UPDATED_DESCRIPCION);
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(updatedTipoAl);

        restTipoAlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoAlDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoAlDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
        TipoAl testTipoAl = tipoAlList.get(tipoAlList.size() - 1);
        assertThat(testTipoAl.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
        assertThat(testTipoAl.getFrutaYVerdura()).isEqualTo(UPDATED_FRUTA_Y_VERDURA);
        assertThat(testTipoAl.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingTipoAl() throws Exception {
        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();
        tipoAl.setId(count.incrementAndGet());

        // Create the TipoAl
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoAlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoAlDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoAlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoAl() throws Exception {
        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();
        tipoAl.setId(count.incrementAndGet());

        // Create the TipoAl
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoAlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoAlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoAl() throws Exception {
        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();
        tipoAl.setId(count.incrementAndGet());

        // Create the TipoAl
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoAlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoAlDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoAlWithPatch() throws Exception {
        // Initialize the database
        tipoAlRepository.saveAndFlush(tipoAl);

        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();

        // Update the tipoAl using partial update
        TipoAl partialUpdatedTipoAl = new TipoAl();
        partialUpdatedTipoAl.setId(tipoAl.getId());

        partialUpdatedTipoAl.nombreAlimento(UPDATED_NOMBRE_ALIMENTO).frutaYVerdura(UPDATED_FRUTA_Y_VERDURA);

        restTipoAlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoAl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoAl))
            )
            .andExpect(status().isOk());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
        TipoAl testTipoAl = tipoAlList.get(tipoAlList.size() - 1);
        assertThat(testTipoAl.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
        assertThat(testTipoAl.getFrutaYVerdura()).isEqualTo(UPDATED_FRUTA_Y_VERDURA);
        assertThat(testTipoAl.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateTipoAlWithPatch() throws Exception {
        // Initialize the database
        tipoAlRepository.saveAndFlush(tipoAl);

        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();

        // Update the tipoAl using partial update
        TipoAl partialUpdatedTipoAl = new TipoAl();
        partialUpdatedTipoAl.setId(tipoAl.getId());

        partialUpdatedTipoAl
            .nombreAlimento(UPDATED_NOMBRE_ALIMENTO)
            .frutaYVerdura(UPDATED_FRUTA_Y_VERDURA)
            .descripcion(UPDATED_DESCRIPCION);

        restTipoAlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoAl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoAl))
            )
            .andExpect(status().isOk());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
        TipoAl testTipoAl = tipoAlList.get(tipoAlList.size() - 1);
        assertThat(testTipoAl.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
        assertThat(testTipoAl.getFrutaYVerdura()).isEqualTo(UPDATED_FRUTA_Y_VERDURA);
        assertThat(testTipoAl.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingTipoAl() throws Exception {
        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();
        tipoAl.setId(count.incrementAndGet());

        // Create the TipoAl
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoAlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoAlDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoAlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoAl() throws Exception {
        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();
        tipoAl.setId(count.incrementAndGet());

        // Create the TipoAl
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoAlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoAlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoAl() throws Exception {
        int databaseSizeBeforeUpdate = tipoAlRepository.findAll().size();
        tipoAl.setId(count.incrementAndGet());

        // Create the TipoAl
        TipoAlDTO tipoAlDTO = tipoAlMapper.toDto(tipoAl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoAlMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoAlDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoAl in the database
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoAl() throws Exception {
        // Initialize the database
        tipoAlRepository.saveAndFlush(tipoAl);

        int databaseSizeBeforeDelete = tipoAlRepository.findAll().size();

        // Delete the tipoAl
        restTipoAlMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoAl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoAl> tipoAlList = tipoAlRepository.findAll();
        assertThat(tipoAlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
