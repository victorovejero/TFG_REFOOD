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

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

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
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION)
            .provincia(DEFAULT_PROVINCIA)
            .responsable(DEFAULT_RESPONSABLE)
            .telefono(DEFAULT_TELEFONO)
            .email(DEFAULT_EMAIL)
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
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .provincia(UPDATED_PROVINCIA)
            .responsable(UPDATED_RESPONSABLE)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
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
        assertThat(testNucleo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testNucleo.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testNucleo.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testNucleo.getResponsable()).isEqualTo(DEFAULT_RESPONSABLE);
        assertThat(testNucleo.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testNucleo.getEmail()).isEqualTo(DEFAULT_EMAIL);
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
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setNombre(null);

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
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setDireccion(null);

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
    void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setProvincia(null);

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
    void checkResponsableIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setResponsable(null);

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
    void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setTelefono(null);

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
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = nucleoRepository.findAll().size();
        // set the field null
        nucleo.setEmail(null);

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
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].responsable").value(hasItem(DEFAULT_RESPONSABLE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
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
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.responsable").value(DEFAULT_RESPONSABLE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
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
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .provincia(UPDATED_PROVINCIA)
            .responsable(UPDATED_RESPONSABLE)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
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
        assertThat(testNucleo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNucleo.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testNucleo.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testNucleo.getResponsable()).isEqualTo(UPDATED_RESPONSABLE);
        assertThat(testNucleo.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testNucleo.getEmail()).isEqualTo(UPDATED_EMAIL);
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

        partialUpdatedNucleo.nombre(UPDATED_NOMBRE).responsable(UPDATED_RESPONSABLE).email(UPDATED_EMAIL);

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
        assertThat(testNucleo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNucleo.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testNucleo.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testNucleo.getResponsable()).isEqualTo(UPDATED_RESPONSABLE);
        assertThat(testNucleo.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testNucleo.getEmail()).isEqualTo(UPDATED_EMAIL);
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
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .provincia(UPDATED_PROVINCIA)
            .responsable(UPDATED_RESPONSABLE)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
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
        assertThat(testNucleo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNucleo.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testNucleo.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testNucleo.getResponsable()).isEqualTo(UPDATED_RESPONSABLE);
        assertThat(testNucleo.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testNucleo.getEmail()).isEqualTo(UPDATED_EMAIL);
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
