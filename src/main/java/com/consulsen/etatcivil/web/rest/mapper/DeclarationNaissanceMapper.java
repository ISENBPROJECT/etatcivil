package com.consulsen.etatcivil.web.rest.mapper;

import com.consulsen.etatcivil.domain.*;
import com.consulsen.etatcivil.web.rest.dto.DeclarationNaissanceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DeclarationNaissance and its DTO DeclarationNaissanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeclarationNaissanceMapper {

    @Mapping(source = "identifiantPersonne.id", target = "identifiantPersonneId")
    @Mapping(source = "identifiantFichier.id", target = "identifiantFichierId")
    DeclarationNaissanceDTO declarationNaissanceToDeclarationNaissanceDTO(DeclarationNaissance declarationNaissance);

    List<DeclarationNaissanceDTO> declarationNaissancesToDeclarationNaissanceDTOs(List<DeclarationNaissance> declarationNaissances);

    @Mapping(source = "identifiantPersonneId", target = "identifiantPersonne")
    @Mapping(source = "identifiantFichierId", target = "identifiantFichier")
    DeclarationNaissance declarationNaissanceDTOToDeclarationNaissance(DeclarationNaissanceDTO declarationNaissanceDTO);

    List<DeclarationNaissance> declarationNaissanceDTOsToDeclarationNaissances(List<DeclarationNaissanceDTO> declarationNaissanceDTOs);

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
