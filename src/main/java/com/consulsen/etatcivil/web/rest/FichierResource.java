package com.consulsen.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulsen.etatcivil.domain.Fichier;
import com.consulsen.etatcivil.service.FichierService;
import com.consulsen.etatcivil.web.rest.util.HeaderUtil;
import com.consulsen.etatcivil.web.rest.dto.FichierDTO;
import com.consulsen.etatcivil.web.rest.mapper.FichierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Fichier.
 */
@RestController
@RequestMapping("/api")
public class FichierResource {

    private final Logger log = LoggerFactory.getLogger(FichierResource.class);
        
    @Inject
    private FichierService fichierService;
    
    @Inject
    private FichierMapper fichierMapper;
    
    /**
     * POST  /fichiers : Create a new fichier.
     *
     * @param fichierDTO the fichierDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fichierDTO, or with status 400 (Bad Request) if the fichier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/fichiers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FichierDTO> createFichier(@Valid @RequestBody FichierDTO fichierDTO) throws URISyntaxException {
        log.debug("REST request to save Fichier : {}", fichierDTO);
        if (fichierDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fichier", "idexists", "A new fichier cannot already have an ID")).body(null);
        }
        FichierDTO result = fichierService.save(fichierDTO);
        return ResponseEntity.created(new URI("/api/fichiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fichier", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fichiers : Updates an existing fichier.
     *
     * @param fichierDTO the fichierDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fichierDTO,
     * or with status 400 (Bad Request) if the fichierDTO is not valid,
     * or with status 500 (Internal Server Error) if the fichierDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/fichiers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FichierDTO> updateFichier(@Valid @RequestBody FichierDTO fichierDTO) throws URISyntaxException {
        log.debug("REST request to update Fichier : {}", fichierDTO);
        if (fichierDTO.getId() == null) {
            return createFichier(fichierDTO);
        }
        FichierDTO result = fichierService.save(fichierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fichier", fichierDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fichiers : get all the fichiers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fichiers in body
     */
    @RequestMapping(value = "/fichiers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FichierDTO> getAllFichiers() {
        log.debug("REST request to get all Fichiers");
        return fichierService.findAll();
    }

    /**
     * GET  /fichiers/:id : get the "id" fichier.
     *
     * @param id the id of the fichierDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fichierDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/fichiers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FichierDTO> getFichier(@PathVariable Long id) {
        log.debug("REST request to get Fichier : {}", id);
        FichierDTO fichierDTO = fichierService.findOne(id);
        return Optional.ofNullable(fichierDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fichiers/:id : delete the "id" fichier.
     *
     * @param id the id of the fichierDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/fichiers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFichier(@PathVariable Long id) {
        log.debug("REST request to delete Fichier : {}", id);
        fichierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fichier", id.toString())).build();
    }

}
