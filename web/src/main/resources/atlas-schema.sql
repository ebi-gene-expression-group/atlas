CREATE TABLE IF NOT EXISTS experiment_transcripts(
    experiment_accession VARCHAR(255) NOT NULL,
    gene_id VARCHAR(255) NOT NULL,
    transcript_id VARCHAR(255) NOT NULL,
    transcript_expressions ARRAY NOT NULL,
    PRIMARY KEY (experiment_accession, gene_id, transcript_id)
);
-- to be removed remove after migration deployment
CREATE TABLE IF NOT EXISTS experiment_configuration(
    experiment_accession VARCHAR(255) NOT NULL,
    experiment_type VARCHAR(50) NOT NULL,
    PRIMARY KEY (experiment_accession)
);
--

CREATE TABLE IF NOT EXISTS experiment(
    experiment_accession VARCHAR(255) NOT NULL,
    experiment_type VARCHAR(50) NOT NULL,
    private BOOLEAN NOT NULL DEFAULT TRUE,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (experiment_accession)
);

CREATE TABLE IF NOT EXISTS bioentity_name(
    identifier VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    organism  VARCHAR(255),
    type VARCHAR(50),
    PRIMARY KEY (identifier, organism, type)
);

CREATE TABLE IF NOT EXISTS designelement_mapping(
    designelement VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    arraydesign  VARCHAR(255),
    PRIMARY KEY (designelement, arraydesign)
);

CREATE OR REPLACE VIEW public_experiment AS
SELECT experiment_accession, experiment_type, last_update
FROM experiment WHERE PRIVATE=false;

-- migrate public experiments
MERGE INTO experiment (experiment_accession, experiment_type, private)
SELECT experiment_accession, experiment_type,false
FROM experiment_configuration;
COMMIT;

-- enable the next statement only after successful migration
-- DROP TABLE IF EXIST experiment_configuration