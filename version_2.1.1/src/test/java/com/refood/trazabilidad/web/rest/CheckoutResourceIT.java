package com.refood.trazabilidad.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.refood.trazabilidad.IntegrationTest;
import com.refood.trazabilidad.domain.Checkout;
import com.refood.trazabilidad.repository.CheckoutRepository;
import com.refood.trazabilidad.service.CheckoutService;
import com.refood.trazabilidad.service.dto.CheckoutDTO;
import com.refood.trazabilidad.service.mapper.CheckoutMapper;
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
 * Integration tests for the {@link CheckoutResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CheckoutResourceIT {

    private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PESO = 1;
    private static final Integer UPDATED_PESO = 2;

    private static final String ENTITY_API_URL = "/api/checkouts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Mock
    private CheckoutRepository checkoutRepositoryMock;

    @Autowired
    private CheckoutMapper checkoutMapper;

    @Mock
    private CheckoutService checkoutServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckoutMockMvc;

    private Checkout checkout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Checkout createEntity(EntityManager em) {
        Checkout checkout = new Checkout().fechaSalida(DEFAULT_FECHA_SALIDA).peso(DEFAULT_PESO);
        return checkout;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Checkout createUpdatedEntity(EntityManager em) {
        Checkout checkout = new Checkout().fechaSalida(UPDATED_FECHA_SALIDA).peso(UPDATED_PESO);
        return checkout;
    }

    @BeforeEach
    public void initTest() {
        checkout = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckout() throws Exception {
        int databaseSizeBeforeCreate = checkoutRepository.findAll().size();
        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);
        restCheckoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isCreated());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeCreate + 1);
        Checkout testCheckout = checkoutList.get(checkoutList.size() - 1);
        assertThat(testCheckout.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testCheckout.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    void createCheckoutWithExistingId() throws Exception {
        // Create the Checkout with an existing ID
        checkout.setId(1L);
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        int databaseSizeBeforeCreate = checkoutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaSalidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkoutRepository.findAll().size();
        // set the field null
        checkout.setFechaSalida(null);

        // Create the Checkout, which fails.
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        restCheckoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isBadRequest());

        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkoutRepository.findAll().size();
        // set the field null
        checkout.setPeso(null);

        // Create the Checkout, which fails.
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        restCheckoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isBadRequest());

        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCheckouts() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);

        // Get all the checkoutList
        restCheckoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkout.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCheckoutsWithEagerRelationshipsIsEnabled() throws Exception {
        when(checkoutServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCheckoutMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(checkoutServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCheckoutsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(checkoutServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCheckoutMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(checkoutRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCheckout() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);

        // Get the checkout
        restCheckoutMockMvc
            .perform(get(ENTITY_API_URL_ID, checkout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkout.getId().intValue()))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO));
    }

    @Test
    @Transactional
    void getNonExistingCheckout() throws Exception {
        // Get the checkout
        restCheckoutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCheckout() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);

        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();

        // Update the checkout
        Checkout updatedCheckout = checkoutRepository.findById(checkout.getId()).get();
        // Disconnect from session so that the updates on updatedCheckout are not directly saved in db
        em.detach(updatedCheckout);
        updatedCheckout.fechaSalida(UPDATED_FECHA_SALIDA).peso(UPDATED_PESO);
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(updatedCheckout);

        restCheckoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkoutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkoutDTO))
            )
            .andExpect(status().isOk());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
        Checkout testCheckout = checkoutList.get(checkoutList.size() - 1);
        assertThat(testCheckout.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testCheckout.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void putNonExistingCheckout() throws Exception {
        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();
        checkout.setId(count.incrementAndGet());

        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkoutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckout() throws Exception {
        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();
        checkout.setId(count.incrementAndGet());

        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckout() throws Exception {
        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();
        checkout.setId(count.incrementAndGet());

        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckoutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkoutDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckoutWithPatch() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);

        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();

        // Update the checkout using partial update
        Checkout partialUpdatedCheckout = new Checkout();
        partialUpdatedCheckout.setId(checkout.getId());

        partialUpdatedCheckout.fechaSalida(UPDATED_FECHA_SALIDA).peso(UPDATED_PESO);

        restCheckoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckout))
            )
            .andExpect(status().isOk());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
        Checkout testCheckout = checkoutList.get(checkoutList.size() - 1);
        assertThat(testCheckout.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testCheckout.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void fullUpdateCheckoutWithPatch() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);

        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();

        // Update the checkout using partial update
        Checkout partialUpdatedCheckout = new Checkout();
        partialUpdatedCheckout.setId(checkout.getId());

        partialUpdatedCheckout.fechaSalida(UPDATED_FECHA_SALIDA).peso(UPDATED_PESO);

        restCheckoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckout))
            )
            .andExpect(status().isOk());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
        Checkout testCheckout = checkoutList.get(checkoutList.size() - 1);
        assertThat(testCheckout.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testCheckout.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void patchNonExistingCheckout() throws Exception {
        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();
        checkout.setId(count.incrementAndGet());

        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkoutDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckout() throws Exception {
        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();
        checkout.setId(count.incrementAndGet());

        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckout() throws Exception {
        int databaseSizeBeforeUpdate = checkoutRepository.findAll().size();
        checkout.setId(count.incrementAndGet());

        // Create the Checkout
        CheckoutDTO checkoutDTO = checkoutMapper.toDto(checkout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckoutMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkoutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Checkout in the database
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckout() throws Exception {
        // Initialize the database
        checkoutRepository.saveAndFlush(checkout);

        int databaseSizeBeforeDelete = checkoutRepository.findAll().size();

        // Delete the checkout
        restCheckoutMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkout.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Checkout> checkoutList = checkoutRepository.findAll();
        assertThat(checkoutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
