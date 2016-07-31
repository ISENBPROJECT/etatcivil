package com.consulsen.etatcivil.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the DeclarationNaissance entity.
 */
public class DeclarationNaissanceDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateDeclaration;

    @NotNull
    private Long identifiantEnfant;

    @NotNull
    private Long identifiantPere;

    @NotNull
    private Long identifiantMere;

    private String mentionMarginale;

    private Long numeroCarteIdentite;

    private Long numeroPassPort;


    private Long identifiantPersonneId;
    
    private Long identifiantFichierId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(LocalDate dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }
    public Long getIdentifiantEnfant() {
        return identifiantEnfant;
    }

    public void setIdentifiantEnfant(Long identifiantEnfant) {
        this.identifiantEnfant = identifiantEnfant;
    }
    public Long getIdentifiantPere() {
        return identifiantPere;
    }

    public void setIdentifiantPere(Long identifiantPere) {
        this.identifiantPere = identifiantPere;
    }
    public Long getIdentifiantMere() {
        return identifiantMere;
    }

    public void setIdentifiantMere(Long identifiantMere) {
        this.identifiantMere = identifiantMere;
    }
    public String getMentionMarginale() {
        return mentionMarginale;
    }

    public void setMentionMarginale(String mentionMarginale) {
        this.mentionMarginale = mentionMarginale;
    }
    public Long getNumeroCarteIdentite() {
        return numeroCarteIdentite;
    }

    public void setNumeroCarteIdentite(Long numeroCarteIdentite) {
        this.numeroCarteIdentite = numeroCarteIdentite;
    }
    public Long getNumeroPassPort() {
        return numeroPassPort;
    }

    public void setNumeroPassPort(Long numeroPassPort) {
        this.numeroPassPort = numeroPassPort;
    }

    public Long getIdentifiantPersonneId() {
        return identifiantPersonneId;
    }

    public void setIdentifiantPersonneId(Long personneId) {
        this.identifiantPersonneId = personneId;
    }

    public Long getIdentifiantFichierId() {
        return identifiantFichierId;
    }

    public void setIdentifiantFichierId(Long fichierId) {
        this.identifiantFichierId = fichierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeclarationNaissanceDTO declarationNaissanceDTO = (DeclarationNaissanceDTO) o;

        if ( ! Objects.equals(id, declarationNaissanceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeclarationNaissanceDTO{" +
            "id=" + id +
            ", dateDeclaration='" + dateDeclaration + "'" +
            ", identifiantEnfant='" + identifiantEnfant + "'" +
            ", identifiantPere='" + identifiantPere + "'" +
            ", identifiantMere='" + identifiantMere + "'" +
            ", mentionMarginale='" + mentionMarginale + "'" +
            ", numeroCarteIdentite='" + numeroCarteIdentite + "'" +
            ", numeroPassPort='" + numeroPassPort + "'" +
            '}';
    }
}
