package com.consulsen.etatcivil.service;

import com.consulsen.etatcivil.domain.PieceJustificatif;
import com.consulsen.etatcivil.repository.PieceJustificatifRepository;
import com.consulsen.etatcivil.web.rest.dto.PieceJustificatifDTO;
import com.consulsen.etatcivil.web.rest.mapper.PieceJustificatifMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PieceJustificatif.
 */
@Service
@Transactional
public class PieceJustificatifService {

    private final Logger log = LoggerFactory.getLogger(PieceJustificatifService.class);
    
    @Inject
    private PieceJustificatifRepository pieceJustificatifRepository;
    
    @Inject
    private PieceJustificatifMapper pieceJustificatifMapper;
    
    /**
     * Save a pieceJustificatif.
     * 
     * @param pieceJustificatifDTO the entity to save
     * @return the persisted entity
     */
    public PieceJustificatifDTO save(PieceJustificatifDTO pieceJustificatifDTO) {
        log.debug("Request to save PieceJustificatif : {}", pieceJustificatifDTO);
        PieceJustificatif pieceJustificatif = pieceJustificatifMapper.pieceJustificatifDTOToPieceJustificatif(pieceJustificatifDTO);
        pieceJustificatif = pieceJustificatifRepository.save(pieceJustificatif);
        PieceJustificatifDTO result = pieceJustificatifMapper.pieceJustificatifToPieceJustificatifDTO(pieceJustificatif);
        return result;
    }

    /**
     *  Get all the pieceJustificatifs.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PieceJustificatifDTO> findAll() {
        log.debug("Request to get all PieceJustificatifs");
        List<PieceJustificatifDTO> result = pieceJustificatifRepository.findAll().stream()
            .map(pieceJustificatifMapper::pieceJustificatifToPieceJustificatifDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one pieceJustificatif by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PieceJustificatifDTO findOne(Long id) {
        log.debug("Request to get PieceJustificatif : {}", id);
        PieceJustificatif pieceJustificatif = pieceJustificatifRepository.findOne(id);
        PieceJustificatifDTO pieceJustificatifDTO = pieceJustificatifMapper.pieceJustificatifToPieceJustificatifDTO(pieceJustificatif);
        return pieceJustificatifDTO;
    }

    /**
     *  Delete the  pieceJustificatif by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PieceJustificatif : {}", id);
        pieceJustificatifRepository.delete(id);
    }
}
