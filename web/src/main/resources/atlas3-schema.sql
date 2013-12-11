-- Microarray differential analytics
DROP TABLE MICROARRAY_DIFF_ANALYTICS;
CREATE TABLE MICROARRAY_DIFF_ANALYTICS (
    DESIGNELEMENT VARCHAR2(255) NOT NULL
  , EXPERIMENT VARCHAR2(255) NOT NULL
  , ARRAYDESIGN  VARCHAR(255) NOT NULL
  , CONTRASTID VARCHAR2(255) NOT NULL
  , ISACTIVE VARCHAR2(1) DEFAULT 'T'
  , PVAL FLOAT(125) NOT NULL
  , LOG2FOLD FLOAT(125)
  , TSTAT FLOAT(125) 
)
PARTITION BY LIST (ISACTIVE)
SUBPARTITION BY RANGE (PVAL) 
( PARTITION DIFF_ANALYTICS_ACTIVE VALUES ('T')
       ( SUBPARTITION BELOW_DEFAULT_FDR VALUES LESS THAN (0.05)
       , SUBPARTITION OTHERS VALUES LESS THAN (MAXVALUE)
       )
, PARTITION DIFF_ANALYTICS_INACTIVE VALUES ('F')
 )
PARALLEL;
-- This allows for inactivating an experiment's data - moving it to the DIFF_ANALYTICS_INACTIVE partition
alter table MICROARRAY_DIFF_ANALYTICS enable row movement;


CREATE INDEX PK_MICROARRAY_DIFF_ANALYTICS ON MICROARRAY_DIFF_ANALYTICS (DESIGNELEMENT, EXPERIMENT, CONTRASTID, PVAL, ISACTIVE) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE MICROARRAY_DIFF_ANALYTICS
  ADD CONSTRAINT PK_MICROARRAY_DIFF_ANALYTICS
  PRIMARY KEY (DESIGNELEMENT, EXPERIMENT, CONTRASTID, PVAL, ISACTIVE) USING INDEX PK_MICROARRAY_DIFF_ANALYTICS
ENABLE;

CREATE INDEX MICROARRAY_DIFF_ANALYTICS_IDX ON MICROARRAY_DIFF_ANALYTICS (EXPERIMENT, CONTRASTID) COMPRESS LOCAL (
     PARTITION  DIFF_ANALYTICS_ACTIVE TABLESPACE ATLASPRD3_INDX
     (
	SUBPARTITION BELOW_DEFAULT_FDR, 
	SUBPARTITION OTHERS
     )
   , PARTITION  DIFF_ANALYTICS_INACTIVE TABLESPACE ATLASPRD3_INDX
);

-- RNA-seq differential analytics
DROP TABLE RNASEQ_DIFF_ANALYTICS;
CREATE TABLE RNASEQ_DIFF_ANALYTICS (
    IDENTIFIER VARCHAR2(255) NOT NULL
  , EXPERIMENT VARCHAR2(255) NOT NULL
  , CONTRASTID VARCHAR2(255) NOT NULL
  , ISACTIVE VARCHAR2(1) DEFAULT 'T'
  , PVAL FLOAT(125) NOT NULL
  , LOG2FOLD FLOAT(125) 
)
PARTITION BY LIST (ISACTIVE)
SUBPARTITION BY RANGE (PVAL) 
( PARTITION DIFF_ANALYTICS_ACTIVE VALUES ('T')
       ( SUBPARTITION BELOW_DEFAULT_FDR VALUES LESS THAN (0.05)
       , SUBPARTITION OTHERS VALUES LESS THAN (MAXVALUE)
       )
, PARTITION DIFF_ANALYTICS_INACTIVE VALUES ('F')
 )
PARALLEL;
-- This allows for inactivating an experiment's data - moving it to the DIFF_ANALYTICS_INACTIVE partition
alter table RNASEQ_DIFF_ANALYTICS enable row movement;


CREATE INDEX PK_RNASEQ_DIFF_ANALYTICS ON RNASEQ_DIFF_ANALYTICS (IDENTIFIER, EXPERIMENT, CONTRASTID, PVAL, ISACTIVE) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE RNASEQ_DIFF_ANALYTICS
  ADD CONSTRAINT PK_RNASEQ_DIFF_ANALYTICS
  PRIMARY KEY (IDENTIFIER, EXPERIMENT, CONTRASTID, PVAL, ISACTIVE) USING INDEX PK_RNASEQ_DIFF_ANALYTICS
ENABLE;

CREATE INDEX RNASEQ_DIFF_ANALYTICS_IDX ON RNASEQ_DIFF_ANALYTICS (EXPERIMENT, CONTRASTID) COMPRESS LOCAL (
     PARTITION  DIFF_ANALYTICS_ACTIVE TABLESPACE ATLASPRD3_INDX
     (
	SUBPARTITION BELOW_DEFAULT_FDR, 
	SUBPARTITION OTHERS
     )
   , PARTITION  DIFF_ANALYTICS_INACTIVE TABLESPACE ATLASPRD3_INDX
);

-- Differential analitics across all types of experiments, with additional gene name and organism information
DROP MATERIALIZED VIEW VW_DIFFANALYTICS;
CREATE MATERIALIZED VIEW VW_DIFFANALYTICS 
NOLOGGING PARALLEL 16
BUILD DEFERRED
REFRESH COMPLETE ON DEMAND 
ENABLE QUERY REWRITE AS
select dem.IDENTIFIER, mda.DESIGNELEMENT, bn.NAME AS NAME, o.NAME AS ORGANISM, mda.EXPERIMENT, mda.CONTRASTID, mda.ISACTIVE, mda.PVAL, mda.LOG2FOLD, mda.TSTAT 
from MICROARRAY_DIFF_ANALYTICS subpartition( BELOW_DEFAULT_FDR ) mda
join DESIGNELEMENT_MAPPING dem on mda.designelement=dem.designelement and mda.arraydesign = dem.arraydesign
join BIOENTITY_NAME bn on dem.identifier=bn.identifier  
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
join EXPERIMENT_ORGANISM eo on o.name = eo.bioentity_organism and eo.experiment = mda.experiment
union all
select rda.IDENTIFIER, null, bn.NAME AS NAME, o.name AS ORGANISM, rda.EXPERIMENT, rda.CONTRASTID, rda.ISACTIVE, rda.PVAL, rda.LOG2FOLD, null 
from RNASEQ_DIFF_ANALYTICS subpartition( BELOW_DEFAULT_FDR ) rda
join BIOENTITY_NAME bn on rda.IDENTIFIER=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
join EXPERIMENT_ORGANISM eo on o.name = eo.bioentity_organism and eo.experiment = rda.experiment;

-- RNA-seq baseline analytics
DROP TABLE RNASEQ_BSLN_EXPRESSIONS;
CREATE TABLE RNASEQ_BSLN_EXPRESSIONS (
    IDENTIFIER VARCHAR2(255) NOT NULL
  , EXPERIMENT VARCHAR2(255) NOT NULL
  , ASSAYGROUPID VARCHAR2(255) NOT NULL
  , ISACTIVE VARCHAR2(1) DEFAULT 'T'
  , EXPRESSION FLOAT(125) NOT NULL
)
PARTITION BY LIST (ISACTIVE)
SUBPARTITION BY RANGE (EXPRESSION) 
( PARTITION BSLN_EXPRESSIONS_ACTIVE VALUES ('T')
       ( SUBPARTITION OTHERS VALUES LESS THAN (0.6)
       , SUBPARTITION ABOVE_CUTOFF VALUES LESS THAN (MAXVALUE)
       )
, PARTITION BSLN_EXPRESSIONS_INACTIVE VALUES ('F')
 )
PARALLEL;
-- This allows for inactivating an experiment's data - moving it to the BSLN_EXPRESSIONS_INACTIVE partition
alter table RNASEQ_BSLN_EXPRESSIONS enable row movement;

CREATE INDEX PK_RNASEQ_BSLN_ANALYTICS ON RNASEQ_BSLN_EXPRESSIONS (IDENTIFIER, EXPERIMENT, ASSAYGROUPID, EXPRESSION, ISACTIVE) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE RNASEQ_BSLN_EXPRESSIONS
  ADD CONSTRAINT PK_RNASEQ_BSLN_EXPRESSIONS
  PRIMARY KEY (IDENTIFIER, EXPERIMENT, ASSAYGROUPID, EXPRESSION, ISACTIVE) USING INDEX PK_RNASEQ_BSLN_ANALYTICS
ENABLE;

CREATE INDEX RNASEQ_BSLN_EXPRESSIONS_IDX ON RNASEQ_BSLN_EXPRESSIONS (IDENTIFIER, ISACTIVE, EXPERIMENT, ASSAYGROUPID) COMPRESS LOCAL (
     PARTITION  BSLN_EXPRESSIONS_ACTIVE TABLESPACE ATLASPRD3_INDX
     (
        SUBPARTITION OTHERS,
	SUBPARTITION ABOVE_CUTOFF
     )
   , PARTITION BSLN_EXPRESSIONS_INACTIVE TABLESPACE ATLASPRD3_INDX
);

-- miRBase and Ensembl gene names
DROP TABLE BIOENTITY_NAME;
CREATE TABLE BIOENTITY_NAME (
    identifier VARCHAR(255) NOT NULL,
    organismid NUMBER(22,0) NOT NULL,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(255)
);

CREATE INDEX PK_BIOENTITY_NAME ON BIOENTITY_NAME (IDENTIFIER, ORGANISMID, TYPE) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE BIOENTITY_NAME
  ADD CONSTRAINT PK_BIOENTITY_NAME
  PRIMARY KEY (IDENTIFIER, ORGANISMID, TYPE) USING INDEX PK_BIOENTITY_NAME
ENABLE;

-- miRBase and Ensembl design element to gene mappings
DROP TABLE DESIGNELEMENT_MAPPING;
CREATE TABLE DESIGNELEMENT_MAPPING (
    designelement VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    arraydesign  VARCHAR(255) NOT NULL
);

CREATE INDEX PK_DESIGNELEMENT_MAPPING ON DESIGNELEMENT_MAPPING (DESIGNELEMENT, IDENTIFIER, ARRAYDESIGN) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE DESIGNELEMENT_MAPPING
  ADD CONSTRAINT PK_DESIGNELEMENT_MAPPING
  PRIMARY KEY (DESIGNELEMENT, IDENTIFIER, ARRAYDESIGN) USING INDEX PK_DESIGNELEMENT_MAPPING
ENABLE;

-- Reference table for all bioentity organisms (across Ensembl and miRBase
DROP TABLE BIOENTITY_ORGANISM;
CREATE TABLE BIOENTITY_ORGANISM (
    organismid NUMBER(22,0) NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE INDEX PK_BIOENTITY_ORGANISM ON BIOENTITY_ORGANISM (ORGANISMID) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE BIOENTITY_ORGANISM
  ADD CONSTRAINT PK_BIOENTITY_ORGANISM
  PRIMARY KEY (ORGANISMID) USING INDEX PK_BIOENTITY_ORGANISM
ENABLE;

-- Transcript expressions per gene for RNA-seq baseline experiments
DROP TABLE RNASEQ_BSLN_TRANSCRIPTS;
CREATE TABLE RNASEQ_BSLN_TRANSCRIPTS(
    EXPERIMENT VARCHAR(255) NOT NULL
  , GENE_IDENTIFIER VARCHAR(255) NOT NULL
  , TRANSCRIPT_IDENTIFIER VARCHAR(255) NOT NULL
  , TRANSCRIPT_EXPRESSIONS VARCHAR(1000) NOT NULL
  , ISACTIVE VARCHAR2(1) DEFAULT 'T'
)
PARTITION BY LIST (ISACTIVE)
( PARTITION RNASEQ_BSLN_TSCRPT_ACTIVE VALUES ('T')
, PARTITION RNASEQ_BSLN_TSCRPT_INACTIVE VALUES ('F')
 )
PARALLEL;
-- This allows for inactivating an experiment's data - moving it to the RNASEQ_BSLN_TSCRPT_INACTIVE partition
alter table RNASEQ_BSLN_TRANSCRIPTS enable row movement;

CREATE INDEX PK_RNASEQ_BSLN_TRANSCRIPTS ON RNASEQ_BSLN_TRANSCRIPTS (EXPERIMENT, GENE_IDENTIFIER, TRANSCRIPT_IDENTIFIER, ISACTIVE) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE RNASEQ_BSLN_TRANSCRIPTS
  ADD CONSTRAINT PK_RNASEQ_BSLN_TRANSCRIPTS
  PRIMARY KEY (EXPERIMENT, GENE_IDENTIFIER, TRANSCRIPT_IDENTIFIER, ISACTIVE) USING INDEX PK_RNASEQ_BSLN_TRANSCRIPTS
ENABLE;

-- Experiment table
ALTER TABLE EXPERIMENT_ORGANISM DROP CONSTRAINT FK_EXPERIMENT_ORGANISM;
DROP TABLE EXPERIMENT;
CREATE TABLE EXPERIMENT(
    ACCESSION VARCHAR(255) NOT NULL,
    TYPE VARCHAR(50) NOT NULL,
    ACCESS_KEY CHAR(36) NOT NULL,
    PRIVATE VARCHAR2(1) DEFAULT 'T',
    LAST_UPDATE DATE DEFAULT sysdate,
    PUBMED_IDS VARCHAR2(255),
    TITLE VARCHAR2(255)
);

CREATE INDEX PK_EXPERIMENT ON EXPERIMENT (ACCESSION) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE EXPERIMENT
  ADD CONSTRAINT PK_EXPERIMENT
  PRIMARY KEY (ACCESSION) USING INDEX PK_EXPERIMENT
ENABLE;

CREATE OR REPLACE VIEW PUBLIC_EXPERIMENT AS
SELECT ACCESSION, type, LAST_UPDATE
FROM EXPERIMENT WHERE PRIVATE='F';

-- Data processing in-progress flag table - used by data production team - no dependency on it exists in the Web services code
DROP TABLE DATAPROCESSING_JOB_LOCK;
CREATE TABLE DATAPROCESSING_JOB_LOCK(
    INPROGRESS DATE DEFAULT sysdate
);

-- Species table
DROP TABLE EXPERIMENT_ORGANISM;
CREATE TABLE EXPERIMENT_ORGANISM(
    ORGANISM VARCHAR(255) NOT NULL,
    EXPERIMENT VARCHAR(255) NOT NULL,
    BIOENTITY_ORGANISM AS (NVL(substr(ORGANISM,0,instr(ORGANISM,' ',1,2) - 1),ORGANISM))
);

CREATE INDEX ES_EXPERIMENT_ACCESSION ON EXPERIMENT_ORGANISM (EXPERIMENT) TABLESPACE ATLASPRD3_INDX;
ALTER TABLE EXPERIMENT_ORGANISM
  ADD CONSTRAINT FK_EXPERIMENT_ORGANISM FOREIGN KEY (EXPERIMENT) REFERENCES EXPERIMENT(ACCESSION) ON DELETE CASCADE
ENABLE;

-- Stored functions
CREATE OR REPLACE Function getBaselineExpSpecificCount
   ( experiment_in IN varchar2, assaygroupids_in IN varchar2 )
   RETURN number
IS
   cnumber number;

   cursor c1 is
   select count(distinct rbe.identifier)
   from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe
   where rbe.ASSAYGROUPID in (assaygroupids_in) and rbe.EXPERIMENT = experiment_in
   and exists (select 1 from bioentity_name where identifier = rbe.identifier)
   and 
   ( select avg(expression)
   from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) 
   where ASSAYGROUPID in (assaygroupids_in) and EXPERIMENT = experiment_in
   and identifier = rbe.identifier) >
   (select NVL(max(expression),0)
   from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) 
   where ASSAYGROUPID not in (assaygroupids_in) and EXPERIMENT = experiment_in
   and identifier = rbe.identifier);

BEGIN
   open c1;
   fetch c1 into cnumber;

   if c1%notfound then
      cnumber := 0;
   end if;

   close c1;
RETURN cnumber;

EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;

-- Generalised stored function that can return counts of distinct gene identifiers. It takes the following 4 parameters:
-- experiment_in - experiment accession, e.g. 'E-GEOD-30352'
-- query_assaygroupids_in - comma-separated list of assay groups forming a specific query (i.e. expression specific to just those assay groups), e.g. 'g9,g12'
-- subexp_assaygroupids_in - comma-separated list of assay groups describe a specific partition of a larger baseline experiment, e.g. 'Mus musculus' organism in E-GEOD-30352,
--                           e.g. 'g9,g10,g11,g12,g13,g14'
-- geneids_in - comma-separated list of gene identifiers to be used in the query
-- regex_in - separator of values in query_assaygroupids_in, subexp_assaygroupids_in (',' by default - does not need to be specified at function call time)
-- This function covers the following query cases:
-- 1. A non-specific query for genes in a whole experiment, e.g. 
--     select getBaselineExprCount('E-MTAB-599',null,null,'ENSMUSG00000064356') from dual; 
--     select getBaselineExprCount('E-MTAB-599',null,null,'ENSMUSG00000022097,ENSMUSG00000024653,ENSMUSG00000064356') from dual;
-- 2. A non-specific query for genes in an experiment partition, e.g. 
--    Sub-experiment (mus musculus):
--    select getBaselineExprCount('E-GEOD-30352',null,'g9,g10,g11,g12,g13,g14','ENSMUSG00000054422,ENSMUSG00000066154,ENSMUSG00000018593') from dual; 
-- 3. A specific query with no genes specified across the whole experiment, e.g. 
--    select getBaselineExprCount('E-MTAB-599','g2',null,null) from dual;
-- 4. A specific query with no genes specified across an experiment partition, e.g. 
--    Sub-experiment (mus musculus):
--    select getBaselineExprCount('E-GEOD-30352','g12','g9,g10,g11,g12,g13,g14',null) from dual;
-- 5. A specific query for genes in a whole experiment, e.g. 
--    Whole experiment, specific query (liver:g2 and lung:g5)
--    select getBaselineExprCount('E-MTAB-599','g5',null,'ENSMUSG00000022097,ENSMUSG00000024653,ENSMUSG00000064356') from dual;
--    select getBaselineExprCount('E-MTAB-599','g2,g5', null,'ENSMUSG00000022097,ENSMUSG00000024653,ENSMUSG00000064356') from dual;
--    select getBaselineExprCount('E-MTAB-599','g5',null,'ENSMUSG00000022097') from dual;
--    select getBaselineExprCount('E-MTAB-599','g2',null,'ENSMUSG00000064356') from dual;
-- 6. A specific query for genes in an experiment partition:
--    Sub-experiment (mus musculus), specific query (heart:g9, liver:g12) 
--    select getBaselineExprCount('E-GEOD-30352','g9,g12','g9,g10,g11,g12,g13,g14','ENSMUSG00000054422,ENSMUSG00000066154,ENSMUSG00000018593') from dual;
--    select getBaselineExprCount('E-GEOD-30352','g9','g9,g10,g11,g12,g13,g14','ENSMUSG00000054422,ENSMUSG00000066154,ENSMUSG00000018593') from dual;
--    select getBaselineExprCount('E-GEOD-30352','g12','g9,g10,g11,g12,g13,g14','ENSMUSG00000054422,ENSMUSG00000066154,ENSMUSG00000018593') from dual;
CREATE OR REPLACE Function getBaselineExprCount
   ( experiment_in IN varchar2, query_assaygroupids_in IN varchar2, subexp_assaygroupids_in IN varchar2, geneids_in IN varchar2, regex_in IN varchar2 DEFAULT '[^,]+' )
   RETURN number
IS
   cnumber number;
   -- Define a weakly typed system reference cursor.
   c   SYS_REFCURSOR;

mainSelect VARCHAR2(2000) := 'select count(distinct rbe.identifier) from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe '
                          || 'where rbe.experiment = :experiment_in '
                          || 'and exists (select 1 from bioentity_name where identifier = rbe.identifier) ';
                          
specificGroupsSelect VARCHAR2(2000) := 'select regexp_substr(:query_assaygroupids_in, :regex_in, 1, level) from dual '
                              || '      connect by level <= regexp_count(:query_assaygroupids_in , :regex_in) ';     
                              
subExpSelect VARCHAR2(2000) := 'and rbe.assaygroupid in ( '
                            || 'select regexp_substr(:subexp_assaygroupids_in, :regex_in, 1, level) from dual '
                            || 'connect by level <= regexp_count(:subexp_assaygroupids_in , :regex_in)) ';

specificClauseWholeExp VARCHAR2(2000) := 'and (  select avg(expression) '
                              || '   from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) ' 
                              || '   where assaygroupid in ( ' || specificGroupsSelect || ')'
                              || '   and experiment = :experiment_in '
                              || '   and identifier = rbe.identifier) > '  
                              || '    (  select NVL(max(expression),0) '
                              || '   from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) ' 
                              || '   where assaygroupid not in ( ' || specificGroupsSelect || ')'                            
                              || '   and experiment = :experiment_in '
                              || '   and identifier = rbe.identifier)'; 
                              
specificClauseSubExp VARCHAR2(2000) := 'and (  select avg(expression) '
                              || '   from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) ' 
                              || '   where assaygroupid in ( ' || specificGroupsSelect || ')'
                              || '   and experiment = :experiment_in '
                              || '   and identifier = rbe.identifier) > '  
                              || '    (  select NVL(max(expression),0) '
                              || '   from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) ' 
                              || '   where assaygroupid not in ( ' || specificGroupsSelect || ')'                           
                              || '   and experiment = :experiment_in '
                              || subExpSelect                                 
                              || '   and identifier = rbe.identifier)'; 
                                    
geneIdentifierSelect VARCHAR2(2000) := 'and rbe.identifier in ( '
                            || 'select regexp_substr(:geneids_in, :regex_in, 1, level) from dual '
                            || 'connect by level <= regexp_count(:geneids_in , :regex_in)) ';
    
    
BEGIN
   IF query_assaygroupids_in is NULL THEN
      -- Non-specific query (gene ids must be specified; sub or whole experiment)
      IF subexp_assaygroupids_in is NULL THEN
         -- Non-specific query in the whole experiment
         OPEN c FOR mainSelect || geneIdentifierSelect 
         USING experiment_in, geneids_in, regex_in, geneids_in, regex_in;
      ELSE
        -- Non-specific query including a sub-experiment restriction
        OPEN c FOR mainSelect || geneIdentifierSelect || subExpSelect 
        USING experiment_in, geneids_in, regex_in, geneids_in, regex_in, subexp_assaygroupids_in, regex_in, subexp_assaygroupids_in, regex_in;
      END IF;
   ELSE
      -- Specific query (gene ids may or may not be specified; sub or whole experiment)
      IF geneids_in is NULL THEN
         IF subexp_assaygroupids_in is NULL THEN
            -- Specific query with no gene identifiers specified in whole experiment
            OPEN c FOR mainSelect || specificClauseWholeExp 
            USING experiment_in,  
                  query_assaygroupids_in, regex_in, query_assaygroupids_in, regex_in, experiment_in,
                  query_assaygroupids_in, regex_in, query_assaygroupids_in, regex_in, experiment_in;
         ELSE
            -- Specific query with no gene identifiers specified in a sub-experiment
            OPEN c FOR mainSelect || subExpSelect || specificClauseSubExp 
            USING experiment_in, 
                  subexp_assaygroupids_in, regex_in, subexp_assaygroupids_in, regex_in, 
                  query_assaygroupids_in, regex_in, query_assaygroupids_in, regex_in, experiment_in,
                  query_assaygroupids_in, regex_in, query_assaygroupids_in, regex_in, experiment_in,
                  subexp_assaygroupids_in, regex_in, subexp_assaygroupids_in, regex_in;
         END IF;
      ELSE
        IF subexp_assaygroupids_in is NULL THEN
            -- Specific query with gene identifiers specified in whole experiment
            OPEN c FOR mainSelect || geneIdentifierSelect || specificClauseWholeExp 
            USING experiment_in, geneids_in, regex_in, geneids_in, regex_in, 
                  query_assaygroupids_in, regex_in, query_assaygroupids_in, regex_in, experiment_in,
                  query_assaygroupids_in, regex_in, query_assaygroupids_in, regex_in, experiment_in;
        ELSE
           -- Specific query with gene identifiers specified in a sub-experiment
           OPEN c FOR mainSelect || geneIdentifierSelect || subExpSelect || specificClauseSubExp 
           USING experiment_in, 
                 geneids_in, regex_in, geneids_in, regex_in, 
                 subexp_assaygroupids_in, regex_in, subexp_assaygroupids_in, regex_in,
                 query_assaygroupids_in, regex_in, query_assaygroupids_in, regex_in, experiment_in,
                 query_assaygroupids_in, regex_in, query_assaygroupids_in, regex_in, experiment_in,
                 subexp_assaygroupids_in, regex_in, subexp_assaygroupids_in, regex_in;
        END IF;
      END IF;
   END IF;
   
   fetch c into cnumber;
   if c%notfound then
      cnumber := 0;
   end if;
   close c;

RETURN cnumber;                              

EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;

-------------------------------------------------------------------------------
-- Facade stored functions to simplify the interface to getBaselineExprCount()
-------------------------------------------------------------------------------

-- A non-specific query for genes in a whole experiment, e.g. 
--     select getWholeBslnExprCntForGenes('E-MTAB-599','ENSMUSG00000064356') from dual; 
--     select getWholeBslnExprCntForGenes('E-MTAB-599','ENSMUSG00000022097,ENSMUSG00000024653,ENSMUSG00000064356') from dual;
CREATE OR REPLACE Function getWholeBslnExprCntForGenes
   ( experiment_in IN varchar2, geneids_in IN varchar2 )
   RETURN number
IS
   cnumber number;
BEGIN
   cnumber := getBaselineExprCount(experiment_in, null, null, geneids_in);
   RETURN cnumber;                              
EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;

-- A non-specific query for genes in an experiment partition, e.g. 
--    Sub-experiment (mus musculus):
--    select getSubBslnExprCntForGenes('E-GEOD-30352','g9,g10,g11,g12,g13,g14','ENSMUSG00000054422,ENSMUSG00000066154,ENSMUSG00000018593') from dual; 
CREATE OR REPLACE Function getSubBslnExprCntForGenes
   ( experiment_in IN varchar2, subexp_assaygroupids_in IN varchar2, geneids_in IN varchar2 )
   RETURN number
IS
   cnumber number;
BEGIN
   cnumber := getBaselineExprCount(experiment_in, null, subexp_assaygroupids_in, geneids_in);
   RETURN cnumber;                              
EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;

-- A specific query with no genes specified across the whole experiment, e.g. 
--    select getWholeBslnExprSpfcCnt('E-MTAB-599','g2') from dual;
CREATE OR REPLACE Function getWholeBslnExprSpfcCnt
   ( experiment_in IN varchar2, query_assaygroupids_in IN varchar2)
   RETURN number
IS
   cnumber number;
BEGIN
   cnumber := getBaselineExprCount(experiment_in, query_assaygroupids_in, null, null);
   RETURN cnumber;                              
EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;

-- A specific query with no genes specified across an experiment partition, e.g. 
--    Sub-experiment (mus musculus):
--    select getSubBslnExprSpfcCnt('E-GEOD-30352','g12','g9,g10,g11,g12,g13,g14') from dual;
CREATE OR REPLACE Function getSubBslnExprSpfcCnt
   ( experiment_in IN varchar2, query_assaygroupids_in IN varchar2, subexp_assaygroupids_in IN varchar2)
   RETURN number
IS
   cnumber number;
BEGIN
   cnumber := getBaselineExprCount(experiment_in, query_assaygroupids_in, subexp_assaygroupids_in, null);
   RETURN cnumber;                              
EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;

-- A specific query for genes in a whole experiment, e.g. 
--    Whole experiment, specific query (liver:g2 and lung:g5)
--    select getWholBslnExprSpfcCntForGenes('E-MTAB-599','g5','ENSMUSG00000022097,ENSMUSG00000024653,ENSMUSG00000064356') from dual;
--    select getWholBslnExprSpfcCntForGenes('E-MTAB-599','g2,g5','ENSMUSG00000022097,ENSMUSG00000024653,ENSMUSG00000064356') from dual;
--    select getWholBslnExprSpfcCntForGenes('E-MTAB-599','g5','ENSMUSG00000022097') from dual;
--    select getWholBslnExprSpfcCntForGenes('E-MTAB-599','g2','ENSMUSG00000064356') from dual;
CREATE OR REPLACE Function getWholBslnExprSpfcCntForGenes
   ( experiment_in IN varchar2, query_assaygroupids_in IN varchar2, geneids_in IN varchar2)
   RETURN number
IS
   cnumber number;
BEGIN
   cnumber := getBaselineExprCount(experiment_in, query_assaygroupids_in, null, geneids_in);
   RETURN cnumber;                              
EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;

-- A specific query for genes in an experiment partition:
--    Sub-experiment (mus musculus), specific query (heart:g9, liver:g12) 
--    select getSubBslnExprSpfcCntForGenes('E-GEOD-30352','g9,g12','g9,g10,g11,g12,g13,g14','ENSMUSG00000054422,ENSMUSG00000066154,ENSMUSG00000018593') from dual; 
--    select getSubBslnExprSpfcCntForGenes('E-GEOD-30352','g9','g9,g10,g11,g12,g13,g14','ENSMUSG00000054422,ENSMUSG00000066154,ENSMUSG00000018593') from dual;
--    select getSubBslnExprSpfcCntForGenes('E-GEOD-30352','g12','g9,g10,g11,g12,g13,g14','ENSMUSG00000054422,ENSMUSG00000066154,ENSMUSG00000018593') from dual;
CREATE OR REPLACE Function getSubBslnExprSpfcCntForGenes
   ( experiment_in IN varchar2, query_assaygroupids_in IN varchar2, subexp_assaygroupids_in IN varchar2, geneids_in IN varchar2)
   RETURN number
IS
   cnumber number;
BEGIN
   cnumber := getBaselineExprCount(experiment_in, query_assaygroupids_in, subexp_assaygroupids_in, geneids_in);
   RETURN cnumber;                              
EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;

create or replace type IDENTIFIERS_TABLE as table of varchar2(255 byte);

exit;