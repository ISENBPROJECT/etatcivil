package com.consulsen.etatcivil.web.rest;

import com.consulsen.etatcivil.EtatcivilApp;
import com.consulsen.etatcivil.domain.DeclarationNaissance;
import com.consulsen.etatcivil.repository.DeclarationNaissanceRepository;
import com.consulsen.etatcivil.service.DeclarationNaissanceService;
import com.consulsen.etatcivil.web.rest.dto.DeclarationNaissanceDTO;
import com.consulsen.etatcivil.web.rest.mapper.DeclarationNaissanceMapper;

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
 * Test class for the DeclarationNaissanceResource REST controller.
 *
 * @see DeclarationNaissanceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EtatcivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class DeclarationNaissanceResourceIntTest {


    private static final LocalDate DEFAULT_DATE_DECLARATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DECLARATION = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_IDENTIFIANT_ENFANT = 1L;
    private static final Long UPDATED_IDENTIFIANT_ENFANT = 2L;

    private static final Long DEFAULT_IDENTIFIANT_PERE = 1L;
    private static final Long UPDATED_IDENTIFIANT_PERE = 2L;

    private static final Long DEFAULT_IDENTIFIANT_MERE = 1L;
    private static final Long UPDATED_IDENTIFIANT_MERE = 2L;
    private static final String DEFAULT_MENTION_MARGINALE = "AAAAA";
    private static final String UPDATED_MENTION_MARGINALE = "BBBBB";

    private static final Long DEFAULT_NUMERO_CARTE_IDENTITE = 1L;
    private static final Long UPDATED_NUMERO_CARTE_IDENTITE = 2L;

    private static final Long DEFAULT_NUMERO_PASS_PORT = 1L;
    private static final Long UPDATED_NUMERO_PASS_PORT = 2L;

    @Inject
    private DeclarationNaissanceRepository declarationNaissanceRepository;

    @Inject
    private DeclarationNaissanceMapper declarationNaissanceMapper;

    @Inject
    private DeclarationNaissanceService declarationNaissanceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeclarationNaissanceMockMvc;

    private DeclarationNaissance declarationNaissance;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeclarationNaissanceResource declarationNaissanceResource = new DeclarationNaissanceResource();
        ReflectionTestUtils.setField(declarationNaissanceResource, "declarationNaissanceService", declarationNaissanceService);
        ReflectionTestUtils.setField(declarationNaissanceResource, "declarationNaissanceMapper", declarationNaissanceMapper);
        this.restDeclarationNaissanceMockMvc = MockMvcBuilders.standaloneSetup(declarationNaissanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        declarationNaissance = new DeclarationNaissance();
        declarationNaissance.setDateDeclaration(DEFAULT_DATE_DECLARATION);
        declarationNaissance.setIdentifiantEnfant(DEFAULT_IDENTIFIANT_ENFANT);
        declarationNaissance.setIdentifiantPere(DEFAULT_IDENTIFIANT_PERE);
        declarationNaissance.setIdentifiantMere(DEFAULT_IDENTIFIANT_MERE);
        declarationNaissance.setMentionMarginale(DEFAULT_MENTION_MARGINALE);
        declarationNaissance.setNumeroCarteIdentite(DEFAULT_NUMERO_CARTE_IDENTITE);
        declarationNaissance.setNumeroPassPort(DEFAULT_NUMERO_PASS_PORT);
    }

    @Test
    @Transactional
    public void createDeclarationNaissance() throws Exception {
        int databaseSizeBeforeCreate = declarationNaissanceRepository.findAll().size();

        // Create the DeclarationNaissance
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);

        restDeclarationNaissanceMockMvc.perform(post("/api/declaration-naissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationNaissanceDTO)))
                .andExpect(status().isCreated());

        // Validate the DeclarationNaissance in the database
        List<DeclarationNaissance> declarationNaissances = declarationNaissanceRepository.findAll();
        assertThat(declarationNaissances).hasSize(databaseSizeBeforeCreate + 1);
        DeclarationNaissance testDeclarationNaissance = declarationNaissances.get(declarationNaissances.size() - 1);
        assertThat(testDeclarationNaissance.getDateDeclaration()).isEqualTo(DEFAULT_DATE_DECLARATION);
        assertThat(testDeclarationNaissance.getIdentifiantEnfant()).isEqualTo(DEFAULT_IDENTIFIANT_ENFANT);
        assertThat(testDeclarationNaissance.getIdentifiantPere()).isEqualTo(DEFAULT_IDENTIFIANT_PERE);
        assertThat(testDeclarationNaissance.getIdentifiantMere()).isEqualTo(DEFAULT_IDENTIFIANT_MERE);
        assertThat(testDeclarationNaissance.getMentionMarginale()).isEqualTo(DEFAULT_MENTION_MARGINALE);
        assertThat(testDeclarationNaissance.getNumeroCarteIdentite()).isEqualTo(DEFAULT_NUMERO_CARTE_IDENTITE);
        assertThat(testDeclarationNaissance.getNumeroPassPort()).isEqualTo(DEFAULT_NUMERO_PASS_PORT);
    }

    @Test
    @Transactional
    public void checkDateDeclarationIsRequired() throws Exception {
        int databaseSizeBeforeTest = declarationNaissanceRepository.findAll().size();
        // set the field null
        declarationNaissance.setDateDeclaration(null);

        // Create the DeclarationNaissance, which fails.
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);

        restDeclarationNaissanceMockMvc.perform(post("/api/declaration-naissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationNaissanceDTO)))
                .andExpect(status().isBadRequest());

        List<DeclarationNaissance> declarationNaissances = declarationNaissanceRepository.findAll();
        assertThat(declarationNaissances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentifiantEnfantIsRequired() throws Exception {
        int databaseSizeBeforeTest = declarationNaissanceRepository.findAll().size();
        // set the field null
        declarationNaissance.setIdentifiantEnfant(null);

        // Create the DeclarationNaissance, which fails.
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);

        restDeclarationNaissanceMockMvc.perform(post("/api/declaration-naissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationNaissanceDTO)))
                .andExpect(status().isBadRequest());

        List<DeclarationNaissance> declarationNaissances = declarationNaissanceRepository.findAll();
        assertThat(declarationNaissances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentifiantPereIsRequired() throws Exception {
        int databaseSizeBeforeTest = declarationNaissanceRepository.findAll().size();
        // set the field null
        declarationNaissance.setIdentifiantPere(null);

        // Create the DeclarationNaissance, which fails.
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);

        restDeclarationNaissanceMockMvc.perform(post("/api/declaration-naissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationNaissanceDTO)))
                .andExpect(status().isBadRequest());

        List<DeclarationNaissance> declarationNaissances = declarationNaissanceRepository.findAll();
        assertThat(declarationNaissances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentifiantMereIsRequired() throws Exception {
        int databaseSizeBeforeTest = declarationNaissanceRepository.findAll().size();
        // set the field null
        declarationNaissance.setIdentifiantMere(null);

        // Create the DeclarationNaissance, which fails.
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);

        restDeclarationNaissanceMockMvc.perform(post("/api/declaration-naissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationNaissanceDTO)))
                .andExpect(status().isBadRequest());

        List<DeclarationNaissance> declarationNaissances = declarationNaissanceRepository.findAll();
        assertThat(declarationNaissances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeclarationNaissances() throws Exception {
        // Initialize the database
        declarationNaissanceRepository.saveAndFlush(declarationNaissance);

        // Get all the declarationNaissances
        restDeclarationNaissanceMockMvc.perform(get("/api/declaration-naissances?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(declarationNaissance.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateDeclaration").value(hasItem(DEFAULT_DATE_DECLARATION.toString())))
                .andExpect(jsonPath("$.[*].identifiantEnfant").value(hasItem(DEFAULT_IDENTIFIANT_ENFANT.intValue())))
                .andExpect(jsonPath("$.[*].identifiantPere").value(hasItem(DEFAULT_IDENTIFIANT_PERE.intValue())))
                .andExpect(jsonPath("$.[*].identifiantMere").value(hasItem(DEFAULT_IDENTIFIANT_MERE.intValue())))
                .andExpect(jsonPath("$.[*].mentionMarginale").value(hasItem(DEFAULT_MENTION_MARGINALE.toString())))
                .andExpect(jsonPath("$.[*].numeroCarteIdentite").value(hasItem(DEFAULT_NUMERO_CARTE_IDENTITE.intValue())))
                .andExpect(jsonPath("$.[*].numeroPassPort").value(hasItem(DEFAULT_NUMERO_PASS_PORT.intValue())));
    }

    @Test
    @Transactional
    public void getDeclarationNaissance() throws Exception {
        // Initialize the database
        declarationNaissanceRepository.saveAndFlush(declarationNaissance);

        // Get the declarationNaissance
        restDeclarationNaissanceMockMvc.perform(get("/api/declaration-naissances/{id}", declarationNaissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(declarationNaissance.getId().intValue()))
            .andExpect(jsonPath("$.dateDeclaration").value(DEFAULT_DATE_DECLARATION.toString()))
            .andExpect(jsonPath("$.identifiantEnfant").value(DEFAULT_IDENTIFIANT_ENFANT.intValue()))
            .andExpect(jsonPath("$.identifiantPere").value(DEFAULT_IDENTIFIANT_PERE.intValue()))
            .andExpect(jsonPath("$.identifiantMere").value(DEFAULT_IDENTIFIANT_MERE.intValue()))
            .andExpect(jsonPath("$.mentionMarginale").value(DEFAULT_MENTION_MARGINALE.toString()))
            .andExpect(jsonPath("$.numeroCarteIdentite").value(DEFAULT_NUMERO_CARTE_IDENTITE.intValue()))
            .andExpect(jsonPath("$.numeroPassPort").value(DEFAULT_NUMERO_PASS_PORT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDeclarationNaissance() throws Exception {
        // Get the declarationNaissance
        restDeclarationNaissanceMockMvc.perform(get("/api/declaration-naissances/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeclarationNaissance() throws Exception {
        // Initialize the database
        declarationNaissanceRepository.saveAndFlush(declarationNaissance);
        int databaseSizeBeforeUpdate = declarationNaissanceRepository.findAll().size();

        // Update the declarationNaissance
        DeclarationNaissance updatedDeclarationNaissance = new DeclarationNaissance();
        updatedDeclarationNaissance.setId(declarationNaissance.getId());
        updatedDeclarationNaissance.setDateDeclaration(UPDATED_DATE_DECLARATION);
        updatedDeclarationNaissance.setIdentifiantEnfant(UPDATED_IDENTIFIANT_ENFANT);
        updatedDeclarationNaissance.setIdentifiantPere(UPDATED_IDENTIFIANT_PERE);
        updatedDeclarationNaissance.setIdentifiantMere(UPDATED_IDENTIFIANT_MERE);
        updatedDeclarationNaissance.setMentionMarginale(UPDATED_MENTION_MARGINALE);
        updatedDeclarationNaissance.setNumeroCarteIdentite(UPDATED_NUMERO_CARTE_IDENTITE);
        updatedDeclarationNaissance.setNumeroPassPort(UPDATED_NUMERO_PASS_PORT);
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(updatedDeclarationNaissance);

        restDeclarationNaissanceMockMvc.perform(put("/api/declaration-naissances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(declarationNaissanceDTO)))
                .andExpect(status().isOk());

        // Validate the DeclarationNaissance in the database
        List<DeclarationNaissance> declarationNaissances = declarationNaissanceRepository.findAll();
        assertThat(declarationNaissances).hasSize(databaseSizeBeforeUpdate);
        DeclarationNaissance testDeclarationNaissance = declarationNaissances.get(declarationNaissances.size() - 1);
        assertThat(testDeclarationNaissance.getDateDeclaration()).isEqualTo(UPDATED_DATE_DECLARATION);
        assertThat(testDeclarationNaissance.getIdentifiantEnfant()).isEqualTo(UPDATED_IDENTIFIANT_ENFANT);
        assertThat(testDeclarationNaissance.getIdentifiantPere()).isEqualTo(UPDATED_IDENTIFIANT_PERE);
        assertThat(testDeclarationNaissance.getIdentifiantMere()).isEqualTo(UPDATED_IDENTIFIANT_MERE);
        assertThat(testDeclarationNaissance.getMentionMarginale()).isEqualTo(UPDATED_MENTION_MARGINALE);
        assertThat(testDeclarationNaissance.getNumeroCarteIdentite()).isEqualTo(UPDATED_NUMERO_CARTE_IDENTITE);
        assertThat(testDeclarationNaissance.getNumeroPassPort()).isEqualTo(UPDATED_NUMERO_PASS_PORT);
    }

    @Test
    @Transactional
    public void deleteDeclarationNaissance() throws Exception {
        // Initialize the database
        declarationNaissanceRepository.saveAndFlush(declarationNaissance);
        int databaseSizeBeforeDelete = declarationNaissanceRepository.findAll().size();

        // Get the declarationNaissance
        restDeclarationNaissanceMockMvc.perform(delete("/api/declaration-naissances/{id}", declarationNaissance.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeclarationNaissance> declarationNaissances = declarationNaissanceRepository.findAll();
        assertThat(declarationNaissances).hasSize(databaseSizeBeforeDelete - 1);
    }
}
