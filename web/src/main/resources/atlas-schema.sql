CREATE TABLE IF NOT EXISTS experiment_transcripts(
    experiment_accession VARCHAR(50) NOT NULL,
    gene_id VARCHAR(255) NOT NULL,
    transcript_id VARCHAR(255) NOT NULL,
    transcript_expressions ARRAY NOT NULL,
    PRIMARY KEY (experiment_accession, gene_id, transcript_id)
);

CREATE TABLE IF NOT EXISTS experiment_configuration(
    experiment_accession VARCHAR(50) NOT NULL,
    experiment_type VARCHAR(50) NOT NULL,
    PRIMARY KEY (experiment_accession)
);

CREATE TABLE IF NOT EXISTS bioentity_name(
    bioentityid INT NOT NULL auto_increment,
    identifier VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    organism  VARCHAR(50),
    type  VARCHAR(50),
    PRIMARY KEY (bioentityid)
);
CREATE INDEX IF NOT EXISTS bioentity_name_identifier ON bioentity_name(identifier);

CREATE TABLE IF NOT EXISTS designelement_mapping(
    designelement VARCHAR(50) NOT NULL,
    identifier VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL,
    arraydesign  VARCHAR(50),
    PRIMARY KEY (designelement, arraydesign)
);