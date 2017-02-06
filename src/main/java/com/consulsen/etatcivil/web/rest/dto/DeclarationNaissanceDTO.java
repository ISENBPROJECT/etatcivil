package com.consulsen.etatcivil.web.rest.dto;

import com.consulsen.etatcivil.domain.Fichier;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;


/**
 * A DTO for the DeclarationNaissance entity.
 */
public class DeclarationNaissanceDTO implements Serializable {

    private Long id;

    public static String lieuDeclaration = "Bordeaux";

    @NotNull
    private LocalDate dateDeclaration;


    private PersonneDTO informationEnfant;


    private PersonneDTO informationPere;

    private PersonneDTO informationMere;

    private String mentionMarginale;

    private FichierDTO fichier;

    private Set<FichierDTO> fichiers = new HashSet<>();

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

    public String getMentionMarginale() {
        return mentionMarginale;
    }

    public void setMentionMarginale(String mentionMarginale) {
        this.mentionMarginale = mentionMarginale;
    }


    public static String getLieuDeclaration() {
        return lieuDeclaration;
    }

    public static void setLieuDeclaration(String lieuDeclaration) {
        DeclarationNaissanceDTO.lieuDeclaration = lieuDeclaration;
    }

    public Set<FichierDTO> getFichiers() {
        return fichiers;
    }

    public void setFichiers(Set<FichierDTO> fichiers) {
        this.fichiers = fichiers;
    }

    /**
	 * @return the fichier
	 */
	public FichierDTO getFichier() {
		return fichier;
	}

	/**
	 * @param fichier the fichier to set
	 */
	public void setFichier(FichierDTO fichier) {
		this.fichier = fichier;
	}

	/**
	 * @return the informationPere
	 */
	public PersonneDTO getInformationPere() {
		return informationPere;
	}

	/**
	 * @param informationPere the informationPere to set
	 */
	public void setInformationPere(PersonneDTO informationPere) {
		this.informationPere = informationPere;
	}

	/**
	 * @return the informationMere
	 */
	public PersonneDTO getInformationMere() {
		return informationMere;
	}

	/**
	 * @param informationMere the informationMere to set
	 */
	public void setInformationMere(PersonneDTO informationMere) {
		this.informationMere = informationMere;
	}

	/**
	 * @return the informationEnfant
	 */
	public PersonneDTO getInformationEnfant() {
		return informationEnfant;
	}

	/**
	 * @param informationEnfant the informationEnfant to set
	 */
	public void setInformationEnfant(PersonneDTO informationEnfant) {
		this.informationEnfant = informationEnfant;
	}

    @Override
    public boolean equals(Object o) {


        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeclarationNaissanceDTO that = (DeclarationNaissanceDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dateDeclaration != null ? !dateDeclaration.equals(that.dateDeclaration) : that.dateDeclaration != null)
            return false;
        if (informationEnfant != null ? !informationEnfant.equals(that.informationEnfant) : that.informationEnfant != null)
            return false;
        if (informationPere != null ? !informationPere.equals(that.informationPere) : that.informationPere != null)
            return false;
        if (informationMere != null ? !informationMere.equals(that.informationMere) : that.informationMere != null)
            return false;
        if (mentionMarginale != null ? !mentionMarginale.equals(that.mentionMarginale) : that.mentionMarginale != null)
            return false;
        if (fichier != null ? !fichier.equals(that.fichier) : that.fichier != null) return false;
        return fichiers != null ? fichiers.equals(that.fichiers) : that.fichiers == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateDeclaration != null ? dateDeclaration.hashCode() : 0);
        result = 31 * result + (informationEnfant != null ? informationEnfant.hashCode() : 0);
        result = 31 * result + (informationPere != null ? informationPere.hashCode() : 0);
        result = 31 * result + (informationMere != null ? informationMere.hashCode() : 0);
        result = 31 * result + (mentionMarginale != null ? mentionMarginale.hashCode() : 0);
        result = 31 * result + (fichier != null ? fichier.hashCode() : 0);
        result = 31 * result + (fichiers != null ? fichiers.hashCode() : 0);
        return result;
    }
}
