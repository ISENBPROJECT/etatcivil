package com.consulsen.etatcivil.service;

import com.consulsen.etatcivil.domain.Adresse;
import com.consulsen.etatcivil.repository.AdresseRepository;
import com.consulsen.etatcivil.web.rest.dto.AdresseDTO;
import com.consulsen.etatcivil.web.rest.mapper.AdresseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Adresse.
 */
@Service
@Transactional
public class AdresseService {

    private final Logger log = LoggerFactory.getLogger(AdresseService.class);
    
    @Inject
    private AdresseRepository adresseRepository;
    
    @Inject
    private AdresseMapper adresseMapper;
    
    /**
     * Save a adresse.
     * 
     * @param adresseDTO the entity to save
     * @return the persisted entity
     */
    public AdresseDTO save(AdresseDTO adresseDTO) {
        log.debug("Request to save Adresse : {}", adresseDTO);
        Adresse adresse = adresseMapper.adresseDTOToAdresse(adresseDTO);
        adresse = adresseRepository.save(adresse);
        AdresseDTO result = adresseMapper.adresseToAdresseDTO(adresse);
        return result;
    }

    /**
     *  Get all the adresses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AdresseDTO> findAll() {
        log.debug("Request to get all Adresses");
        List<AdresseDTO> result = adresseRepository.findAll().stream()
            .map(adresseMapper::adresseToAdresseDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }


    /**
     *  get all the adresses where IdentifiantPersonne is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AdresseDTO> findAllWhereIdentifiantPersonneIsNull() {
        log.debug("Request to get all adresses where IdentifiantPersonne is null");
        return StreamSupport
            .stream(adresseRepository.findAll().spliterator(), false)
            .filter(adresse -> adresse.getIdentifiantPersonne() == null)
            .map(adresseMapper::adresseToAdresseDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one adresse by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AdresseDTO findOne(Long id) {
        log.debug("Request to get Adresse : {}", id);
        Adresse adresse = adresseRepository.findOne(id);
        AdresseDTO adresseDTO = adresseMapper.adresseToAdresseDTO(adresse);
        return adresseDTO;
    }

    /**
     *  Delete the  adresse by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Adresse : {}", id);
        adresseRepository.delete(id);
    }
}
