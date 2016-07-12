package com.consulsen.etatcivil.repository;

import com.consulsen.etatcivil.domain.PieceJustificatif;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PieceJustificatif entity.
 */
@SuppressWarnings("unused")
public interface PieceJustificatifRepository extends JpaRepository<PieceJustificatif,Long> {

}
