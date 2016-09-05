package com.consulsen.etatcivil.service;

import com.consulsen.etatcivil.domain.DeclarationNaissance;
import com.consulsen.etatcivil.repository.DeclarationNaissanceRepository;
import com.consulsen.etatcivil.web.rest.dto.AdresseDTO;
import com.consulsen.etatcivil.web.rest.dto.DeclarationNaissanceDTO;
import com.consulsen.etatcivil.web.rest.dto.FichierDTO;
import com.consulsen.etatcivil.web.rest.dto.PersonneDTO;
import com.consulsen.etatcivil.web.rest.mapper.DeclarationNaissanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DeclarationNaissance.
 */
@Service
@Transactional
public class DeclarationNaissanceService {

    private final Logger log = LoggerFactory.getLogger(DeclarationNaissanceService.class);

    @Inject
    private DeclarationNaissanceRepository declarationNaissanceRepository;

    @Inject
    private DeclarationNaissanceMapper declarationNaissanceMapper;

    /**
     * Save a declarationNaissance.
     *
     * @param declarationNaissanceDTO the entity to save
     * @return the persisted entity
     */
    public DeclarationNaissanceDTO save(DeclarationNaissanceDTO declarationNaissanceDTO) {
        log.debug("Request to save DeclarationNaissance : {}", declarationNaissanceDTO);
        File file = new File(String.valueOf(declarationNaissanceDTO.getFichier()));
        DeclarationNaissance declarationNaissance = declarationNaissanceMapper.declarationNaissanceDTOToDeclarationNaissance(declarationNaissanceDTO);
        declarationNaissance = declarationNaissanceRepository.save(declarationNaissance);
        DeclarationNaissanceDTO result = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);

        return result;
    }





    /**
     *  Get all the declarationNaissances.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DeclarationNaissanceDTO> findAll() {
        log.debug("Request to get all DeclarationNaissances");
          List<DeclarationNaissanceDTO> result = declarationNaissanceRepository.findAll().stream()
            .map(declarationNaissanceMapper::declarationNaissanceToDeclarationNaissanceDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one declarationNaissance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DeclarationNaissanceDTO findOne(Long id) {
        log.debug("Request to get DeclarationNaissance : {}", id);
        DeclarationNaissance declarationNaissance = declarationNaissanceRepository.findOne(id);
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);
        return declarationNaissanceDTO;
    }

    /**
     *  Delete the  declarationNaissance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DeclarationNaissance : {}", id);
        declarationNaissanceRepository.delete(id);
    }
}
