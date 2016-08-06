package com.consulsen.etatcivil.web.rest.mapper;

import com.consulsen.etatcivil.domain.*;
import com.consulsen.etatcivil.web.rest.dto.DeclarationNaissanceDTO;
import com.consulsen.etatcivil.web.rest.dto.PersonneDTO;

import org.mapstruct.*;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

/**
 * Mapper for the entity DeclarationNaissance and its DTO DeclarationNaissanceDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonneMapper.class,FichierMapper.class})


public interface DeclarationNaissanceMapper {

 
    @Mapping(source = "identifiantEnfant", target = "informationEnfant")
    @Mapping(source = "identifiantPere", target = "informationPere")
    @Mapping(source = "identifiantMere", target = "informationMere")
    @Mapping(source = "identifiantFichier", target = "fichier")
    DeclarationNaissanceDTO declarationNaissanceToDeclarationNaissanceDTO(DeclarationNaissance declarationNaissance);

    //List<DeclarationNaissanceDTO> declarationNaissancesToDeclarationNaissanceDTOs(List<DeclarationNaissance> declarationNaissances);
  
    @Mapping(source = "informationEnfant", target = "identifiantEnfant")
    @Mapping(source = "informationPere", target = "identifiantPere")
    @Mapping(source = "informationMere", target = "identifiantMere")
    @Mapping(source = "fichier", target = "identifiantFichier")

    DeclarationNaissance declarationNaissanceDTOToDeclarationNaissance(DeclarationNaissanceDTO declarationNaissanceDTO);

    //List<DeclarationNaissance> declarationNaissanceDTOsToDeclarationNaissances(List<DeclarationNaissanceDTO> declarationNaissanceDTOs);

    default Personne personneFromId(Long id) {
        if (id == null) {
            return null;
        }
        Personne personne = new Personne();
        personne.setId(id);
        return personne;
    }

    default Fichier fichierFromId(Long id) {
        if (id == null) {
            return null;
        }
        Fichier fichier = new Fichier();
        fichier.setId(id);
        return fichier;
    }
}
