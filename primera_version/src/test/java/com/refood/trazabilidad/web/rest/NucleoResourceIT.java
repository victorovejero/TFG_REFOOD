package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.repository.NucleoRepository;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import com.refood.trazabilidad.service.mapper.NucleoMapper;
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
 * Integration tests for the {@link NucleoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NucleoResourceIT {

    private static final String DEFAULT_NOMBRE_NUCLEO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_NUCLEO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_NUCLEO = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_NUCLEO = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA_NUCLEO = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA_NUCLEO = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE_NUCLEO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE_NUCLEO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_NUCLEO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_NUCLEO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_NUCLEO = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_NUCLEO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_RUTAS = 1;
    private static final Integer UPDATED_NUMERO_RUTAS = 2;

    private static final String ENTITY_API_URL = "/api/nucleos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NucleoRepository nucleoRepository;

    @Autowired
    private NucleoMapper nucleoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNucleoMockMvc;

    private Nucleo nucleo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nucleo createEntity(EntityManager em) {
        Nucleo nucleo = new Nucleo()
            .nombreNucleo(DEFAULT_NOMBRE_NUCLEO)
            .direccionNucleo(DEFAULT_DIRECCION_NUCLEO)
            .provinciaNucleo(DEFAULT_PROVINCIA_NUCLEO)
            .responsableNucleo(DEFAULT_RESPONSABLE_NUCLEO)
            .telefonoNucleo(DEFAULT_TELEFONO_NUCLEO)
            .emailNucleo(DEFAULT_EMAIL_NUCLEO)
            .numeroRutas(DEFAULT_NUMERO_RUTAS);
        return nucleo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nucleo createUpdatedEntity(EntityManager em) {
        Nucleo nucleo = new Nucleo()
            .nombreNucleo(UPDATED_NOMBRE_NUCLEO)
            .direccionNucleo(UPDATED_DIRECCION_NUCLEO)
            .provinciaNucleo(UPDATED_PROVINCIA_NUCLEO)
            .responsableNucleo(UPDATED_RESPONSABLE_NUCLEO)
            .telefonoNucleo(UPDATED_TELEFONO_NUCLEO)
            .emailNucleo(UPDATED_EMAIL_NUCLEO)
            .numeroRutas(UPDATED_NUMERO_RUTAS);
        return nucleo;
    }

    @BeforeEach
    public void initTest() {
        nucleo = createEntity(em);
    }

    @Test
    @Transactional
    void createNucleo() throws Exception {
        int databaseSizeBeforeCreate = nucleoRepository.findAll().size();
        // Create the Nucleo
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);
        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isCreated());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeCreate + 1);
        Nucleo testNucleo = nucleoList.get(nucleoList.size() - 1);
        assertThat(testNucleo.getNombreNucleo()).isEqualTo(DEFAULT_NOMBRE_NUCLEO);
        assertThat(testNucleo.getDireccionNucleo()).isEqualTo(DEFAULT_DIRECCION_NUCLEO);
        assertThat(testNucleo.getProvinciaNucleo()).isEqualTo(DEFAULT_PROVINCIA_NUCLEO);
        assertThat(testNucleo.getResponsableNucleo()).isEqualTo(DEFAULT_RESPONSABLE_NUCLEO);
        assertThat(testNucleo.getTelefonoNucleo()).isEqualTo(DEFAULT_TELEFONO_NUCLEO);
        assertThat(testNucleo.getEmailNucleo()).isEqualTo(DEFAULT_EMAIL_NUCLEO);
        assertThat(testNucleo.getNumeroRutas()).isEqualTo(DEFAULT_NUMERO_RUTAS);
    }

    @Test
    @Transactional
    void createNucleoWithExistingId() throws Exception {
        // Create the Nucleo with an existing ID
        nucleo.setId(1L);
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        int databaseSizeBeforeCreate = nucleoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreNucleoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setNombreNucleo(null);

        // Create the Nucleo, which fails.
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isBadRequest());

        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionNucleoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setDireccionNucleo(null);

        // Create the Nucleo, which fails.
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isBadRequest());

        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvinciaNucleoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setProvinciaNucleo(null);

        // Create the Nucleo, which fails.
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isBadRequest());

        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResponsableNucleoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setResponsableNucleo(null);

        // Create the Nucleo, which fails.
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isBadRequest());

        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoNucleoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setTelefonoNucleo(null);

        // Create the Nucleo, which fails.
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isBadRequest());

        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailNucleoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setEmailNucleo(null);

        // Create the Nucleo, which fails.
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isBadRequest());

        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroRutasIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setNumeroRutas(null);

        // Create the Nucleo, which fails.
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        restNucleoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isBadRequest());

        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNucleos() throws Exception {
        // Initialize the database
        nucleoRepository.saveAndFlush(nucleo);

        // Get all the nucleoList
        restNucleoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nucleo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreNucleo").value(hasItem(DEFAULT_NOMBRE_NUCLEO)))
            .andExpect(jsonPath("$.[*].direccionNucleo").value(hasItem(DEFAULT_DIRECCION_NUCLEO)))
            .andExpect(jsonPath("$.[*].provinciaNucleo").value(hasItem(DEFAULT_PROVINCIA_NUCLEO)))
            .andExpect(jsonPath("$.[*].responsableNucleo").value(hasItem(DEFAULT_RESPONSABLE_NUCLEO)))
            .andExpect(jsonPath("$.[*].telefonoNucleo").value(hasItem(DEFAULT_TELEFONO_NUCLEO)))
            .andExpect(jsonPath("$.[*].emailNucleo").value(hasItem(DEFAULT_EMAIL_NUCLEO)))
            .andExpect(jsonPath("$.[*].numeroRutas").value(hasItem(DEFAULT_NUMERO_RUTAS)));
    }

    @Test
    @Transactional
    void getNucleo() throws Exception {
        // Initialize the database
        nucleoRepository.saveAndFlush(nucleo);

        // Get the nucleo
        restNucleoMockMvc
            .perform(get(ENTITY_API_URL_ID, nucleo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nucleo.getId().intValue()))
            .andExpect(jsonPath("$.nombreNucleo").value(DEFAULT_NOMBRE_NUCLEO))
            .andExpect(jsonPath("$.direccionNucleo").value(DEFAULT_DIRECCION_NUCLEO))
            .andExpect(jsonPath("$.provinciaNucleo").value(DEFAULT_PROVINCIA_NUCLEO))
            .andExpect(jsonPath("$.responsableNucleo").value(DEFAULT_RESPONSABLE_NUCLEO))
            .andExpect(jsonPath("$.telefonoNucleo").value(DEFAULT_TELEFONO_NUCLEO))
            .andExpect(jsonPath("$.emailNucleo").value(DEFAULT_EMAIL_NUCLEO))
            .andExpect(jsonPath("$.numeroRutas").value(DEFAULT_NUMERO_RUTAS));
    }

    @Test
    @Transactional
    void getNonExistingNucleo() throws Exception {
        // Get the nucleo
        restNucleoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNucleo() throws Exception {
        // Initialize the database
        nucleoRepository.saveAndFlush(nucleo);

        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();

        // Update the nucleo
        Nucleo updatedNucleo = nucleoRepository.findById(nucleo.getId()).get();
        // Disconnect from session so that the updates on updatedNucleo are not directly saved in db
        em.detach(updatedNucleo);
        updatedNucleo
            .nombreNucleo(UPDATED_NOMBRE_NUCLEO)
            .direccionNucleo(UPDATED_DIRECCION_NUCLEO)
            .provinciaNucleo(UPDATED_PROVINCIA_NUCLEO)
            .responsableNucleo(UPDATED_RESPONSABLE_NUCLEO)
            .telefonoNucleo(UPDATED_TELEFONO_NUCLEO)
            .emailNucleo(UPDATED_EMAIL_NUCLEO)
            .numeroRutas(UPDATED_NUMERO_RUTAS);
        NucleoDTO nucleoDTO = nucleoMapper.toDto(updatedNucleo);

        restNucleoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nucleoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nucleoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
        Nucleo testNucleo = nucleoList.get(nucleoList.size() - 1);
        assertThat(testNucleo.getNombreNucleo()).isEqualTo(UPDATED_NOMBRE_NUCLEO);
        assertThat(testNucleo.getDireccionNucleo()).isEqualTo(UPDATED_DIRECCION_NUCLEO);
        assertThat(testNucleo.getProvinciaNucleo()).isEqualTo(UPDATED_PROVINCIA_NUCLEO);
        assertThat(testNucleo.getResponsableNucleo()).isEqualTo(UPDATED_RESPONSABLE_NUCLEO);
        assertThat(testNucleo.getTelefonoNucleo()).isEqualTo(UPDATED_TELEFONO_NUCLEO);
        assertThat(testNucleo.getEmailNucleo()).isEqualTo(UPDATED_EMAIL_NUCLEO);
        assertThat(testNucleo.getNumeroRutas()).isEqualTo(UPDATED_NUMERO_RUTAS);
    }

    @Test
    @Transactional
    void putNonExistingNucleo() throws Exception {
        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();
        nucleo.setId(count.incrementAndGet());

        // Create the Nucleo
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNucleoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nucleoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nucleoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNucleo() throws Exception {
        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();
        nucleo.setId(count.incrementAndGet());

        // Create the Nucleo
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNucleoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nucleoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNucleo() throws Exception {
        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();
        nucleo.setId(count.incrementAndGet());

        // Create the Nucleo
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNucleoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nucleoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNucleoWithPatch() throws Exception {
        // Initialize the database
        nucleoRepository.saveAndFlush(nucleo);

        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();

        // Update the nucleo using partial update
        Nucleo partialUpdatedNucleo = new Nucleo();
        partialUpdatedNucleo.setId(nucleo.getId());

        partialUpdatedNucleo
            .nombreNucleo(UPDATED_NOMBRE_NUCLEO)
            .responsableNucleo(UPDATED_RESPONSABLE_NUCLEO)
            .emailNucleo(UPDATED_EMAIL_NUCLEO);

        restNucleoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNucleo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNucleo))
            )
            .andExpect(status().isOk());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
        Nucleo testNucleo = nucleoList.get(nucleoList.size() - 1);
        assertThat(testNucleo.getNombreNucleo()).isEqualTo(UPDATED_NOMBRE_NUCLEO);
        assertThat(testNucleo.getDireccionNucleo()).isEqualTo(DEFAULT_DIRECCION_NUCLEO);
        assertThat(testNucleo.getProvinciaNucleo()).isEqualTo(DEFAULT_PROVINCIA_NUCLEO);
        assertThat(testNucleo.getResponsableNucleo()).isEqualTo(UPDATED_RESPONSABLE_NUCLEO);
        assertThat(testNucleo.getTelefonoNucleo()).isEqualTo(DEFAULT_TELEFONO_NUCLEO);
        assertThat(testNucleo.getEmailNucleo()).isEqualTo(UPDATED_EMAIL_NUCLEO);
        assertThat(testNucleo.getNumeroRutas()).isEqualTo(DEFAULT_NUMERO_RUTAS);
    }

    @Test
    @Transactional
    void fullUpdateNucleoWithPatch() throws Exception {
        // Initialize the database
        nucleoRepository.saveAndFlush(nucleo);

        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();

        // Update the nucleo using partial update
        Nucleo partialUpdatedNucleo = new Nucleo();
        partialUpdatedNucleo.setId(nucleo.getId());

        partialUpdatedNucleo
            .nombreNucleo(UPDATED_NOMBRE_NUCLEO)
            .direccionNucleo(UPDATED_DIRECCION_NUCLEO)
            .provinciaNucleo(UPDATED_PROVINCIA_NUCLEO)
            .responsableNucleo(UPDATED_RESPONSABLE_NUCLEO)
            .telefonoNucleo(UPDATED_TELEFONO_NUCLEO)
            .emailNucleo(UPDATED_EMAIL_NUCLEO)
            .numeroRutas(UPDATED_NUMERO_RUTAS);

        restNucleoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNucleo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNucleo))
            )
            .andExpect(status().isOk());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
        Nucleo testNucleo = nucleoList.get(nucleoList.size() - 1);
        assertThat(testNucleo.getNombreNucleo()).isEqualTo(UPDATED_NOMBRE_NUCLEO);
        assertThat(testNucleo.getDireccionNucleo()).isEqualTo(UPDATED_DIRECCION_NUCLEO);
        assertThat(testNucleo.getProvinciaNucleo()).isEqualTo(UPDATED_PROVINCIA_NUCLEO);
        assertThat(testNucleo.getResponsableNucleo()).isEqualTo(UPDATED_RESPONSABLE_NUCLEO);
        assertThat(testNucleo.getTelefonoNucleo()).isEqualTo(UPDATED_TELEFONO_NUCLEO);
        assertThat(testNucleo.getEmailNucleo()).isEqualTo(UPDATED_EMAIL_NUCLEO);
        assertThat(testNucleo.getNumeroRutas()).isEqualTo(UPDATED_NUMERO_RUTAS);
    }

    @Test
    @Transactional
    void patchNonExistingNucleo() throws Exception {
        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();
        nucleo.setId(count.incrementAndGet());

        // Create the Nucleo
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNucleoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nucleoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nucleoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNucleo() throws Exception {
        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();
        nucleo.setId(count.incrementAndGet());

        // Create the Nucleo
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNucleoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nucleoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNucleo() throws Exception {
        int databaseSizeBeforeUpdate = nucleoRepository.findAll().size();
        nucleo.setId(count.incrementAndGet());

        // Create the Nucleo
        NucleoDTO nucleoDTO = nucleoMapper.toDto(nucleo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNucleoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nucleoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nucleo in the database
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNucleo() throws Exception {
        // Initialize the database
        nucleoRepository.saveAndFlush(nucleo);

        int databaseSizeBeforeDelete = nucleoRepository.findAll().size();

        // Delete the nucleo
        restNucleoMockMvc
            .perform(delete(ENTITY_API_URL_ID, nucleo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nucleo> nucleoList = nucleoRepository.findAll();
        assertThat(nucleoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
