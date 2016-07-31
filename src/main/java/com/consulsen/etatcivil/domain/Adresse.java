package com.consulsen.etatcivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Adresse.
 */
@Entity
@Table(name = "adresse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code_postale", nullable = false)
    private Long codePostale;

    @NotNull
    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "adresse_complementaire")
    private String adresseComplementaire;

    @OneToOne(mappedBy = "adresse")
    @JsonIgnore
    private Personne identifiantPersonne;

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

    public Personne getIdentifiantPersonne() {
        return identifiantPersonne;
    }

    public void setIdentifiantPersonne(Personne personne) {
        this.identifiantPersonne = personne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Adresse adresse = (Adresse) o;
        if(adresse.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, adresse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Adresse{" +
            "id=" + id +
            ", codePostale='" + codePostale + "'" +
            ", ville='" + ville + "'" +
            ", adresseComplementaire='" + adresseComplementaire + "'" +
            '}';
    }
}
