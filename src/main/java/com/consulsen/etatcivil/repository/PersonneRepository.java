package com.consulsen.etatcivil.repository;

import com.consulsen.etatcivil.domain.Personne;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Personne entity.
 */
@SuppressWarnings("unused")
public interface PersonneRepository extends JpaRepository<Personne,Long> {

}
