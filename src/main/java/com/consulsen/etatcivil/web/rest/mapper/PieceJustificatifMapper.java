package com.consulsen.etatcivil.web.rest.mapper;

import com.consulsen.etatcivil.domain.*;
import com.consulsen.etatcivil.web.rest.dto.PieceJustificatifDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PieceJustificatif and its DTO PieceJustificatifDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PieceJustificatifMapper {

    PieceJustificatifDTO pieceJustificatifToPieceJustificatifDTO(PieceJustificatif pieceJustificatif);

    List<PieceJustificatifDTO> pieceJustificatifsToPieceJustificatifDTOs(List<PieceJustificatif> pieceJustificatifs);

    @Mapping(target = "declarationNaissances", ignore = true)
    PieceJustificatif pieceJustificatifDTOToPieceJustificatif(PieceJustificatifDTO pieceJustificatifDTO);

    List<PieceJustificatif> pieceJustificatifDTOsToPieceJustificatifs(List<PieceJustificatifDTO> pieceJustificatifDTOs);
}
