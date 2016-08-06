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


    @ManyToOne (cascade = {CascadeType.ALL})
    @JoinColumn(name="identifiant_enfant")
    private Personne identifiantEnfant;

    @ManyToOne (cascade = {CascadeType.ALL})
    @JoinColumn(name="identifiant_pere")
    private Personne identifiantPere;

    @ManyToOne (cascade = {CascadeType.ALL})
    @JoinColumn(name="identifiant_mere")
    private Personne identifiantMere;

    @Column(name = "mention_marginale")
    private String mentionMarginale;

    @ManyToOne (cascade = {CascadeType.ALL})
    @JoinColumn(name="identifiant_fichier_id")
    private Fichier identifiantFichier;

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

 

    /**
	 * @return the identifiantEnfant
	 */
	public Personne getIdentifiantEnfant() {
		return identifiantEnfant;
	}

	/**
	 * @param identifiantEnfant the identifiantEnfant to set
	 */
	public void setIdentifiantEnfant(Personne identifiantEnfant) {
		this.identifiantEnfant = identifiantEnfant;
	}

	/**
	 * @return the identifiantPere
	 */
	public Personne getIdentifiantPere() {
		return identifiantPere;
	}

	/**
	 * @param identifiantPere the identifiantPere to set
	 */
	public void setIdentifiantPere(Personne identifiantPere) {
		this.identifiantPere = identifiantPere;
	}

	/**
	 * @return the identifiantMere
	 */
	public Personne getIdentifiantMere() {
		return identifiantMere;
	}

	/**
	 * @param identifiantMere the identifiantMere to set
	 */
	public void setIdentifiantMere(Personne identifiantMere) {
		this.identifiantMere = identifiantMere;
	}

	public String getMentionMarginale() {
        return mentionMarginale;
    }

    public void setMentionMarginale(String mentionMarginale) {
        this.mentionMarginale = mentionMarginale;
    }



    public Fichier getIdentifiantFichier() {
        return identifiantFichier;
    }

    public void setIdentifiantFichier(Fichier fichier) {
        this.identifiantFichier = fichier;
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

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeclarationNaissance [id=" + id + ", dateDeclaration=" + dateDeclaration + ", identifiantEnfant="
				+ identifiantEnfant + ", identifiantPere=" + identifiantPere + ", identifiantMere=" + identifiantMere
				+ ", mentionMarginale=" + mentionMarginale + ", numeroCarteIdentite="
				+ ", identifiantFichier=" + identifiantFichier + "]";
	}
    
    
}
