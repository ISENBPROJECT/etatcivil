package com.consulsen.etatcivil.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consulsen.etatcivil.domain.DeclarationNaissance;
import com.consulsen.etatcivil.repository.DeclarationNaissanceRepository;
import com.consulsen.etatcivil.web.rest.dto.DeclarationNaissanceDTO;
import com.consulsen.etatcivil.web.rest.mapper.DeclarationNaissanceMapper;

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
    
    private static String path_source = "C:\\Users\\Serigne26\\Pictures\\";
	private static String path_destination = "D:\\Outils\\";


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
    
    /**
     *  Get declarationNaissance by criteria.
     *
     *  @param personneDTO
 	 *	@param declarationNaissanceDTO
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public List<DeclarationNaissanceDTO> findByCriteria(DeclarationNaissanceDTO declarationNaissanceDTO) {
    	 log.debug("Request to get DeclarationNaissances by criteria");
    	 String nom = "%" + declarationNaissanceDTO.getInformationEnfant().getNom() +"%";
    	 String prenom = "%" + declarationNaissanceDTO.getInformationEnfant().getPrenom() +"%";
    	 
         List<DeclarationNaissanceDTO> result = declarationNaissanceRepository.findByCriteria(declarationNaissanceDTO.getId(), nom,
        		 prenom, declarationNaissanceDTO.getInformationEnfant().getDateNaissance()).stream()
           .map(declarationNaissanceMapper::declarationNaissanceToDeclarationNaissanceDTO)
           .collect(Collectors.toCollection(LinkedList::new));
       return result;
    }
    
    
    /**
     * @param file
     */
    public void copyFileToPJDirectory(String file) { log.debug("Copie file to the directory pj");
    // String file = "PV.pdf";
	Path source = Paths.get(path_source + file);
   	 Path destination = Paths.get(path_destination + file);
   	 try {
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
