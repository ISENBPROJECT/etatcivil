package com.consulsen.etatcivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DeclarationNaissance.
 */
@Entity
@Table(name = "declaration_naissance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeclarationNaissance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date_declaration", nullable = false)
    private LocalDate dateDeclaration;

    @NotNull
    @Column(name = "identifiant_enfant", nullable = false)
    private Long identifiantEnfant;

    @NotNull
    @Column(name = "identifiant_pere", nullable = false)
    private Long identifiantPere;

    @NotNull
    @Column(name = "identifiant_mere", nullable = false)
    private Long identifiantMere;

    @Column(name = "mention_marginale")
    private String mentionMarginale;

    @ManyToOne
    private Personne identifiantPersonne;

    @ManyToOne
    private PieceJustificatif identifiantPieceJustificatif;

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

    public Personne getIdentifiantPersonne() {
        return identifiantPersonne;
    }

    public void setIdentifiantPersonne(Personne personne) {
        this.identifiantPersonne = personne;
    }

    public PieceJustificatif getIdentifiantPieceJustificatif() {
        return identifiantPieceJustificatif;
    }

    public void setIdentifiantPieceJustificatif(PieceJustificatif pieceJustificatif) {
        this.identifiantPieceJustificatif = pieceJustificatif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeclarationNaissance declarationNaissance = (DeclarationNaissance) o;
        if(declarationNaissance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, declarationNaissance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeclarationNaissance{" +
            "id=" + id +
            ", dateDeclaration='" + dateDeclaration + "'" +
            ", identifiantEnfant='" + identifiantEnfant + "'" +
            ", identifiantPere='" + identifiantPere + "'" +
            ", identifiantMere='" + identifiantMere + "'" +
            ", mentionMarginale='" + mentionMarginale + "'" +
            '}';
    }
}
