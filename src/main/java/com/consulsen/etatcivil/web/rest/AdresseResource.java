package com.consulsen.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulsen.etatcivil.domain.Adresse;
import com.consulsen.etatcivil.service.AdresseService;
import com.consulsen.etatcivil.web.rest.util.HeaderUtil;
import com.consulsen.etatcivil.web.rest.dto.AdresseDTO;
import com.consulsen.etatcivil.web.rest.mapper.AdresseMapper;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Adresse.
 */
@RestController
@RequestMapping("/api")
public class AdresseResource {

    private final Logger log = LoggerFactory.getLogger(AdresseResource.class);
        
    @Inject
    private AdresseService adresseService;
    
    @Inject
    private AdresseMapper adresseMapper;
    
    /**
     * POST  /adresses : Create a new adresse.
     *
     * @param adresseDTO the adresseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adresseDTO, or with status 400 (Bad Request) if the adresse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/adresses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdresseDTO> createAdresse(@Valid @RequestBody AdresseDTO adresseDTO) throws URISyntaxException {
        log.debug("REST request to save Adresse : {}", adresseDTO);
        if (adresseDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("adresse", "idexists", "A new adresse cannot already have an ID")).body(null);
        }
        AdresseDTO result = adresseService.save(adresseDTO);
        return ResponseEntity.created(new URI("/api/adresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("adresse", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adresses : Updates an existing adresse.
     *
     * @param adresseDTO the adresseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adresseDTO,
     * or with status 400 (Bad Request) if the adresseDTO is not valid,
     * or with status 500 (Internal Server Error) if the adresseDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/adresses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdresseDTO> updateAdresse(@Valid @RequestBody AdresseDTO adresseDTO) throws URISyntaxException {
        log.debug("REST request to update Adresse : {}", adresseDTO);
        if (adresseDTO.getId() == null) {
            return createAdresse(adresseDTO);
        }
        AdresseDTO result = adresseService.save(adresseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("adresse", adresseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adresses : get all the adresses.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of adresses in body
     */
    @RequestMapping(value = "/adresses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AdresseDTO> getAllAdresses(@RequestParam(required = false) String filter) {
        if ("identifiantpersonne-is-null".equals(filter)) {
            log.debug("REST request to get all Adresses where identifiantPersonne is null");
            return adresseService.findAllWhereIdentifiantPersonneIsNull();
        }
        log.debug("REST request to get all Adresses");
        return adresseService.findAll();
    }

    /**
     * GET  /adresses/:id : get the "id" adresse.
     *
     * @param id the id of the adresseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adresseDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/adresses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdresseDTO> getAdresse(@PathVariable Long id) {
        log.debug("REST request to get Adresse : {}", id);
        AdresseDTO adresseDTO = adresseService.findOne(id);
        return Optional.ofNullable(adresseDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /adresses/:id : delete the "id" adresse.
     *
     * @param id the id of the adresseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/adresses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAdresse(@PathVariable Long id) {
        log.debug("REST request to delete Adresse : {}", id);
        adresseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("adresse", id.toString())).build();
    }

}
