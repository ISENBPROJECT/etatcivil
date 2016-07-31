package com.consulsen.etatcivil.web.rest;

import com.consulsen.etatcivil.EtatcivilApp;
import com.consulsen.etatcivil.domain.Personne;
import com.consulsen.etatcivil.repository.PersonneRepository;
import com.consulsen.etatcivil.service.PersonneService;
import com.consulsen.etatcivil.web.rest.dto.PersonneDTO;
import com.consulsen.etatcivil.web.rest.mapper.PersonneMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PersonneResource REST controller.
 *
 * @see PersonneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EtatcivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class PersonneResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";
    private static final String DEFAULT_PRENOM = "AAAAA";
    private static final String UPDATED_PRENOM = "BBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PersonneRepository personneRepository;

    @Inject
    private PersonneMapper personneMapper;

    @Inject
    private PersonneService personneService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPersonneMockMvc;

    private Personne personne;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonneResource personneResource = new PersonneResource();
        ReflectionTestUtils.setField(personneResource, "personneService", personneService);
        ReflectionTestUtils.setField(personneResource, "personneMapper", personneMapper);
        this.restPersonneMockMvc = MockMvcBuilders.standaloneSetup(personneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        personne = new Personne();
        personne.setNom(DEFAULT_NOM);
        personne.setPrenom(DEFAULT_PRENOM);
        personne.setDateNaissance(DEFAULT_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void createPersonne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne
        PersonneDTO personneDTO = personneMapper.personneToPersonneDTO(personne);

        restPersonneMockMvc.perform(post("/api/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personneDTO)))
                .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personnes.get(personnes.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonne.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneRepository.findAll().size();
        // set the field null
        personne.setNom(null);

        // Create the Personne, which fails.
        PersonneDTO personneDTO = personneMapper.personneToPersonneDTO(personne);

        restPersonneMockMvc.perform(post("/api/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personneDTO)))
                .andExpect(status().isBadRequest());

        List<Personne> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneRepository.findAll().size();
        // set the field null
        personne.setPrenom(null);

        // Create the Personne, which fails.
        PersonneDTO personneDTO = personneMapper.personneToPersonneDTO(personne);

        restPersonneMockMvc.perform(post("/api/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personneDTO)))
                .andExpect(status().isBadRequest());

        List<Personne> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get all the personnes
        restPersonneMockMvc.perform(get("/api/personnes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
                .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())));
    }

    @Test
    @Transactional
    public void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        Personne updatedPersonne = new Personne();
        updatedPersonne.setId(personne.getId());
        updatedPersonne.setNom(UPDATED_NOM);
        updatedPersonne.setPrenom(UPDATED_PRENOM);
        updatedPersonne.setDateNaissance(UPDATED_DATE_NAISSANCE);
        PersonneDTO personneDTO = personneMapper.personneToPersonneDTO(updatedPersonne);

        restPersonneMockMvc.perform(put("/api/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personneDTO)))
                .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personnes.get(personnes.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonne.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void deletePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);
        int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Get the personne
        restPersonneMockMvc.perform(delete("/api/personnes/{id}", personne.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Personne> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
