CREATE TABLE IF NOT EXISTS experiment_transcripts(
    experiment_accession VARCHAR(255) NOT NULL,
    gene_id VARCHAR(255) NOT NULL,
    transcript_id VARCHAR(255) NOT NULL,
    transcript_expressions ARRAY NOT NULL,
    PRIMARY KEY (experiment_accession, gene_id, transcript_id)
);

CREATE TABLE IF NOT EXISTS experiment_configuration(
    experiment_accession VARCHAR(255) NOT NULL,
    experiment_type VARCHAR(50) NOT NULL,
    PRIMARY KEY (experiment_accession)
);

CREATE TABLE IF NOT EXISTS bioentity_name(
    identifier VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    organism  VARCHAR(255),
    type VARCHAR(50),
    PRIMARY KEY (identifier, organism, type)
);
CREATE INDEX IF NOT EXISTS bioentity_name_identifier ON bioentity_name(identifier);

CREATE TABLE IF NOT EXISTS designelement_mapping(
    designelement VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    arraydesign  VARCHAR(255),
    PRIMARY KEY (designelement, arraydesign)
);