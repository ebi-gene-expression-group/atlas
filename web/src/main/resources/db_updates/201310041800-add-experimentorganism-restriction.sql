CREATE TABLE EXPERIMENT_ORGANISM(
    ORGANISM VARCHAR(255) NOT NULL,
    EXPERIMENT VARCHAR(255) NOT NULL,
    BIOENTITY_ORGANISM AS (NVL(substr(ORGANISM,0,instr(ORGANISM,' ',1,2) - 1),ORGANISM))
);

ALTER TABLE EXPERIMENT_SPECIES DROP CONSTRAINT FK_EXPERIMENT_ORGANISM;
DROP INDEX ES_EXPERIMENT_ACCESSION;
CREATE INDEX ES_EXPERIMENT_ACCESSION ON EXPERIMENT_ORGANISM (EXPERIMENT);
ALTER TABLE EXPERIMENT_ORGANISM
  ADD CONSTRAINT FK_EXPERIMENT_ORGANISM FOREIGN KEY (EXPERIMENT) REFERENCES EXPERIMENT(ACCESSION) ON DELETE CASCADE
ENABLE;

insert into EXPERIMENT_ORGANISM (organism, experiment) select species, experiment from experiment_species;
drop view VW_DIFFANALYTICS;
drop table EXPERIMENT_SPECIES;

-- This view now restrics the genes returned to those whose species matches the organism of the experiment('s samples) in which it is expressed
CREATE OR REPLACE FORCE VIEW VW_DIFFANALYTICS (IDENTIFIER, DESIGNELEMENT, NAME, ORGANISM, EXPERIMENT, CONTRASTID, ISACTIVE, PVAL, LOG2FOLD, TSTAT) AS
select dem.IDENTIFIER, mda.DESIGNELEMENT, bn.NAME, o.NAME, mda.EXPERIMENT, mda.CONTRASTID, mda.ISACTIVE, mda.PVAL, mda.LOG2FOLD, mda.TSTAT 
from MICROARRAY_DIFF_ANALYTICS subpartition( BELOW_DEFAULT_FDR ) mda
join DESIGNELEMENT_MAPPING dem on mda.designelement=dem.designelement and mda.arraydesign = dem.arraydesign
join BIOENTITY_NAME bn on dem.identifier=bn.identifier  
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
join EXPERIMENT_ORGANISM eo on o.name = eo.bioentity_organism and eo.experiment = mda.experiment
union all
select rda.IDENTIFIER, null, bn.NAME, o.name, rda.EXPERIMENT, rda.CONTRASTID, rda.ISACTIVE, rda.PVAL, rda.LOG2FOLD, null 
from RNASEQ_DIFF_ANALYTICS subpartition( BELOW_DEFAULT_FDR ) rda
join BIOENTITY_NAME bn on rda.IDENTIFIER=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
join EXPERIMENT_ORGANISM eo on o.name = eo.bioentity_organism and eo.experiment = rda.experiment;

