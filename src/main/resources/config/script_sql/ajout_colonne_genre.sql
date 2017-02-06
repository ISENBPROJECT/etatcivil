ALTER TABLE etatcivil.personne
ADD genre VARCHAR(255);
--ajout colonne pays de naissance
alter table etatcivil.personne add column pays_naissance varchar(255);

--ajout colonne fonction
alter table etatcivil.personne add column fonction varchar(255);

-- ville de naissance
alter table etatcivil.personne add column ville_naissance varchar(255);


--suppression colonne fichier dans declaration naissance
ALTER TABLE etatcivil.declaration_naissance DROP FOREIGN KEY fk_declarationnaissance_identifiantfichier_id;
DROP INDEX fk_declarationnaissance_identifiantfichier_id ON etatcivil.declaration_naissance;
ALTER TABLE etatcivil.declaration_naissance DROP identifiant_fichier_id;


--creation champ declaration naissance dans entity fichier
ALTER TABLE etatcivil.fichier ADD identifiant_declaration_id BIGINT NULL;
CREATE INDEX fk_fichier_identifiantdeclaration_id ON etatcivil.fichier (identifiant_declaration_id);
ALTER TABLE etatcivil.fichier
ADD CONSTRAINT fk_identifiant_declaration_id
FOREIGN KEY (identifiant_declaration_id) REFERENCES declaration_naissance(id);
