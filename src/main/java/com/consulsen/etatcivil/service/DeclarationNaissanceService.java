package com.consulsen.etatcivil.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consulsen.etatcivil.domain.DeclarationNaissance;
import com.consulsen.etatcivil.repository.DeclarationNaissanceRepository;
import com.consulsen.etatcivil.web.rest.dto.DeclarationNaissanceDTO;
import com.consulsen.etatcivil.web.rest.mapper.DeclarationNaissanceMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;



/**
 * Service Implementation for managing DeclarationNaissance.
 */
/**
 * @author a617407
 *
 */
@Service
@Transactional
public class DeclarationNaissanceService {

    private final Logger log = LoggerFactory.getLogger(DeclarationNaissanceService.class);

    @Inject
    private DeclarationNaissanceRepository declarationNaissanceRepository;

    @Inject
    private DeclarationNaissanceMapper declarationNaissanceMapper;
    
    private static String path_source = "C:\\Users\\Serigne26\\Pictures\\";
	private static String path_destination = "D:\\Outils\\";


    /**
     * Save a declarationNaissance.
     *
     * @param declarationNaissanceDTO the entity to save
     * @return the persisted entity
     */
    public DeclarationNaissanceDTO save(DeclarationNaissanceDTO declarationNaissanceDTO) {
        log.debug("Request to save DeclarationNaissance : {}", declarationNaissanceDTO);
        File file = new File(String.valueOf(declarationNaissanceDTO.getFichier()));
        DeclarationNaissance declarationNaissance = declarationNaissanceMapper.declarationNaissanceDTOToDeclarationNaissance(declarationNaissanceDTO);
       
        declarationNaissance = declarationNaissanceRepository.save(declarationNaissance);
        DeclarationNaissanceDTO result = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);
        Long registre = result.getId();
        creerExtraitNaissance(declarationNaissanceDTO,registre);
        try{
        creerTranscription(declarationNaissanceDTO);
        } catch (Exception e) {
		      e.printStackTrace();
		    }
        
        return result;
    }


    /**
     *  Get all the declarationNaissances.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DeclarationNaissanceDTO> findAll() {
        log.debug("Request to get all DeclarationNaissances");
          List<DeclarationNaissanceDTO> result = declarationNaissanceRepository.findAll().stream()
            .map(declarationNaissanceMapper::declarationNaissanceToDeclarationNaissanceDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one declarationNaissance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DeclarationNaissanceDTO findOne(Long id) {
        log.debug("Request to get DeclarationNaissance : {}", id);
        DeclarationNaissance declarationNaissance = declarationNaissanceRepository.findOne(id);
        DeclarationNaissanceDTO declarationNaissanceDTO = declarationNaissanceMapper.declarationNaissanceToDeclarationNaissanceDTO(declarationNaissance);
        return declarationNaissanceDTO;
    }

    /**
     *  Delete the  declarationNaissance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DeclarationNaissance : {}", id);
        declarationNaissanceRepository.delete(id);
    }
    
    /**
     *  Get declarationNaissance by criteria.
     *
     *  @param personneDTO
 	 *	@param declarationNaissanceDTO
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public List<DeclarationNaissanceDTO> findByCriteria(DeclarationNaissanceDTO declarationNaissanceDTO) {
    	 log.debug("Request to get DeclarationNaissances by criteria");
    	 String nom = "%" + declarationNaissanceDTO.getInformationEnfant().getNom() +"%";
    	 String prenom = "%" + declarationNaissanceDTO.getInformationEnfant().getPrenom() +"%";
    	 
         List<DeclarationNaissanceDTO> result = declarationNaissanceRepository.findByCriteria(declarationNaissanceDTO.getId(), nom,
        		 prenom, declarationNaissanceDTO.getInformationEnfant().getDateNaissance()).stream()
           .map(declarationNaissanceMapper::declarationNaissanceToDeclarationNaissanceDTO)
           .collect(Collectors.toCollection(LinkedList::new));
       return result;
    }
    
    
    /**
     * @param file
     */
    public void copyFileToPJDirectory(String file) { log.debug("Copie file to the directory pj");
    // String file = "PV.pdf";
	Path source = Paths.get(path_source + file);
   	 Path destination = Paths.get(path_destination + file);
   	 try {
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
    
    /**
     * 
     * @param declarationNaissanceDTO
     * @param registre
     * @return
     * 
     * Cette fonction permet d'éditer l'extrait de naissance
     */
    public String creerExtraitNaissance (DeclarationNaissanceDTO declarationNaissanceDTO, Long id){ 
		 PdfReader pdfTemplate;
		 String acteNaissance = declarationNaissanceDTO.getInformationEnfant().getPrenom()+"_"+declarationNaissanceDTO.getInformationEnfant().getNom()
				 +"_acte_naissance.pdf";
		// SimpleDateFormat dateDeclaration = null;
		 Calendar c = Calendar.getInstance();
		 int year = c.get(Calendar.YEAR);
		 String registre = year+"-"+id;
		 DateFormat format_fr = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRENCH);
		try {
			pdfTemplate = new PdfReader("template_acte_naissance.pdf");
			FileOutputStream fileOutputStream = new FileOutputStream(acteNaissance);
			//ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(pdfTemplate, fileOutputStream);
			stamper.setFormFlattening(true);	
			stamper.getAcroFields().setField("annee",Integer.toString(year));
			stamper.getAcroFields().setField("registre", registre);
			stamper.getAcroFields().setField("dateNaissance", format_fr.format(fromLocalDate(declarationNaissanceDTO.getInformationEnfant().getDateNaissance())));			
			stamper.getAcroFields().setField("lieu",declarationNaissanceDTO.lieuDeclaration);
			stamper.getAcroFields().setField("lieuNaissance", declarationNaissanceDTO.getInformationEnfant().getAdresse().getVille());
			stamper.getAcroFields().setField("sexe", declarationNaissanceDTO.getInformationEnfant().getGenre());
			stamper.getAcroFields().setField("prenom",declarationNaissanceDTO.getInformationEnfant().getPrenom());
			stamper.getAcroFields().setField("nom", declarationNaissanceDTO.getInformationEnfant().getNom());
			stamper.getAcroFields().setField("prenomPere", declarationNaissanceDTO.getInformationPere().getPrenom());
			stamper.getAcroFields().setField("nomMere",declarationNaissanceDTO.getInformationMere().getPrenom()+" "+declarationNaissanceDTO.getInformationMere().getNom());
			stamper.getAcroFields().setField("mentionMarginale", declarationNaissanceDTO.getMentionMarginale());
			stamper.getAcroFields().setField("dateDeclaration",format_fr.format(new Date()));
			stamper.close();
			pdfTemplate.close();		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
			
		return acteNaissance;
	 }
     
    /**
     * 
     * @param declarationNaissanceDTO
     * @throws IOException
     * @throws DocumentException
     * 
     * Cette fonction permet de créer le fichier de transcription de naissance
     */
    public void creerTranscription (DeclarationNaissanceDTO declarationNaissanceDTO) 
    		throws IOException, DocumentException{
    	
     String FILE = "C:/workspace/etatcivil/transcription_naissance.pdf";
  	   Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
  	      Font.BOLD);
  	   Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
  	      Font.BOLD);
  	 Calendar c = Calendar.getInstance();
	 int year = c.get(Calendar.YEAR);
	 DateFormat format_fr = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRENCH);
  	 Document document = new Document();
     PdfWriter.getInstance(document, new FileOutputStream(FILE));
     document.open();
	  String  ville_naissance_pere ="Contribel (Guinée)";
	  String  profession_pere = " Mécanicien";
	  String  ville_naissance_mere = "Youtou (Sénégal)";
	  String profession_mere = "femme de ménage";
	  String personne_qui_transcrit = "Monsieur Abdourahmane KOITA"; /// c'est le user qui s'est connecté
	  String  fonction = "Consul Général de la République du Sénégal à Bordeaux"; //fonction du user
	  
	    Paragraph preface = new Paragraph();
	    addEmptyLine(preface, 1);
	    preface.add(new Paragraph("Transcription de l’acte de naissance", catFont));
	    addEmptyLine(preface, 2);
	    preface.add(new Paragraph("Année :"+year,smallBold));
	    addEmptyLine(preface, 1);
	    preface.add(new Paragraph("Le "+format_fr.format(fromLocalDate(declarationNaissanceDTO.getInformationEnfant().getDateNaissance()))+" est né(e) à "
	    		+declarationNaissanceDTO.getInformationEnfant().getAdresse().getVille()+", "
	    		+declarationNaissanceDTO.getInformationEnfant().getPrenom()+" "+declarationNaissanceDTO.getInformationEnfant().getNom()
	    		+", de "+declarationNaissanceDTO.getInformationPere().getPrenom()+" "+declarationNaissanceDTO.getInformationPere().getNom()
	    		+" né le : "+format_fr.format(fromLocalDate(declarationNaissanceDTO.getInformationPere().getDateNaissance()))
	    		+" à "+ville_naissance_pere+", "+profession_pere
	    		+" et de "+declarationNaissanceDTO.getInformationMere().getPrenom()+" "+declarationNaissanceDTO.getInformationMere().getNom()+","
	    		+ " née le : "+format_fr.format(fromLocalDate(declarationNaissanceDTO.getInformationMere().getDateNaissance()))
	    		+" à "+ville_naissance_mere+", "+profession_mere+"."));
	    addEmptyLine(preface, 1);
	    preface.add(new Paragraph("Transcrit le "+format_fr.format(new Date())+", par Nous, "+personne_qui_transcrit+", "+fonction
	    		+", Officier de l’état-civil sur la foi de l’acte de naissance authentique, ci-contre, dressé par la Mairie de "
	    		+declarationNaissanceDTO.getInformationEnfant().getAdresse().getVille()+"."));
	    document.add(preface);
	    document.close();
    	
    }
    
    private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	  }
    
    public static Date fromLocalDate(LocalDate date) {
	    Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault())
	        .toInstant();
	    return Date.from(instant);
	  }
       

}
