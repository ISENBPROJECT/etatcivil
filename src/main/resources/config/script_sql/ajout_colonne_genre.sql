ALTER TABLE etatcivil.personne
ADD genre VARCHAR(255);
--ajout colonne pays de naissance
alter table etatcivil.personne add column pays_naissance varchar(255);

--ajout colonne fonction
alter table etatcivil.personne add column fonction varchar(255);

-- ville de naissance
alter table etatcivil.personne add column ville_naissance varchar(255);
