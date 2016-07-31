package com.consulsen.etatcivil.web.rest;

import com.consulsen.etatcivil.EtatcivilApp;
import com.consulsen.etatcivil.domain.Fichier;
import com.consulsen.etatcivil.repository.FichierRepository;
import com.consulsen.etatcivil.service.FichierService;
import com.consulsen.etatcivil.web.rest.dto.FichierDTO;
import com.consulsen.etatcivil.web.rest.mapper.FichierMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FichierResource REST controller.
 *
 * @see FichierResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EtatcivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class FichierResourceIntTest {

    private static final String DEFAULT_NOM_FICHIER = "AAAAA";
    private static final String UPDATED_NOM_FICHIER = "BBBBB";
    private static final String DEFAULT_CHEMIN = "AAAAA";
    private static final String UPDATED_CHEMIN = "BBBBB";

    @Inject
    private FichierRepository fichierRepository;

    @Inject
    private FichierMapper fichierMapper;

    @Inject
    private FichierService fichierService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFichierMockMvc;

    private Fichier fichier;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FichierResource fichierResource = new FichierResource();
        ReflectionTestUtils.setField(fichierResource, "fichierService", fichierService);
        ReflectionTestUtils.setField(fichierResource, "fichierMapper", fichierMapper);
        this.restFichierMockMvc = MockMvcBuilders.standaloneSetup(fichierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fichier = new Fichier();
        fichier.setNomFichier(DEFAULT_NOM_FICHIER);
        fichier.setChemin(DEFAULT_CHEMIN);
    }

    @Test
    @Transactional
    public void createFichier() throws Exception {
        int databaseSizeBeforeCreate = fichierRepository.findAll().size();

        // Create the Fichier
        FichierDTO fichierDTO = fichierMapper.fichierToFichierDTO(fichier);

        restFichierMockMvc.perform(post("/api/fichiers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fichierDTO)))
                .andExpect(status().isCreated());

        // Validate the Fichier in the database
        List<Fichier> fichiers = fichierRepository.findAll();
        assertThat(fichiers).hasSize(databaseSizeBeforeCreate + 1);
        Fichier testFichier = fichiers.get(fichiers.size() - 1);
        assertThat(testFichier.getNomFichier()).isEqualTo(DEFAULT_NOM_FICHIER);
        assertThat(testFichier.getChemin()).isEqualTo(DEFAULT_CHEMIN);
    }

    @Test
    @Transactional
    public void checkNomFichierIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichierRepository.findAll().size();
        // set the field null
        fichier.setNomFichier(null);

        // Create the Fichier, which fails.
        FichierDTO fichierDTO = fichierMapper.fichierToFichierDTO(fichier);

        restFichierMockMvc.perform(post("/api/fichiers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fichierDTO)))
                .andExpect(status().isBadRequest());

        List<Fichier> fichiers = fichierRepository.findAll();
        assertThat(fichiers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheminIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichierRepository.findAll().size();
        // set the field null
        fichier.setChemin(null);

        // Create the Fichier, which fails.
        FichierDTO fichierDTO = fichierMapper.fichierToFichierDTO(fichier);

        restFichierMockMvc.perform(post("/api/fichiers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fichierDTO)))
                .andExpect(status().isBadRequest());

        List<Fichier> fichiers = fichierRepository.findAll();
        assertThat(fichiers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFichiers() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichiers
        restFichierMockMvc.perform(get("/api/fichiers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fichier.getId().intValue())))
                .andExpect(jsonPath("$.[*].nomFichier").value(hasItem(DEFAULT_NOM_FICHIER.toString())))
                .andExpect(jsonPath("$.[*].chemin").value(hasItem(DEFAULT_CHEMIN.toString())));
    }

    @Test
    @Transactional
    public void getFichier() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get the fichier
        restFichierMockMvc.perform(get("/api/fichiers/{id}", fichier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fichier.getId().intValue()))
            .andExpect(jsonPath("$.nomFichier").value(DEFAULT_NOM_FICHIER.toString()))
            .andExpect(jsonPath("$.chemin").value(DEFAULT_CHEMIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFichier() throws Exception {
        // Get the fichier
        restFichierMockMvc.perform(get("/api/fichiers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFichier() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);
        int databaseSizeBeforeUpdate = fichierRepository.findAll().size();

        // Update the fichier
        Fichier updatedFichier = new Fichier();
        updatedFichier.setId(fichier.getId());
        updatedFichier.setNomFichier(UPDATED_NOM_FICHIER);
        updatedFichier.setChemin(UPDATED_CHEMIN);
        FichierDTO fichierDTO = fichierMapper.fichierToFichierDTO(updatedFichier);

        restFichierMockMvc.perform(put("/api/fichiers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fichierDTO)))
                .andExpect(status().isOk());

        // Validate the Fichier in the database
        List<Fichier> fichiers = fichierRepository.findAll();
        assertThat(fichiers).hasSize(databaseSizeBeforeUpdate);
        Fichier testFichier = fichiers.get(fichiers.size() - 1);
        assertThat(testFichier.getNomFichier()).isEqualTo(UPDATED_NOM_FICHIER);
        assertThat(testFichier.getChemin()).isEqualTo(UPDATED_CHEMIN);
    }

    @Test
    @Transactional
    public void deleteFichier() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);
        int databaseSizeBeforeDelete = fichierRepository.findAll().size();

        // Get the fichier
        restFichierMockMvc.perform(delete("/api/fichiers/{id}", fichier.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fichier> fichiers = fichierRepository.findAll();
        assertThat(fichiers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
