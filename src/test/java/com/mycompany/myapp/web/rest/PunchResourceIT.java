package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyLocationApp;
import com.mycompany.myapp.domain.Punch;
import com.mycompany.myapp.repository.PunchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PunchResource} REST controller.
 */
@SpringBootTest(classes = MyLocationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PunchResourceIT {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MYLATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MYLATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MY_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MY_LONGITUDE = new BigDecimal(2);

    private static final LocalDate DEFAULT_PUNCHTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUNCHTIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PunchRepository punchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPunchMockMvc;

    private Punch punch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Punch createEntity(EntityManager em) {
        Punch punch = new Punch()
            .user(DEFAULT_USER)
            .mylatitude(DEFAULT_MYLATITUDE)
            .myLongitude(DEFAULT_MY_LONGITUDE)
            .punchtime(DEFAULT_PUNCHTIME);
        return punch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Punch createUpdatedEntity(EntityManager em) {
        Punch punch = new Punch()
            .user(UPDATED_USER)
            .mylatitude(UPDATED_MYLATITUDE)
            .myLongitude(UPDATED_MY_LONGITUDE)
            .punchtime(UPDATED_PUNCHTIME);
        return punch;
    }

    @BeforeEach
    public void initTest() {
        punch = createEntity(em);
    }

    @Test
    @Transactional
    public void createPunch() throws Exception {
        int databaseSizeBeforeCreate = punchRepository.findAll().size();
        // Create the Punch
        restPunchMockMvc.perform(post("/api/punches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(punch)))
            .andExpect(status().isCreated());

        // Validate the Punch in the database
        List<Punch> punchList = punchRepository.findAll();
        assertThat(punchList).hasSize(databaseSizeBeforeCreate + 1);
        Punch testPunch = punchList.get(punchList.size() - 1);
        assertThat(testPunch.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testPunch.getMylatitude()).isEqualTo(DEFAULT_MYLATITUDE);
        assertThat(testPunch.getMyLongitude()).isEqualTo(DEFAULT_MY_LONGITUDE);
        assertThat(testPunch.getPunchtime()).isEqualTo(DEFAULT_PUNCHTIME);
    }

    @Test
    @Transactional
    public void createPunchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = punchRepository.findAll().size();

        // Create the Punch with an existing ID
        punch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPunchMockMvc.perform(post("/api/punches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(punch)))
            .andExpect(status().isBadRequest());

        // Validate the Punch in the database
        List<Punch> punchList = punchRepository.findAll();
        assertThat(punchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPunches() throws Exception {
        // Initialize the database
        punchRepository.saveAndFlush(punch);

        // Get all the punchList
        restPunchMockMvc.perform(get("/api/punches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(punch.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].mylatitude").value(hasItem(DEFAULT_MYLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].myLongitude").value(hasItem(DEFAULT_MY_LONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].punchtime").value(hasItem(DEFAULT_PUNCHTIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPunch() throws Exception {
        // Initialize the database
        punchRepository.saveAndFlush(punch);

        // Get the punch
        restPunchMockMvc.perform(get("/api/punches/{id}", punch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(punch.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.mylatitude").value(DEFAULT_MYLATITUDE.intValue()))
            .andExpect(jsonPath("$.myLongitude").value(DEFAULT_MY_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.punchtime").value(DEFAULT_PUNCHTIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPunch() throws Exception {
        // Get the punch
        restPunchMockMvc.perform(get("/api/punches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePunch() throws Exception {
        // Initialize the database
        punchRepository.saveAndFlush(punch);

        int databaseSizeBeforeUpdate = punchRepository.findAll().size();

        // Update the punch
        Punch updatedPunch = punchRepository.findById(punch.getId()).get();
        // Disconnect from session so that the updates on updatedPunch are not directly saved in db
        em.detach(updatedPunch);
        updatedPunch
            .user(UPDATED_USER)
            .mylatitude(UPDATED_MYLATITUDE)
            .myLongitude(UPDATED_MY_LONGITUDE)
            .punchtime(UPDATED_PUNCHTIME);

        restPunchMockMvc.perform(put("/api/punches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPunch)))
            .andExpect(status().isOk());

        // Validate the Punch in the database
        List<Punch> punchList = punchRepository.findAll();
        assertThat(punchList).hasSize(databaseSizeBeforeUpdate);
        Punch testPunch = punchList.get(punchList.size() - 1);
        assertThat(testPunch.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testPunch.getMylatitude()).isEqualTo(UPDATED_MYLATITUDE);
        assertThat(testPunch.getMyLongitude()).isEqualTo(UPDATED_MY_LONGITUDE);
        assertThat(testPunch.getPunchtime()).isEqualTo(UPDATED_PUNCHTIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPunch() throws Exception {
        int databaseSizeBeforeUpdate = punchRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPunchMockMvc.perform(put("/api/punches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(punch)))
            .andExpect(status().isBadRequest());

        // Validate the Punch in the database
        List<Punch> punchList = punchRepository.findAll();
        assertThat(punchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePunch() throws Exception {
        // Initialize the database
        punchRepository.saveAndFlush(punch);

        int databaseSizeBeforeDelete = punchRepository.findAll().size();

        // Delete the punch
        restPunchMockMvc.perform(delete("/api/punches/{id}", punch.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Punch> punchList = punchRepository.findAll();
        assertThat(punchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
