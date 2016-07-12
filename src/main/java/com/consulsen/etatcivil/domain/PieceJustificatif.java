package com.consulsen.etatcivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PieceJustificatif.
 */
@Entity
@Table(name = "piece_justificatif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PieceJustificatif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nom_fichier", nullable = false)
    private String nomFichier;

    @NotNull
    @Column(name = "chemin", nullable = false)
    private String chemin;

    @OneToMany(mappedBy = "identifiantPieceJustificatif")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeclarationNaissance> declarationNaissances = new HashSet<>();

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

    public Set<DeclarationNaissance> getDeclarationNaissances() {
        return declarationNaissances;
    }

    public void setDeclarationNaissances(Set<DeclarationNaissance> declarationNaissances) {
        this.declarationNaissances = declarationNaissances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieceJustificatif pieceJustificatif = (PieceJustificatif) o;
        if(pieceJustificatif.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pieceJustificatif.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PieceJustificatif{" +
            "id=" + id +
            ", nomFichier='" + nomFichier + "'" +
            ", chemin='" + chemin + "'" +
            '}';
    }
}
