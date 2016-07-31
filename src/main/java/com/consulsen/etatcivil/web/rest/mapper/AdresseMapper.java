package com.consulsen.etatcivil.web.rest.mapper;

import com.consulsen.etatcivil.domain.*;
import com.consulsen.etatcivil.web.rest.dto.AdresseDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Adresse and its DTO AdresseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdresseMapper {

    AdresseDTO adresseToAdresseDTO(Adresse adresse);

    List<AdresseDTO> adressesToAdresseDTOs(List<Adresse> adresses);

    @Mapping(target = "identifiantPersonne", ignore = true)
    Adresse adresseDTOToAdresse(AdresseDTO adresseDTO);

    List<Adresse> adresseDTOsToAdresses(List<AdresseDTO> adresseDTOs);
}
