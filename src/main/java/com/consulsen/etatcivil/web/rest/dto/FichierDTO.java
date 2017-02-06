package com.consulsen.etatcivil.web.rest.dto;

import com.consulsen.etatcivil.domain.DeclarationNaissance;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Fichier entity.
 */
public class FichierDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomFichier;

    @NotNull
    private String chemin;

    @NotNull
    private DeclarationNaissanceDTO declarationNaissance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FichierDTO fichierDTO = (FichierDTO) o;

        if (!Objects.equals(id, fichierDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public DeclarationNaissanceDTO getDeclarationNaissance() {
        return declarationNaissance;
    }

    public void setDeclarationNaissance(DeclarationNaissanceDTO declarationNaissance) {
        this.declarationNaissance = declarationNaissance;
    }

    @Override
    public String toString() {
        return "FichierDTO{" +
            "id=" + id +
            ", nomFichier='" + nomFichier + '\'' +
            ", chemin='" + chemin + '\'' +
            ", declarationNaissance=" + declarationNaissance +
            '}';
    }
}
