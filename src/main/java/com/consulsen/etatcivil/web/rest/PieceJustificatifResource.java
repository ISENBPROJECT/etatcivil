package com.consulsen.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulsen.etatcivil.domain.PieceJustificatif;
import com.consulsen.etatcivil.service.PieceJustificatifService;
import com.consulsen.etatcivil.web.rest.util.HeaderUtil;
import com.consulsen.etatcivil.web.rest.dto.PieceJustificatifDTO;
import com.consulsen.etatcivil.web.rest.mapper.PieceJustificatifMapper;
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
 * REST controller for managing PieceJustificatif.
 */
@RestController
@RequestMapping("/api")
public class PieceJustificatifResource {

    private final Logger log = LoggerFactory.getLogger(PieceJustificatifResource.class);
        
    @Inject
    private PieceJustificatifService pieceJustificatifService;
    
    @Inject
    private PieceJustificatifMapper pieceJustificatifMapper;
    
    /**
     * POST  /piece-justificatifs : Create a new pieceJustificatif.
     *
     * @param pieceJustificatifDTO the pieceJustificatifDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pieceJustificatifDTO, or with status 400 (Bad Request) if the pieceJustificatif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/piece-justificatifs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PieceJustificatifDTO> createPieceJustificatif(@Valid @RequestBody PieceJustificatifDTO pieceJustificatifDTO) throws URISyntaxException {
        log.debug("REST request to save PieceJustificatif : {}", pieceJustificatifDTO);
        if (pieceJustificatifDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pieceJustificatif", "idexists", "A new pieceJustificatif cannot already have an ID")).body(null);
        }
        PieceJustificatifDTO result = pieceJustificatifService.save(pieceJustificatifDTO);
        return ResponseEntity.created(new URI("/api/piece-justificatifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pieceJustificatif", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /piece-justificatifs : Updates an existing pieceJustificatif.
     *
     * @param pieceJustificatifDTO the pieceJustificatifDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pieceJustificatifDTO,
     * or with status 400 (Bad Request) if the pieceJustificatifDTO is not valid,
     * or with status 500 (Internal Server Error) if the pieceJustificatifDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/piece-justificatifs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PieceJustificatifDTO> updatePieceJustificatif(@Valid @RequestBody PieceJustificatifDTO pieceJustificatifDTO) throws URISyntaxException {
        log.debug("REST request to update PieceJustificatif : {}", pieceJustificatifDTO);
        if (pieceJustificatifDTO.getId() == null) {
            return createPieceJustificatif(pieceJustificatifDTO);
        }
        PieceJustificatifDTO result = pieceJustificatifService.save(pieceJustificatifDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pieceJustificatif", pieceJustificatifDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /piece-justificatifs : get all the pieceJustificatifs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pieceJustificatifs in body
     */
    @RequestMapping(value = "/piece-justificatifs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PieceJustificatifDTO> getAllPieceJustificatifs() {
        log.debug("REST request to get all PieceJustificatifs");
        return pieceJustificatifService.findAll();
    }

    /**
     * GET  /piece-justificatifs/:id : get the "id" pieceJustificatif.
     *
     * @param id the id of the pieceJustificatifDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pieceJustificatifDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/piece-justificatifs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PieceJustificatifDTO> getPieceJustificatif(@PathVariable Long id) {
        log.debug("REST request to get PieceJustificatif : {}", id);
        PieceJustificatifDTO pieceJustificatifDTO = pieceJustificatifService.findOne(id);
        return Optional.ofNullable(pieceJustificatifDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /piece-justificatifs/:id : delete the "id" pieceJustificatif.
     *
     * @param id the id of the pieceJustificatifDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/piece-justificatifs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePieceJustificatif(@PathVariable Long id) {
        log.debug("REST request to delete PieceJustificatif : {}", id);
        pieceJustificatifService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pieceJustificatif", id.toString())).build();
    }

}
