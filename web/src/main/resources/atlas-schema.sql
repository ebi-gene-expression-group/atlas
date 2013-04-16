CREATE TABLE IF NOT EXISTS experiment_transcripts(
    experiment_accession VARCHAR(50) NOT NULL,
    gene_id VARCHAR(255) NOT NULL,
    transcript_profiles VARCHAR NOT NULL,
    PRIMARY KEY (experiment_accession, gene_id)
);