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


    private Long identifiantPersonneId;
    
    private Long identifiantPieceJustificatifId;
    
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

    public Long getIdentifiantPersonneId() {
        return identifiantPersonneId;
    }

    public void setIdentifiantPersonneId(Long personneId) {
        this.identifiantPersonneId = personneId;
    }

    public Long getIdentifiantPieceJustificatifId() {
        return identifiantPieceJustificatifId;
    }

    public void setIdentifiantPieceJustificatifId(Long pieceJustificatifId) {
        this.identifiantPieceJustificatifId = pieceJustificatifId;
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
            '}';
    }
}
