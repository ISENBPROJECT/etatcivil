package com.consulsen.etatcivil.web.rest;

import com.consulsen.etatcivil.EtatcivilApp;
import com.consulsen.etatcivil.domain.Adresse;
import com.consulsen.etatcivil.repository.AdresseRepository;
import com.consulsen.etatcivil.service.AdresseService;
import com.consulsen.etatcivil.web.rest.dto.AdresseDTO;
import com.consulsen.etatcivil.web.rest.mapper.AdresseMapper;

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
 * Test class for the AdresseResource REST controller.
 *
 * @see AdresseResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EtatcivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class AdresseResourceIntTest {


    private static final Long DEFAULT_CODE_POSTALE = 1L;
    private static final Long UPDATED_CODE_POSTALE = 2L;
    private static final String DEFAULT_VILLE = "AAAAA";
    private static final String UPDATED_VILLE = "BBBBB";
    private static final String DEFAULT_ADRESSE_COMPLEMENTAIRE = "AAAAA";
    private static final String UPDATED_ADRESSE_COMPLEMENTAIRE = "BBBBB";

    @Inject
    private AdresseRepository adresseRepository;

    @Inject
    private AdresseMapper adresseMapper;

    @Inject
    private AdresseService adresseService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdresseResource adresseResource = new AdresseResource();
        ReflectionTestUtils.setField(adresseResource, "adresseService", adresseService);
        ReflectionTestUtils.setField(adresseResource, "adresseMapper", adresseMapper);
        this.restAdresseMockMvc = MockMvcBuilders.standaloneSetup(adresseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        adresse = new Adresse();
        adresse.setCodePostale(DEFAULT_CODE_POSTALE);
        adresse.setVille(DEFAULT_VILLE);
        adresse.setAdresseComplementaire(DEFAULT_ADRESSE_COMPLEMENTAIRE);
    }

    @Test
    @Transactional
    public void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.adresseToAdresseDTO(adresse);

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
                .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresses.get(adresses.size() - 1);
        assertThat(testAdresse.getCodePostale()).isEqualTo(DEFAULT_CODE_POSTALE);
        assertThat(testAdresse.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdresse.getAdresseComplementaire()).isEqualTo(DEFAULT_ADRESSE_COMPLEMENTAIRE);
    }

    @Test
    @Transactional
    public void checkCodePostaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setCodePostale(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.adresseToAdresseDTO(adresse);

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
                .andExpect(status().isBadRequest());

        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setVille(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.adresseToAdresseDTO(adresse);

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
                .andExpect(status().isBadRequest());

        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresses
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
                .andExpect(jsonPath("$.[*].codePostale").value(hasItem(DEFAULT_CODE_POSTALE.intValue())))
                .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
                .andExpect(jsonPath("$.[*].adresseComplementaire").value(hasItem(DEFAULT_ADRESSE_COMPLEMENTAIRE.toString())));
    }

    @Test
    @Transactional
    public void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.codePostale").value(DEFAULT_CODE_POSTALE.intValue()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.adresseComplementaire").value(DEFAULT_ADRESSE_COMPLEMENTAIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = new Adresse();
        updatedAdresse.setId(adresse.getId());
        updatedAdresse.setCodePostale(UPDATED_CODE_POSTALE);
        updatedAdresse.setVille(UPDATED_VILLE);
        updatedAdresse.setAdresseComplementaire(UPDATED_ADRESSE_COMPLEMENTAIRE);
        AdresseDTO adresseDTO = adresseMapper.adresseToAdresseDTO(updatedAdresse);

        restAdresseMockMvc.perform(put("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
                .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresses.get(adresses.size() - 1);
        assertThat(testAdresse.getCodePostale()).isEqualTo(UPDATED_CODE_POSTALE);
        assertThat(testAdresse.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresse.getAdresseComplementaire()).isEqualTo(UPDATED_ADRESSE_COMPLEMENTAIRE);
    }

    @Test
    @Transactional
    public void deleteAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Get the adresse
        restAdresseMockMvc.perform(delete("/api/adresses/{id}", adresse.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
