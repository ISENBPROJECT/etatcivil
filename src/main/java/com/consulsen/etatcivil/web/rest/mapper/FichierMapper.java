package com.consulsen.etatcivil.web.rest.mapper;

import com.consulsen.etatcivil.domain.*;
import com.consulsen.etatcivil.web.rest.dto.FichierDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Fichier and its DTO FichierDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FichierMapper {

    FichierDTO fichierToFichierDTO(Fichier fichier);

    List<FichierDTO> fichiersToFichierDTOs(List<Fichier> fichiers);

    @Mapping(target = "declarationNaissances", ignore = true)
    Fichier fichierDTOToFichier(FichierDTO fichierDTO);

    List<Fichier> fichierDTOsToFichiers(List<FichierDTO> fichierDTOs);
}
