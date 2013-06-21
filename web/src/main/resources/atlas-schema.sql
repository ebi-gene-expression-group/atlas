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
