package com.consulsen.etatcivil.repository;

import com.consulsen.etatcivil.domain.DeclarationNaissance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DeclarationNaissance entity.
 */
@SuppressWarnings("unused")
public interface DeclarationNaissanceRepository extends JpaRepository<DeclarationNaissance,Long> {

}
