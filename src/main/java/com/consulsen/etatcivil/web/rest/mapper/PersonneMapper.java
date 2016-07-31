package com.consulsen.etatcivil.web.rest.mapper;

import com.consulsen.etatcivil.domain.*;
import com.consulsen.etatcivil.web.rest.dto.PersonneDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Personne and its DTO PersonneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonneMapper {

    @Mapping(source = "adresse.id", target = "adresseId")
    PersonneDTO personneToPersonneDTO(Personne personne);

    List<PersonneDTO> personnesToPersonneDTOs(List<Personne> personnes);

    @Mapping(source = "adresseId", target = "adresse")
    @Mapping(target = "declarationNaissances", ignore = true)
    Personne personneDTOToPersonne(PersonneDTO personneDTO);

    List<Personne> personneDTOsToPersonnes(List<PersonneDTO> personneDTOs);

    default Adresse adresseFromId(Long id) {
        if (id == null) {
            return null;
        }
        Adresse adresse = new Adresse();
        adresse.setId(id);
        return adresse;
    }
}
