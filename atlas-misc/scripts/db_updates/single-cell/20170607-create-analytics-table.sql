DROP TABLE scxa_analytics CASCADE;

CREATE TABLE IF NOT EXISTS scxa_analytics
(
    experiment_accession VARCHAR(255)     NOT NULL,
    gene_id              VARCHAR(255)     NOT NULL,
    cell_id              VARCHAR(255)     NOT NULL,
    expression_level     DOUBLE PRECISION,
    CONSTRAINT gene_id_experiment_accession_cell_id
    PRIMARY KEY (gene_id, experiment_accession, cell_id)
);
