package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Tupper;
import com.refood.trazabilidad.repository.TupperRepository;
import com.refood.trazabilidad.service.dto.TupperDTO;
import com.refood.trazabilidad.service.mapper.TupperMapper;
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
 * Integration tests for the {@link TupperResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TupperResourceIT {

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final String DEFAULT_PRODUCTOR = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTOR = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tuppers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TupperRepository tupperRepository;

    @Autowired
    private TupperMapper tupperMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTupperMockMvc;

    private Tupper tupper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tupper createEntity(EntityManager em) {
        Tupper tupper = new Tupper()
            .peso(DEFAULT_PESO)
            .productor(DEFAULT_PRODUCTOR)
            .modelo(DEFAULT_MODELO)
            .precio(DEFAULT_PRECIO)
            .descripcion(DEFAULT_DESCRIPCION);
        return tupper;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tupper createUpdatedEntity(EntityManager em) {
        Tupper tupper = new Tupper()
            .peso(UPDATED_PESO)
            .productor(UPDATED_PRODUCTOR)
            .modelo(UPDATED_MODELO)
            .precio(UPDATED_PRECIO)
            .descripcion(UPDATED_DESCRIPCION);
        return tupper;
    }

    @BeforeEach
    public void initTest() {
        tupper = createEntity(em);
    }

    @Test
    @Transactional
    void createTupper() throws Exception {
        int databaseSizeBeforeCreate = tupperRepository.findAll().size();
        // Create the Tupper
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);
        restTupperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tupperDTO)))
            .andExpect(status().isCreated());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeCreate + 1);
        Tupper testTupper = tupperList.get(tupperList.size() - 1);
        assertThat(testTupper.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testTupper.getProductor()).isEqualTo(DEFAULT_PRODUCTOR);
        assertThat(testTupper.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testTupper.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testTupper.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createTupperWithExistingId() throws Exception {
        // Create the Tupper with an existing ID
        tupper.setId(1L);
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        int databaseSizeBeforeCreate = tupperRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTupperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tupperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tupperRepository.findAll().size();
        // set the field null
        tupper.setPeso(null);

        // Create the Tupper, which fails.
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        restTupperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tupperDTO)))
            .andExpect(status().isBadRequest());

        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProductorIsRequired() throws Exception {
        int databaseSizeBeforeTest = tupperRepository.findAll().size();
        // set the field null
        tupper.setProductor(null);

        // Create the Tupper, which fails.
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        restTupperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tupperDTO)))
            .andExpect(status().isBadRequest());

        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = tupperRepository.findAll().size();
        // set the field null
        tupper.setModelo(null);

        // Create the Tupper, which fails.
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        restTupperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tupperDTO)))
            .andExpect(status().isBadRequest());

        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tupperRepository.findAll().size();
        // set the field null
        tupper.setPrecio(null);

        // Create the Tupper, which fails.
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        restTupperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tupperDTO)))
            .andExpect(status().isBadRequest());

        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTuppers() throws Exception {
        // Initialize the database
        tupperRepository.saveAndFlush(tupper);

        // Get all the tupperList
        restTupperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tupper.getId().intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].productor").value(hasItem(DEFAULT_PRODUCTOR)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getTupper() throws Exception {
        // Initialize the database
        tupperRepository.saveAndFlush(tupper);

        // Get the tupper
        restTupperMockMvc
            .perform(get(ENTITY_API_URL_ID, tupper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tupper.getId().intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.productor").value(DEFAULT_PRODUCTOR))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingTupper() throws Exception {
        // Get the tupper
        restTupperMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTupper() throws Exception {
        // Initialize the database
        tupperRepository.saveAndFlush(tupper);

        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();

        // Update the tupper
        Tupper updatedTupper = tupperRepository.findById(tupper.getId()).get();
        // Disconnect from session so that the updates on updatedTupper are not directly saved in db
        em.detach(updatedTupper);
        updatedTupper
            .peso(UPDATED_PESO)
            .productor(UPDATED_PRODUCTOR)
            .modelo(UPDATED_MODELO)
            .precio(UPDATED_PRECIO)
            .descripcion(UPDATED_DESCRIPCION);
        TupperDTO tupperDTO = tupperMapper.toDto(updatedTupper);

        restTupperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tupperDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tupperDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
        Tupper testTupper = tupperList.get(tupperList.size() - 1);
        assertThat(testTupper.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testTupper.getProductor()).isEqualTo(UPDATED_PRODUCTOR);
        assertThat(testTupper.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testTupper.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testTupper.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingTupper() throws Exception {
        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();
        tupper.setId(count.incrementAndGet());

        // Create the Tupper
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTupperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tupperDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tupperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTupper() throws Exception {
        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();
        tupper.setId(count.incrementAndGet());

        // Create the Tupper
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTupperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tupperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTupper() throws Exception {
        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();
        tupper.setId(count.incrementAndGet());

        // Create the Tupper
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTupperMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tupperDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTupperWithPatch() throws Exception {
        // Initialize the database
        tupperRepository.saveAndFlush(tupper);

        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();

        // Update the tupper using partial update
        Tupper partialUpdatedTupper = new Tupper();
        partialUpdatedTupper.setId(tupper.getId());

        partialUpdatedTupper.peso(UPDATED_PESO).modelo(UPDATED_MODELO);

        restTupperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTupper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTupper))
            )
            .andExpect(status().isOk());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
        Tupper testTupper = tupperList.get(tupperList.size() - 1);
        assertThat(testTupper.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testTupper.getProductor()).isEqualTo(DEFAULT_PRODUCTOR);
        assertThat(testTupper.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testTupper.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testTupper.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateTupperWithPatch() throws Exception {
        // Initialize the database
        tupperRepository.saveAndFlush(tupper);

        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();

        // Update the tupper using partial update
        Tupper partialUpdatedTupper = new Tupper();
        partialUpdatedTupper.setId(tupper.getId());

        partialUpdatedTupper
            .peso(UPDATED_PESO)
            .productor(UPDATED_PRODUCTOR)
            .modelo(UPDATED_MODELO)
            .precio(UPDATED_PRECIO)
            .descripcion(UPDATED_DESCRIPCION);

        restTupperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTupper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTupper))
            )
            .andExpect(status().isOk());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
        Tupper testTupper = tupperList.get(tupperList.size() - 1);
        assertThat(testTupper.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testTupper.getProductor()).isEqualTo(UPDATED_PRODUCTOR);
        assertThat(testTupper.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testTupper.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testTupper.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingTupper() throws Exception {
        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();
        tupper.setId(count.incrementAndGet());

        // Create the Tupper
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTupperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tupperDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tupperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTupper() throws Exception {
        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();
        tupper.setId(count.incrementAndGet());

        // Create the Tupper
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTupperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tupperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTupper() throws Exception {
        int databaseSizeBeforeUpdate = tupperRepository.findAll().size();
        tupper.setId(count.incrementAndGet());

        // Create the Tupper
        TupperDTO tupperDTO = tupperMapper.toDto(tupper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTupperMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tupperDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tupper in the database
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTupper() throws Exception {
        // Initialize the database
        tupperRepository.saveAndFlush(tupper);

        int databaseSizeBeforeDelete = tupperRepository.findAll().size();

        // Delete the tupper
        restTupperMockMvc
            .perform(delete(ENTITY_API_URL_ID, tupper.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tupper> tupperList = tupperRepository.findAll();
        assertThat(tupperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
