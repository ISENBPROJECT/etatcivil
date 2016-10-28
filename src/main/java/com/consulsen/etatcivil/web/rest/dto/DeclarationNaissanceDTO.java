package com.consulsen.etatcivil.web.rest.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;


/**
 * A DTO for the DeclarationNaissance entity.
 */
public class DeclarationNaissanceDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateDeclaration;

  
    private PersonneDTO informationEnfant;


    private PersonneDTO informationPere;

    private PersonneDTO informationMere;

    private String mentionMarginale;

    
    private FichierDTO fichier;
    
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


}
