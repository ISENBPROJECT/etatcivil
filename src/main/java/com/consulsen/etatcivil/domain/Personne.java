package com.consulsen.etatcivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Personne.
 */
@Entity
@Table(name = "personne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "fonction", nullable = true)
    private String fonction;

    @Column(name = "ville_naissance", nullable = false)
    private String villeDeNaissance;

    @Column(name = "pays_naissance", nullable = false)
    private String paysDeNaissance;

    @Column(name = "genre", nullable = true)
    private String genre;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(unique = true)
    private Adresse adresse;

    @OneToMany(mappedBy = "id")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeclarationNaissance> declarationNaissances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Set<DeclarationNaissance> getDeclarationNaissances() {
        return declarationNaissances;
    }

    public void setDeclarationNaissances(Set<DeclarationNaissance> declarationNaissances) {
        this.declarationNaissances = declarationNaissances;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getVilleDeNaissance() {
        return villeDeNaissance;
    }

    public void setVilleDeNaissance(String villeDeNaissance) {
        this.villeDeNaissance = villeDeNaissance;
    }

    public String getPaysDeNaissance() {
        return paysDeNaissance;
    }

    public void setPaysDeNaissance(String paysDeNaissance) {
        this.paysDeNaissance = paysDeNaissance;
    }

    /**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Personne personne = (Personne) o;

        if (id != null ? !id.equals(personne.id) : personne.id != null) return false;
        if (nom != null ? !nom.equals(personne.nom) : personne.nom != null) return false;
        if (prenom != null ? !prenom.equals(personne.prenom) : personne.prenom != null) return false;
        if (fonction != null ? !fonction.equals(personne.fonction) : personne.fonction != null) return false;
        if (villeDeNaissance != null ? !villeDeNaissance.equals(personne.villeDeNaissance) : personne.villeDeNaissance != null)
            return false;
        if (paysDeNaissance != null ? !paysDeNaissance.equals(personne.paysDeNaissance) : personne.paysDeNaissance != null)
            return false;
        if (genre != null ? !genre.equals(personne.genre) : personne.genre != null) return false;
        if (dateNaissance != null ? !dateNaissance.equals(personne.dateNaissance) : personne.dateNaissance != null)
            return false;
        if (adresse != null ? !adresse.equals(personne.adresse) : personne.adresse != null) return false;
        return declarationNaissances != null ? declarationNaissances.equals(personne.declarationNaissances) : personne.declarationNaissances == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        result = 31 * result + (fonction != null ? fonction.hashCode() : 0);
        result = 31 * result + (villeDeNaissance != null ? villeDeNaissance.hashCode() : 0);
        result = 31 * result + (paysDeNaissance != null ? paysDeNaissance.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (dateNaissance != null ? dateNaissance.hashCode() : 0);
        result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
        result = 31 * result + (declarationNaissances != null ? declarationNaissances.hashCode() : 0);
        return result;
    }
}
