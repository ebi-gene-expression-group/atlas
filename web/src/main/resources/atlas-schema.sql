CREATE TABLE IF NOT EXISTS experiment_transcripts(
    experiment_accession VARCHAR(50) NOT NULL,
    gene_id VARCHAR(255) NOT NULL,
    transcript_profiles BLOB,
    PRIMARY KEY (experiment_accession, gene_id)
);