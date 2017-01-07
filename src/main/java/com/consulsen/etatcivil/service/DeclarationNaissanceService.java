package com.consulsen.etatcivil.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;



/**
 * Service Implementation for managing DeclarationNaissance.
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
    
    
    public String creerExtraitNaissance (DeclarationNaissanceDTO declarationNaissanceDTO, Long registre){ 
		 PdfReader pdfTemplate;
		 String acteNaissance = declarationNaissanceDTO.getInformationEnfant().getPrenom()+"_"+declarationNaissanceDTO.getInformationEnfant().getNom()
				 +"_acte_naissance.pdf";
		 SimpleDateFormat dateDeclaration = null;
		 Calendar c = Calendar.getInstance();
		 int year = c.get(Calendar.YEAR);
		try {
			pdfTemplate = new PdfReader("template_acte_naissance.pdf");
			FileOutputStream fileOutputStream = new FileOutputStream(acteNaissance);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(pdfTemplate, fileOutputStream);
			stamper.setFormFlattening(true);	
			stamper.getAcroFields().setField("annee",Integer.toString(year));
			stamper.getAcroFields().setField("registre", Long.toString(registre));
			stamper.getAcroFields().setField("dateNaissance", declarationNaissanceDTO.getDateDeclaration().toString());			
			stamper.getAcroFields().setField("lieu",declarationNaissanceDTO.lieuDeclaration);
			stamper.getAcroFields().setField("lieuNaissance", declarationNaissanceDTO.getInformationEnfant().getAdresse().getVille());
			stamper.getAcroFields().setField("sexe", declarationNaissanceDTO.getInformationEnfant().getGenre());
			stamper.getAcroFields().setField("prenom",declarationNaissanceDTO.getInformationEnfant().getPrenom());
			stamper.getAcroFields().setField("nom", declarationNaissanceDTO.getInformationEnfant().getNom());
			stamper.getAcroFields().setField("prenomPere", declarationNaissanceDTO.getInformationPere().getPrenom());
			stamper.getAcroFields().setField("nomMere",declarationNaissanceDTO.getInformationMere().getPrenom()+" "+declarationNaissanceDTO.getInformationMere().getNom());
			stamper.getAcroFields().setField("mentionMarginale", declarationNaissanceDTO.getMentionMarginale());
			stamper.getAcroFields().setField("dateDeclaration","2016-12-11");
			
			stamper.close();
			pdfTemplate.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return acteNaissance;
	 }

//	/**
//	 * @param declarationNaissanceDTO
//	 * @param pdfTemplate
//	 * @param dateDeclaration
//	 * @param fileOutputStream
//	 * @throws DocumentException
//	 * @throws IOException
//	 */
//	private void valoriserDonneesTemplate(DeclarationNaissanceDTO declarationNaissanceDTO, PdfReader pdfTemplate,
//			SimpleDateFormat dateDeclaration, FileOutputStream fileOutputStream,long registre) throws  IOException, DocumentException {
//		PdfStamper stamper = new PdfStamper(pdfTemplate, fileOutputStream);
//		stamper.setFormFlattening(true);
//		
//		stamper.getAcroFields().setField("annee",Integer.toString(new Date().getYear()));
//		stamper.getAcroFields().setField("registre", Long.toString(registre));
//		stamper.getAcroFields().setField("dateNaissance", declarationNaissanceDTO.getDateDeclaration().toString());			
//		stamper.getAcroFields().setField("lieu",declarationNaissanceDTO.lieuDeclaration);
//		stamper.getAcroFields().setField("lieuNaissance", declarationNaissanceDTO.getInformationEnfant().getAdresse().getVille());
//		stamper.getAcroFields().setField("sexe", declarationNaissanceDTO.getInformationEnfant().getGenre());
//		stamper.getAcroFields().setField("prenom",declarationNaissanceDTO.getInformationEnfant().getPrenom());
//		stamper.getAcroFields().setField("nom", declarationNaissanceDTO.getInformationEnfant().getNom());
//		stamper.getAcroFields().setField("prenomPere", declarationNaissanceDTO.getInformationPere().getPrenom());
//		stamper.getAcroFields().setField("nomMere",declarationNaissanceDTO.getInformationMere().getPrenom()+" "+declarationNaissanceDTO.getInformationMere().getNom());
//		stamper.getAcroFields().setField("mentionMarginale", declarationNaissanceDTO.getMentionMarginale());
//		stamper.getAcroFields().setField("dateDeclaration","2016-12-11");
//		
//		stamper.close();
//		pdfTemplate.close();
//	}
//    
    
   

}
