package com.consulsen.etatcivil.web.rest;

import com.consulsen.etatcivil.EtatcivilApp;
import com.consulsen.etatcivil.domain.PieceJustificatif;
import com.consulsen.etatcivil.repository.PieceJustificatifRepository;
import com.consulsen.etatcivil.service.PieceJustificatifService;
import com.consulsen.etatcivil.web.rest.dto.PieceJustificatifDTO;
import com.consulsen.etatcivil.web.rest.mapper.PieceJustificatifMapper;

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
 * Test class for the PieceJustificatifResource REST controller.
 *
 * @see PieceJustificatifResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EtatcivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class PieceJustificatifResourceIntTest {

    private static final String DEFAULT_NOM_FICHIER = "AAAAA";
    private static final String UPDATED_NOM_FICHIER = "BBBBB";
    private static final String DEFAULT_CHEMIN = "AAAAA";
    private static final String UPDATED_CHEMIN = "BBBBB";

    @Inject
    private PieceJustificatifRepository pieceJustificatifRepository;

    @Inject
    private PieceJustificatifMapper pieceJustificatifMapper;

    @Inject
    private PieceJustificatifService pieceJustificatifService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPieceJustificatifMockMvc;

    private PieceJustificatif pieceJustificatif;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PieceJustificatifResource pieceJustificatifResource = new PieceJustificatifResource();
        ReflectionTestUtils.setField(pieceJustificatifResource, "pieceJustificatifService", pieceJustificatifService);
        ReflectionTestUtils.setField(pieceJustificatifResource, "pieceJustificatifMapper", pieceJustificatifMapper);
        this.restPieceJustificatifMockMvc = MockMvcBuilders.standaloneSetup(pieceJustificatifResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pieceJustificatif = new PieceJustificatif();
        pieceJustificatif.setNomFichier(DEFAULT_NOM_FICHIER);
        pieceJustificatif.setChemin(DEFAULT_CHEMIN);
    }

    @Test
    @Transactional
    public void createPieceJustificatif() throws Exception {
        int databaseSizeBeforeCreate = pieceJustificatifRepository.findAll().size();

        // Create the PieceJustificatif
        PieceJustificatifDTO pieceJustificatifDTO = pieceJustificatifMapper.pieceJustificatifToPieceJustificatifDTO(pieceJustificatif);

        restPieceJustificatifMockMvc.perform(post("/api/piece-justificatifs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pieceJustificatifDTO)))
                .andExpect(status().isCreated());

        // Validate the PieceJustificatif in the database
        List<PieceJustificatif> pieceJustificatifs = pieceJustificatifRepository.findAll();
        assertThat(pieceJustificatifs).hasSize(databaseSizeBeforeCreate + 1);
        PieceJustificatif testPieceJustificatif = pieceJustificatifs.get(pieceJustificatifs.size() - 1);
        assertThat(testPieceJustificatif.getNomFichier()).isEqualTo(DEFAULT_NOM_FICHIER);
        assertThat(testPieceJustificatif.getChemin()).isEqualTo(DEFAULT_CHEMIN);
    }

    @Test
    @Transactional
    public void checkNomFichierIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJustificatifRepository.findAll().size();
        // set the field null
        pieceJustificatif.setNomFichier(null);

        // Create the PieceJustificatif, which fails.
        PieceJustificatifDTO pieceJustificatifDTO = pieceJustificatifMapper.pieceJustificatifToPieceJustificatifDTO(pieceJustificatif);

        restPieceJustificatifMockMvc.perform(post("/api/piece-justificatifs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pieceJustificatifDTO)))
                .andExpect(status().isBadRequest());

        List<PieceJustificatif> pieceJustificatifs = pieceJustificatifRepository.findAll();
        assertThat(pieceJustificatifs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheminIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJustificatifRepository.findAll().size();
        // set the field null
        pieceJustificatif.setChemin(null);

        // Create the PieceJustificatif, which fails.
        PieceJustificatifDTO pieceJustificatifDTO = pieceJustificatifMapper.pieceJustificatifToPieceJustificatifDTO(pieceJustificatif);

        restPieceJustificatifMockMvc.perform(post("/api/piece-justificatifs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pieceJustificatifDTO)))
                .andExpect(status().isBadRequest());

        List<PieceJustificatif> pieceJustificatifs = pieceJustificatifRepository.findAll();
        assertThat(pieceJustificatifs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieceJustificatifs() throws Exception {
        // Initialize the database
        pieceJustificatifRepository.saveAndFlush(pieceJustificatif);

        // Get all the pieceJustificatifs
        restPieceJustificatifMockMvc.perform(get("/api/piece-justificatifs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJustificatif.getId().intValue())))
                .andExpect(jsonPath("$.[*].nomFichier").value(hasItem(DEFAULT_NOM_FICHIER.toString())))
                .andExpect(jsonPath("$.[*].chemin").value(hasItem(DEFAULT_CHEMIN.toString())));
    }

    @Test
    @Transactional
    public void getPieceJustificatif() throws Exception {
        // Initialize the database
        pieceJustificatifRepository.saveAndFlush(pieceJustificatif);

        // Get the pieceJustificatif
        restPieceJustificatifMockMvc.perform(get("/api/piece-justificatifs/{id}", pieceJustificatif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pieceJustificatif.getId().intValue()))
            .andExpect(jsonPath("$.nomFichier").value(DEFAULT_NOM_FICHIER.toString()))
            .andExpect(jsonPath("$.chemin").value(DEFAULT_CHEMIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPieceJustificatif() throws Exception {
        // Get the pieceJustificatif
        restPieceJustificatifMockMvc.perform(get("/api/piece-justificatifs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePieceJustificatif() throws Exception {
        // Initialize the database
        pieceJustificatifRepository.saveAndFlush(pieceJustificatif);
        int databaseSizeBeforeUpdate = pieceJustificatifRepository.findAll().size();

        // Update the pieceJustificatif
        PieceJustificatif updatedPieceJustificatif = new PieceJustificatif();
        updatedPieceJustificatif.setId(pieceJustificatif.getId());
        updatedPieceJustificatif.setNomFichier(UPDATED_NOM_FICHIER);
        updatedPieceJustificatif.setChemin(UPDATED_CHEMIN);
        PieceJustificatifDTO pieceJustificatifDTO = pieceJustificatifMapper.pieceJustificatifToPieceJustificatifDTO(updatedPieceJustificatif);

        restPieceJustificatifMockMvc.perform(put("/api/piece-justificatifs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pieceJustificatifDTO)))
                .andExpect(status().isOk());

        // Validate the PieceJustificatif in the database
        List<PieceJustificatif> pieceJustificatifs = pieceJustificatifRepository.findAll();
        assertThat(pieceJustificatifs).hasSize(databaseSizeBeforeUpdate);
        PieceJustificatif testPieceJustificatif = pieceJustificatifs.get(pieceJustificatifs.size() - 1);
        assertThat(testPieceJustificatif.getNomFichier()).isEqualTo(UPDATED_NOM_FICHIER);
        assertThat(testPieceJustificatif.getChemin()).isEqualTo(UPDATED_CHEMIN);
    }

    @Test
    @Transactional
    public void deletePieceJustificatif() throws Exception {
        // Initialize the database
        pieceJustificatifRepository.saveAndFlush(pieceJustificatif);
        int databaseSizeBeforeDelete = pieceJustificatifRepository.findAll().size();

        // Get the pieceJustificatif
        restPieceJustificatifMockMvc.perform(delete("/api/piece-justificatifs/{id}", pieceJustificatif.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PieceJustificatif> pieceJustificatifs = pieceJustificatifRepository.findAll();
        assertThat(pieceJustificatifs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
