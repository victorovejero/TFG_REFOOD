package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Registro;
import com.refood.trazabilidad.repository.RegistroRepository;
import com.refood.trazabilidad.service.RegistroService;
import com.refood.trazabilidad.service.dto.RegistroDTO;
import com.refood.trazabilidad.service.mapper.RegistroMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link RegistroResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RegistroResourceIT {

    private static final LocalDate DEFAULT_DIA_ACTIVIDAD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DIA_ACTIVIDAD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RUTA = "AAAAAAAAAA";
    private static final String UPDATED_RUTA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/registros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistroRepository registroRepository;

    @Mock
    private RegistroRepository registroRepositoryMock;

    @Autowired
    private RegistroMapper registroMapper;

    @Mock
    private RegistroService registroServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistroMockMvc;

    private Registro registro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Registro createEntity(EntityManager em) {
        Registro registro = new Registro().diaActividad(DEFAULT_DIA_ACTIVIDAD).ruta(DEFAULT_RUTA);
        return registro;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Registro createUpdatedEntity(EntityManager em) {
        Registro registro = new Registro().diaActividad(UPDATED_DIA_ACTIVIDAD).ruta(UPDATED_RUTA);
        return registro;
    }

    @BeforeEach
    public void initTest() {
        registro = createEntity(em);
    }

    @Test
    @Transactional
    void createRegistro() throws Exception {
        int databaseSizeBeforeCreate = registroRepository.findAll().size();
        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);
        restRegistroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isCreated());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeCreate + 1);
        Registro testRegistro = registroList.get(registroList.size() - 1);
        assertThat(testRegistro.getDiaActividad()).isEqualTo(DEFAULT_DIA_ACTIVIDAD);
        assertThat(testRegistro.getRuta()).isEqualTo(DEFAULT_RUTA);
    }

    @Test
    @Transactional
    void createRegistroWithExistingId() throws Exception {
        // Create the Registro with an existing ID
        registro.setId(1L);
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        int databaseSizeBeforeCreate = registroRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDiaActividadIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroRepository.findAll().size();
        // set the field null
        registro.setDiaActividad(null);

        // Create the Registro, which fails.
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        restRegistroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isBadRequest());

        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRutaIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroRepository.findAll().size();
        // set the field null
        registro.setRuta(null);

        // Create the Registro, which fails.
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        restRegistroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isBadRequest());

        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegistros() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        // Get all the registroList
        restRegistroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registro.getId().intValue())))
            .andExpect(jsonPath("$.[*].diaActividad").value(hasItem(DEFAULT_DIA_ACTIVIDAD.toString())))
            .andExpect(jsonPath("$.[*].ruta").value(hasItem(DEFAULT_RUTA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRegistrosWithEagerRelationshipsIsEnabled() throws Exception {
        when(registroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRegistroMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(registroServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRegistrosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(registroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRegistroMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(registroRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRegistro() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        // Get the registro
        restRegistroMockMvc
            .perform(get(ENTITY_API_URL_ID, registro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registro.getId().intValue()))
            .andExpect(jsonPath("$.diaActividad").value(DEFAULT_DIA_ACTIVIDAD.toString()))
            .andExpect(jsonPath("$.ruta").value(DEFAULT_RUTA));
    }

    @Test
    @Transactional
    void getNonExistingRegistro() throws Exception {
        // Get the registro
        restRegistroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegistro() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        int databaseSizeBeforeUpdate = registroRepository.findAll().size();

        // Update the registro
        Registro updatedRegistro = registroRepository.findById(registro.getId()).get();
        // Disconnect from session so that the updates on updatedRegistro are not directly saved in db
        em.detach(updatedRegistro);
        updatedRegistro.diaActividad(UPDATED_DIA_ACTIVIDAD).ruta(UPDATED_RUTA);
        RegistroDTO registroDTO = registroMapper.toDto(updatedRegistro);

        restRegistroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registroDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDTO))
            )
            .andExpect(status().isOk());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
        Registro testRegistro = registroList.get(registroList.size() - 1);
        assertThat(testRegistro.getDiaActividad()).isEqualTo(UPDATED_DIA_ACTIVIDAD);
        assertThat(testRegistro.getRuta()).isEqualTo(UPDATED_RUTA);
    }

    @Test
    @Transactional
    void putNonExistingRegistro() throws Exception {
        int databaseSizeBeforeUpdate = registroRepository.findAll().size();
        registro.setId(count.incrementAndGet());

        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registroDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegistro() throws Exception {
        int databaseSizeBeforeUpdate = registroRepository.findAll().size();
        registro.setId(count.incrementAndGet());

        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegistro() throws Exception {
        int databaseSizeBeforeUpdate = registroRepository.findAll().size();
        registro.setId(count.incrementAndGet());

        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegistroWithPatch() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        int databaseSizeBeforeUpdate = registroRepository.findAll().size();

        // Update the registro using partial update
        Registro partialUpdatedRegistro = new Registro();
        partialUpdatedRegistro.setId(registro.getId());

        restRegistroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistro))
            )
            .andExpect(status().isOk());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
        Registro testRegistro = registroList.get(registroList.size() - 1);
        assertThat(testRegistro.getDiaActividad()).isEqualTo(DEFAULT_DIA_ACTIVIDAD);
        assertThat(testRegistro.getRuta()).isEqualTo(DEFAULT_RUTA);
    }

    @Test
    @Transactional
    void fullUpdateRegistroWithPatch() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        int databaseSizeBeforeUpdate = registroRepository.findAll().size();

        // Update the registro using partial update
        Registro partialUpdatedRegistro = new Registro();
        partialUpdatedRegistro.setId(registro.getId());

        partialUpdatedRegistro.diaActividad(UPDATED_DIA_ACTIVIDAD).ruta(UPDATED_RUTA);

        restRegistroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistro))
            )
            .andExpect(status().isOk());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
        Registro testRegistro = registroList.get(registroList.size() - 1);
        assertThat(testRegistro.getDiaActividad()).isEqualTo(UPDATED_DIA_ACTIVIDAD);
        assertThat(testRegistro.getRuta()).isEqualTo(UPDATED_RUTA);
    }

    @Test
    @Transactional
    void patchNonExistingRegistro() throws Exception {
        int databaseSizeBeforeUpdate = registroRepository.findAll().size();
        registro.setId(count.incrementAndGet());

        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, registroDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegistro() throws Exception {
        int databaseSizeBeforeUpdate = registroRepository.findAll().size();
        registro.setId(count.incrementAndGet());

        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegistro() throws Exception {
        int databaseSizeBeforeUpdate = registroRepository.findAll().size();
        registro.setId(count.incrementAndGet());

        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(registroDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegistro() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        int databaseSizeBeforeDelete = registroRepository.findAll().size();

        // Delete the registro
        restRegistroMockMvc
            .perform(delete(ENTITY_API_URL_ID, registro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
