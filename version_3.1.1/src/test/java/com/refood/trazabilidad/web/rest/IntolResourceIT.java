package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Intol;
import com.refood.trazabilidad.repository.IntolRepository;
import com.refood.trazabilidad.service.dto.IntolDTO;
import com.refood.trazabilidad.service.mapper.IntolMapper;
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
 * Integration tests for the {@link IntolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntolResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/intols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntolRepository intolRepository;

    @Autowired
    private IntolMapper intolMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntolMockMvc;

    private Intol intol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intol createEntity(EntityManager em) {
        Intol intol = new Intol().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
        return intol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intol createUpdatedEntity(EntityManager em) {
        Intol intol = new Intol().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        return intol;
    }

    @BeforeEach
    public void initTest() {
        intol = createEntity(em);
    }

    @Test
    @Transactional
    void createIntol() throws Exception {
        int databaseSizeBeforeCreate = intolRepository.findAll().size();
        // Create the Intol
        IntolDTO intolDTO = intolMapper.toDto(intol);
        restIntolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intolDTO)))
            .andExpect(status().isCreated());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeCreate + 1);
        Intol testIntol = intolList.get(intolList.size() - 1);
        assertThat(testIntol.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testIntol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createIntolWithExistingId() throws Exception {
        // Create the Intol with an existing ID
        intol.setId(1L);
        IntolDTO intolDTO = intolMapper.toDto(intol);

        int databaseSizeBeforeCreate = intolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = intolRepository.findAll().size();
        // set the field null
        intol.setNombre(null);

        // Create the Intol, which fails.
        IntolDTO intolDTO = intolMapper.toDto(intol);

        restIntolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intolDTO)))
            .andExpect(status().isBadRequest());

        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIntols() throws Exception {
        // Initialize the database
        intolRepository.saveAndFlush(intol);

        // Get all the intolList
        restIntolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intol.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getIntol() throws Exception {
        // Initialize the database
        intolRepository.saveAndFlush(intol);

        // Get the intol
        restIntolMockMvc
            .perform(get(ENTITY_API_URL_ID, intol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intol.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingIntol() throws Exception {
        // Get the intol
        restIntolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntol() throws Exception {
        // Initialize the database
        intolRepository.saveAndFlush(intol);

        int databaseSizeBeforeUpdate = intolRepository.findAll().size();

        // Update the intol
        Intol updatedIntol = intolRepository.findById(intol.getId()).get();
        // Disconnect from session so that the updates on updatedIntol are not directly saved in db
        em.detach(updatedIntol);
        updatedIntol.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        IntolDTO intolDTO = intolMapper.toDto(updatedIntol);

        restIntolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intolDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intolDTO))
            )
            .andExpect(status().isOk());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
        Intol testIntol = intolList.get(intolList.size() - 1);
        assertThat(testIntol.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIntol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingIntol() throws Exception {
        int databaseSizeBeforeUpdate = intolRepository.findAll().size();
        intol.setId(count.incrementAndGet());

        // Create the Intol
        IntolDTO intolDTO = intolMapper.toDto(intol);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intolDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntol() throws Exception {
        int databaseSizeBeforeUpdate = intolRepository.findAll().size();
        intol.setId(count.incrementAndGet());

        // Create the Intol
        IntolDTO intolDTO = intolMapper.toDto(intol);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntol() throws Exception {
        int databaseSizeBeforeUpdate = intolRepository.findAll().size();
        intol.setId(count.incrementAndGet());

        // Create the Intol
        IntolDTO intolDTO = intolMapper.toDto(intol);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intolDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntolWithPatch() throws Exception {
        // Initialize the database
        intolRepository.saveAndFlush(intol);

        int databaseSizeBeforeUpdate = intolRepository.findAll().size();

        // Update the intol using partial update
        Intol partialUpdatedIntol = new Intol();
        partialUpdatedIntol.setId(intol.getId());

        partialUpdatedIntol.nombre(UPDATED_NOMBRE);

        restIntolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntol))
            )
            .andExpect(status().isOk());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
        Intol testIntol = intolList.get(intolList.size() - 1);
        assertThat(testIntol.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIntol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateIntolWithPatch() throws Exception {
        // Initialize the database
        intolRepository.saveAndFlush(intol);

        int databaseSizeBeforeUpdate = intolRepository.findAll().size();

        // Update the intol using partial update
        Intol partialUpdatedIntol = new Intol();
        partialUpdatedIntol.setId(intol.getId());

        partialUpdatedIntol.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restIntolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntol))
            )
            .andExpect(status().isOk());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
        Intol testIntol = intolList.get(intolList.size() - 1);
        assertThat(testIntol.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIntol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingIntol() throws Exception {
        int databaseSizeBeforeUpdate = intolRepository.findAll().size();
        intol.setId(count.incrementAndGet());

        // Create the Intol
        IntolDTO intolDTO = intolMapper.toDto(intol);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intolDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntol() throws Exception {
        int databaseSizeBeforeUpdate = intolRepository.findAll().size();
        intol.setId(count.incrementAndGet());

        // Create the Intol
        IntolDTO intolDTO = intolMapper.toDto(intol);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntol() throws Exception {
        int databaseSizeBeforeUpdate = intolRepository.findAll().size();
        intol.setId(count.incrementAndGet());

        // Create the Intol
        IntolDTO intolDTO = intolMapper.toDto(intol);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(intolDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Intol in the database
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntol() throws Exception {
        // Initialize the database
        intolRepository.saveAndFlush(intol);

        int databaseSizeBeforeDelete = intolRepository.findAll().size();

        // Delete the intol
        restIntolMockMvc
            .perform(delete(ENTITY_API_URL_ID, intol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Intol> intolList = intolRepository.findAll();
        assertThat(intolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
