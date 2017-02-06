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
 * A Fichier.
 */
@Entity
@Table(name = "fichier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fichier implements Serializable {

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

    @ManyToOne (cascade = {CascadeType.ALL})
    @JoinColumn(name="identifiant_declaration_id")
    private DeclarationNaissance declarationNaissance;

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

    public DeclarationNaissance getDeclarationNaissance() {
        return declarationNaissance;
    }

    public void setDeclarationNaissance(DeclarationNaissance declarationNaissance) {
        this.declarationNaissance = declarationNaissance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fichier fichier = (Fichier) o;
        if(fichier.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fichier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fichier{" +
            "id=" + id +
            ", nomFichier='" + nomFichier + '\'' +
            ", chemin='" + chemin + '\'' +
            ", declarationNaissance=" + declarationNaissance +
            '}';
    }
}
