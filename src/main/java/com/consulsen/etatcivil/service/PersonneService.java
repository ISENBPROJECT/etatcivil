package com.consulsen.etatcivil.service;

import com.consulsen.etatcivil.domain.Personne;
import com.consulsen.etatcivil.repository.PersonneRepository;
import com.consulsen.etatcivil.web.rest.dto.PersonneDTO;
import com.consulsen.etatcivil.web.rest.mapper.PersonneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Personne.
 */
@Service
@Transactional
public class PersonneService {

    private final Logger log = LoggerFactory.getLogger(PersonneService.class);
    
    @Inject
    private PersonneRepository personneRepository;
    
    @Inject
    private PersonneMapper personneMapper;
    
    /**
     * Save a personne.
     * 
     * @param personneDTO the entity to save
     * @return the persisted entity
     */
    public PersonneDTO save(PersonneDTO personneDTO) {
        log.debug("Request to save Personne : {}", personneDTO);
        Personne personne = personneMapper.personneDTOToPersonne(personneDTO);
        personne = personneRepository.save(personne);
        PersonneDTO result = personneMapper.personneToPersonneDTO(personne);
        return result;
    }

    /**
     *  Get all the personnes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PersonneDTO> findAll() {
        log.debug("Request to get all Personnes");
        List<PersonneDTO> result = personneRepository.findAll().stream()
            .map(personneMapper::personneToPersonneDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one personne by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonneDTO findOne(Long id) {
        log.debug("Request to get Personne : {}", id);
        Personne personne = personneRepository.findOne(id);
        PersonneDTO personneDTO = personneMapper.personneToPersonneDTO(personne);
        return personneDTO;
    }

    /**
     *  Delete the  personne by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.delete(id);
    }
}
