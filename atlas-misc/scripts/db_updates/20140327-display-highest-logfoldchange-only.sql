DROP MATERIALIZED VIEW VW_DIFFANALYTICS;
CREATE MATERIALIZED VIEW VW_DIFFANALYTICS
NOLOGGING PARALLEL 16
BUILD DEFERRED
REFRESH COMPLETE ON DEMAND
ENABLE QUERY REWRITE AS
select IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT
from (
select dem.IDENTIFIER, bn.NAME AS NAME, o.NAME AS ORGANISM, mda.EXPERIMENT, mda.CONTRASTID, mda.ISACTIVE, mda.PVAL, mda.LOG2FOLD, mda.TSTAT
,rank() over(partition by mda.EXPERIMENT, mda.CONTRASTID, dem.IDENTIFIER order by(abs(mda.LOG2FOLD)) desc) as lfrank
from MICROARRAY_DIFF_ANALYTICS subpartition( BELOW_DEFAULT_FDR ) mda
join DESIGNELEMENT_MAPPING dem on mda.designelement=dem.designelement and mda.arraydesign = dem.arraydesign
join BIOENTITY_NAME bn on dem.identifier=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
where abs(mda.LOG2FOLD) >= 1
) where lfrank = 1
union all
select rda.IDENTIFIER, bn.NAME AS NAME, o.name AS ORGANISM, rda.EXPERIMENT, rda.CONTRASTID, rda.PVAL, rda.LOG2FOLD, null
from RNASEQ_DIFF_ANALYTICS subpartition( BELOW_DEFAULT_FDR ) rda
join BIOENTITY_NAME bn on rda.IDENTIFIER=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
join EXPERIMENT_ORGANISM eo on o.name = eo.bioentity_organism and eo.experiment = rda.experiment
where abs(rda.LOG2FOLD) >= 1;

-- run to here in first step, then run the following in a separate second step:

exec dbms_mview.refresh( 'VW_DIFFANALYTICS', 'C' );