package com.consulsen.etatcivil.web.rest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDate;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.consulsen.etatcivil.service.DeclarationNaissanceService;
import com.consulsen.etatcivil.web.rest.dto.DeclarationNaissanceDTO;
import com.consulsen.etatcivil.web.rest.dto.PersonneDTO;
import com.consulsen.etatcivil.web.rest.mapper.DeclarationNaissanceMapper;
import com.consulsen.etatcivil.web.rest.util.HeaderUtil;

/**
 * REST controller for managing DeclarationNaissance.
 */
@RestController
@RequestMapping("/api")
public class DeclarationNaissanceResource {

    private final Logger log = LoggerFactory.getLogger(DeclarationNaissanceResource.class);

    @Inject
    private DeclarationNaissanceService declarationNaissanceService;

    @Inject
    private DeclarationNaissanceMapper declarationNaissanceMapper;

    private static String UNDEFINED = "undefined";
    private String [] datePattern = {"yyyy-MM-dd"};
    final org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MMM-dd");


    /**
     * POST  /declaration-naissances : Create a new declarationNaissance.
     *
     * @param declarationNaissanceDTO the declarationNaissanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new declarationNaissanceDTO, or with status 400 (Bad Request) if the declarationNaissance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/declaration-naissances",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationNaissanceDTO> createDeclarationNaissance(@Valid @RequestBody DeclarationNaissanceDTO declarationNaissanceDTO) throws URISyntaxException {
        log.debug("REST request to save DeclarationNaissance : {}", declarationNaissanceDTO);
        if (declarationNaissanceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("declarationNaissance", "idexists", "A new declarationNaissance cannot already have an ID")).body(null);
        }
        try {
            upload(declarationNaissanceDTO.getFichier().getNomFichier());
        } catch (IOException e) {
            e.printStackTrace();
        }
        DeclarationNaissanceDTO result = declarationNaissanceService.save(declarationNaissanceDTO);
        return ResponseEntity.created(new URI("/api/declaration-naissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("declarationNaissance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /declaration-naissances : Updates an existing declarationNaissance.
     *
     * @param declarationNaissanceDTO the declarationNaissanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated declarationNaissanceDTO,
     * or with status 400 (Bad Request) if the declarationNaissanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the declarationNaissanceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/declaration-naissances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationNaissanceDTO> updateDeclarationNaissance(@Valid @RequestBody DeclarationNaissanceDTO declarationNaissanceDTO) throws URISyntaxException {
        log.debug("REST request to update DeclarationNaissance : {}", declarationNaissanceDTO);
        if (declarationNaissanceDTO.getId() == null) {
            return createDeclarationNaissance(declarationNaissanceDTO);
        }
        DeclarationNaissanceDTO result = declarationNaissanceService.save(declarationNaissanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("declarationNaissance", declarationNaissanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /declaration-naissances : get all the declarationNaissances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of declarationNaissances in body
     */
    @RequestMapping(value = "/declaration-naissances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeclarationNaissanceDTO> getAllDeclarationNaissances() {
        log.debug("REST request to get all DeclarationNaissances");
        return declarationNaissanceService.findAll();
    }

    /**
     * GET  /declaration-naissances/:id : get the "id" declarationNaissance.
     *
     * @param id the id of the declarationNaissanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the declarationNaissanceDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/declaration-naissances/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeclarationNaissanceDTO> getDeclarationNaissance(@PathVariable Long id) {
        log.debug("REST request to get DeclarationNaissance : {}", id);
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceService.findOne(id);
        return Optional.ofNullable(declarationNaissanceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /declaration-naissances/:id : delete the "id" declarationNaissance.
     *
     * @param id the id of the declarationNaissanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/declaration-naissances/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeclarationNaissance(@PathVariable Long id) {
        log.debug("REST request to delete DeclarationNaissance : {}", id);
        declarationNaissanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("declarationNaissance", id.toString())).build();
    }

    @RequestMapping(value = "/declaration-naissances/{id}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeclarationNaissanceDTO> searchDeclarationNaissance(@Valid @RequestBody String data) {
        log.debug("REST request to search DeclarationNaissance");
        Long numeroRegistre;
        String nom;
        String prenom;
        LocalDate dateNaissance;
        data = StringUtils.substring(data, 0);
        String[] datasSearch = StringUtils.split(data,",");
        DeclarationNaissanceDTO declarationNaissanceDTO = new DeclarationNaissanceDTO();
        PersonneDTO personneDTO = new PersonneDTO();
        nom = datasSearch[1].startsWith(UNDEFINED) ? "": datasSearch[1];
        personneDTO.setNom(nom);
        prenom = datasSearch[2].startsWith(UNDEFINED) ? "": datasSearch[2];
        personneDTO.setPrenom(prenom);
		dateNaissance = datasSearch[3].startsWith(UNDEFINED) ? null: LocalDate.parse(datasSearch[3]);
		personneDTO.setDateNaissance(dateNaissance);
        declarationNaissanceDTO.setInformationEnfant(personneDTO);
        numeroRegistre = datasSearch[0].startsWith(UNDEFINED) ? null: Long.parseLong(datasSearch[0]);
        declarationNaissanceDTO.setId(numeroRegistre);
        return declarationNaissanceService.findByCriteria(declarationNaissanceDTO);
    }

    @RequestMapping(value = "/uploadFile",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public ResponseEntity<File> upload(@Valid @RequestBody String filename) throws URISyntaxException, IOException {
        URL url = new URL("src\\main\\webapp\\app\\document"); 

            File file = new File(URLDecoder.decode(url.getFile(),"UTF-8"));
            return  null;
        }
}
    
   
