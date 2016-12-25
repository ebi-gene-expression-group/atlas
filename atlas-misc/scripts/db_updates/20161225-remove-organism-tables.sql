-- Back up old tables/views
alter table ORGANISM_KINGDOM rename to ORGANISM_K_OLD;
alter table ORGANISM_ENSEMBLDB rename to ORGANISM_ENSDB_OLD;
alter table BIOENTITY_ORGANISM rename to ORGANISM_BO_OLD;

-- drop table ORGANISM_K_OLD;
-- drop table ORGANISM_ENSDB_OLD;
-- drop table ORGANISM_BO_OLD;
