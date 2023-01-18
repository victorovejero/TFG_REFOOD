package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.PBenef;
import com.refood.trazabilidad.repository.PBenefRepository;
import com.refood.trazabilidad.service.PBenefService;
import com.refood.trazabilidad.service.dto.PBenefDTO;
import com.refood.trazabilidad.service.mapper.PBenefMapper;
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
 * Integration tests for the {@link PBenefResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PBenefResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_NACIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_NACIMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_PARENTESCO = "BBBBBBBBBB";

    private static final String DEFAULT_SITUACION_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_SITUACION_PROFESIONAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-benefs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PBenefRepository pBenefRepository;

    @Mock
    private PBenefRepository pBenefRepositoryMock;

    @Autowired
    private PBenefMapper pBenefMapper;

    @Mock
    private PBenefService pBenefServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPBenefMockMvc;

    private PBenef pBenef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PBenef createEntity(EntityManager em) {
        PBenef pBenef = new PBenef()
            .nombre(DEFAULT_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .sexo(DEFAULT_SEXO)
            .parentesco(DEFAULT_PARENTESCO)
            .situacionProfesional(DEFAULT_SITUACION_PROFESIONAL);
        return pBenef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PBenef createUpdatedEntity(EntityManager em) {
        PBenef pBenef = new PBenef()
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .situacionProfesional(UPDATED_SITUACION_PROFESIONAL);
        return pBenef;
    }

    @BeforeEach
    public void initTest() {
        pBenef = createEntity(em);
    }

    @Test
    @Transactional
    void createPBenef() throws Exception {
        int databaseSizeBeforeCreate = pBenefRepository.findAll().size();
        // Create the PBenef
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);
        restPBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isCreated());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeCreate + 1);
        PBenef testPBenef = pBenefList.get(pBenefList.size() - 1);
        assertThat(testPBenef.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPBenef.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testPBenef.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testPBenef.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPBenef.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPBenef.getParentesco()).isEqualTo(DEFAULT_PARENTESCO);
        assertThat(testPBenef.getSituacionProfesional()).isEqualTo(DEFAULT_SITUACION_PROFESIONAL);
    }

    @Test
    @Transactional
    void createPBenefWithExistingId() throws Exception {
        // Create the PBenef with an existing ID
        pBenef.setId(1L);
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        int databaseSizeBeforeCreate = pBenefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = pBenefRepository.findAll().size();
        // set the field null
        pBenef.setNombre(null);

        // Create the PBenef, which fails.
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        restPBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isBadRequest());

        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimerApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pBenefRepository.findAll().size();
        // set the field null
        pBenef.setPrimerApellido(null);

        // Create the PBenef, which fails.
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        restPBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isBadRequest());

        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pBenefRepository.findAll().size();
        // set the field null
        pBenef.setFechaNacimiento(null);

        // Create the PBenef, which fails.
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        restPBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isBadRequest());

        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pBenefRepository.findAll().size();
        // set the field null
        pBenef.setSexo(null);

        // Create the PBenef, which fails.
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        restPBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isBadRequest());

        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkParentescoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pBenefRepository.findAll().size();
        // set the field null
        pBenef.setParentesco(null);

        // Create the PBenef, which fails.
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        restPBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isBadRequest());

        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSituacionProfesionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = pBenefRepository.findAll().size();
        // set the field null
        pBenef.setSituacionProfesional(null);

        // Create the PBenef, which fails.
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        restPBenefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isBadRequest());

        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPBenefs() throws Exception {
        // Initialize the database
        pBenefRepository.saveAndFlush(pBenef);

        // Get all the pBenefList
        restPBenefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pBenef.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].parentesco").value(hasItem(DEFAULT_PARENTESCO)))
            .andExpect(jsonPath("$.[*].situacionProfesional").value(hasItem(DEFAULT_SITUACION_PROFESIONAL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPBenefsWithEagerRelationshipsIsEnabled() throws Exception {
        when(pBenefServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPBenefMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pBenefServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPBenefsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pBenefServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPBenefMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(pBenefRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPBenef() throws Exception {
        // Initialize the database
        pBenefRepository.saveAndFlush(pBenef);

        // Get the pBenef
        restPBenefMockMvc
            .perform(get(ENTITY_API_URL_ID, pBenef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pBenef.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.parentesco").value(DEFAULT_PARENTESCO))
            .andExpect(jsonPath("$.situacionProfesional").value(DEFAULT_SITUACION_PROFESIONAL));
    }

    @Test
    @Transactional
    void getNonExistingPBenef() throws Exception {
        // Get the pBenef
        restPBenefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPBenef() throws Exception {
        // Initialize the database
        pBenefRepository.saveAndFlush(pBenef);

        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();

        // Update the pBenef
        PBenef updatedPBenef = pBenefRepository.findById(pBenef.getId()).get();
        // Disconnect from session so that the updates on updatedPBenef are not directly saved in db
        em.detach(updatedPBenef);
        updatedPBenef
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .situacionProfesional(UPDATED_SITUACION_PROFESIONAL);
        PBenefDTO pBenefDTO = pBenefMapper.toDto(updatedPBenef);

        restPBenefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pBenefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pBenefDTO))
            )
            .andExpect(status().isOk());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
        PBenef testPBenef = pBenefList.get(pBenefList.size() - 1);
        assertThat(testPBenef.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPBenef.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testPBenef.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testPBenef.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPBenef.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPBenef.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testPBenef.getSituacionProfesional()).isEqualTo(UPDATED_SITUACION_PROFESIONAL);
    }

    @Test
    @Transactional
    void putNonExistingPBenef() throws Exception {
        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();
        pBenef.setId(count.incrementAndGet());

        // Create the PBenef
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPBenefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pBenefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pBenefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPBenef() throws Exception {
        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();
        pBenef.setId(count.incrementAndGet());

        // Create the PBenef
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPBenefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pBenefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPBenef() throws Exception {
        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();
        pBenef.setId(count.incrementAndGet());

        // Create the PBenef
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPBenefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pBenefDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePBenefWithPatch() throws Exception {
        // Initialize the database
        pBenefRepository.saveAndFlush(pBenef);

        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();

        // Update the pBenef using partial update
        PBenef partialUpdatedPBenef = new PBenef();
        partialUpdatedPBenef.setId(pBenef.getId());

        partialUpdatedPBenef
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .situacionProfesional(UPDATED_SITUACION_PROFESIONAL);

        restPBenefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPBenef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPBenef))
            )
            .andExpect(status().isOk());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
        PBenef testPBenef = pBenefList.get(pBenefList.size() - 1);
        assertThat(testPBenef.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPBenef.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testPBenef.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testPBenef.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPBenef.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPBenef.getParentesco()).isEqualTo(DEFAULT_PARENTESCO);
        assertThat(testPBenef.getSituacionProfesional()).isEqualTo(UPDATED_SITUACION_PROFESIONAL);
    }

    @Test
    @Transactional
    void fullUpdatePBenefWithPatch() throws Exception {
        // Initialize the database
        pBenefRepository.saveAndFlush(pBenef);

        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();

        // Update the pBenef using partial update
        PBenef partialUpdatedPBenef = new PBenef();
        partialUpdatedPBenef.setId(pBenef.getId());

        partialUpdatedPBenef
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .situacionProfesional(UPDATED_SITUACION_PROFESIONAL);

        restPBenefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPBenef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPBenef))
            )
            .andExpect(status().isOk());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
        PBenef testPBenef = pBenefList.get(pBenefList.size() - 1);
        assertThat(testPBenef.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPBenef.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testPBenef.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testPBenef.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPBenef.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPBenef.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testPBenef.getSituacionProfesional()).isEqualTo(UPDATED_SITUACION_PROFESIONAL);
    }

    @Test
    @Transactional
    void patchNonExistingPBenef() throws Exception {
        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();
        pBenef.setId(count.incrementAndGet());

        // Create the PBenef
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPBenefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pBenefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pBenefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPBenef() throws Exception {
        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();
        pBenef.setId(count.incrementAndGet());

        // Create the PBenef
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPBenefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pBenefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPBenef() throws Exception {
        int databaseSizeBeforeUpdate = pBenefRepository.findAll().size();
        pBenef.setId(count.incrementAndGet());

        // Create the PBenef
        PBenefDTO pBenefDTO = pBenefMapper.toDto(pBenef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPBenefMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pBenefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PBenef in the database
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePBenef() throws Exception {
        // Initialize the database
        pBenefRepository.saveAndFlush(pBenef);

        int databaseSizeBeforeDelete = pBenefRepository.findAll().size();

        // Delete the pBenef
        restPBenefMockMvc
            .perform(delete(ENTITY_API_URL_ID, pBenef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PBenef> pBenefList = pBenefRepository.findAll();
        assertThat(pBenefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
