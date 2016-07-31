package com.consulsen.etatcivil.service;

import com.consulsen.etatcivil.domain.Fichier;
import com.consulsen.etatcivil.repository.FichierRepository;
import com.consulsen.etatcivil.web.rest.dto.FichierDTO;
import com.consulsen.etatcivil.web.rest.mapper.FichierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Fichier.
 */
@Service
@Transactional
public class FichierService {

    private final Logger log = LoggerFactory.getLogger(FichierService.class);
    
    @Inject
    private FichierRepository fichierRepository;
    
    @Inject
    private FichierMapper fichierMapper;
    
    /**
     * Save a fichier.
     * 
     * @param fichierDTO the entity to save
     * @return the persisted entity
     */
    public FichierDTO save(FichierDTO fichierDTO) {
        log.debug("Request to save Fichier : {}", fichierDTO);
        Fichier fichier = fichierMapper.fichierDTOToFichier(fichierDTO);
        fichier = fichierRepository.save(fichier);
        FichierDTO result = fichierMapper.fichierToFichierDTO(fichier);
        return result;
    }

    /**
     *  Get all the fichiers.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<FichierDTO> findAll() {
        log.debug("Request to get all Fichiers");
        List<FichierDTO> result = fichierRepository.findAll().stream()
            .map(fichierMapper::fichierToFichierDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one fichier by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FichierDTO findOne(Long id) {
        log.debug("Request to get Fichier : {}", id);
        Fichier fichier = fichierRepository.findOne(id);
        FichierDTO fichierDTO = fichierMapper.fichierToFichierDTO(fichier);
        return fichierDTO;
    }

    /**
     *  Delete the  fichier by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fichier : {}", id);
        fichierRepository.delete(id);
    }
}
