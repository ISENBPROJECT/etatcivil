package com.consulsen.etatcivil.repository;

import com.consulsen.etatcivil.domain.DeclarationNaissance;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the DeclarationNaissance entity.
 */
public interface DeclarationNaissanceRepository extends JpaRepository<DeclarationNaissance,Long> {

	 public final static String FIND_DECLARATION_NAISSANCE_BY_CRITERIA = "SELECT d " + 
             "FROM DeclarationNaissance d JOIN FETCH d.identifiantEnfant e " +
             "WHERE e.nom like :nom and e.prenom like :prenom " + 
             "and (d.id = :numeroRegistre or d.id is not null )" +
             "and (e.dateNaissance = :dateNaissance or e.dateNaissance is not null )";

@Query(FIND_DECLARATION_NAISSANCE_BY_CRITERIA)
public List<DeclarationNaissance> findByCriteria(@Param("numeroRegistre") Long numeroRegistre, @Param("nom") String nom,
		@Param("prenom") String prenom, @Param("dateNaissance") LocalDate dateNaissance);
}
