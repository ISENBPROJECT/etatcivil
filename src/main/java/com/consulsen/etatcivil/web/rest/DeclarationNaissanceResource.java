package com.consulsen.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulsen.etatcivil.domain.DeclarationNaissance;
import com.consulsen.etatcivil.service.DeclarationNaissanceService;
import com.consulsen.etatcivil.web.rest.util.HeaderUtil;
import com.consulsen.etatcivil.web.rest.dto.DeclarationNaissanceDTO;
import com.consulsen.etatcivil.web.rest.mapper.DeclarationNaissanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @RequestMapping(value = "/declaration-naissances-recherche",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeclarationNaissanceDTO> seacrhDeclarationNaissances(@RequestBody DeclarationNaissanceDTO declarationNaissanceDTO) {
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


    @RequestMapping(value = "/uploadFile",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<File> upload(@Valid @RequestBody File file) throws URISyntaxException {
       System.out.println("coucou");
        return null;
    }
}
