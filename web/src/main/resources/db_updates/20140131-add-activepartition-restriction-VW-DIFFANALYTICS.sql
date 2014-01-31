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
where mda.ISACTIVE='T'
union all
select rda.IDENTIFIER, null, bn.NAME AS NAME, o.name AS ORGANISM, rda.EXPERIMENT, rda.CONTRASTID, rda.ISACTIVE, rda.PVAL, rda.LOG2FOLD, null
from RNASEQ_DIFF_ANALYTICS subpartition( BELOW_DEFAULT_FDR ) rda
join BIOENTITY_NAME bn on rda.IDENTIFIER=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
join EXPERIMENT_ORGANISM eo on o.name = eo.bioentity_organism and eo.experiment = rda.experiment
where rda.ISACTIVE='T';