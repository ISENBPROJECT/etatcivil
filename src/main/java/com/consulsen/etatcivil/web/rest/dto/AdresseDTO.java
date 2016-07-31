package com.consulsen.etatcivil.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Adresse entity.
 */
public class AdresseDTO implements Serializable {

    private Long id;

    @NotNull
    private Long codePostale;

    @NotNull
    private String ville;

    private String adresseComplementaire;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(Long codePostale) {
        this.codePostale = codePostale;
    }
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getAdresseComplementaire() {
        return adresseComplementaire;
    }

    public void setAdresseComplementaire(String adresseComplementaire) {
        this.adresseComplementaire = adresseComplementaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdresseDTO adresseDTO = (AdresseDTO) o;

        if ( ! Objects.equals(id, adresseDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AdresseDTO{" +
            "id=" + id +
            ", codePostale='" + codePostale + "'" +
            ", ville='" + ville + "'" +
            ", adresseComplementaire='" + adresseComplementaire + "'" +
            '}';
    }
}
