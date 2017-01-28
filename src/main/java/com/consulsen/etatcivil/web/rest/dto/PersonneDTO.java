package com.consulsen.etatcivil.web.rest.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * A DTO for the Personne entity.
 */
public class PersonneDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private LocalDate dateNaissance;

    private String genre;

    private String fonction;

    private String villeDeNaissance;

    private String paysDeNaissance;

    private AdresseDTO adresse;

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


    /**
     * @return the adresse
     */
    public AdresseDTO getAdresse() {
        return adresse;
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

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(AdresseDTO adresse) {
        this.adresse = adresse;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonneDTO that = (PersonneDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
        if (prenom != null ? !prenom.equals(that.prenom) : that.prenom != null) return false;
        if (dateNaissance != null ? !dateNaissance.equals(that.dateNaissance) : that.dateNaissance != null)
            return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        if (fonction != null ? !fonction.equals(that.fonction) : that.fonction != null) return false;
        if (villeDeNaissance != null ? !villeDeNaissance.equals(that.villeDeNaissance) : that.villeDeNaissance != null)
            return false;
        if (paysDeNaissance != null ? !paysDeNaissance.equals(that.paysDeNaissance) : that.paysDeNaissance != null)
            return false;
        if (adresse != null ? !adresse.equals(that.adresse) : that.adresse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        result = 31 * result + (dateNaissance != null ? dateNaissance.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (fonction != null ? fonction.hashCode() : 0);
        result = 31 * result + (villeDeNaissance != null ? villeDeNaissance.hashCode() : 0);
        result = 31 * result + (paysDeNaissance != null ? paysDeNaissance.hashCode() : 0);
        result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
        return result;
    }
}
