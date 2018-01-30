DROP TABLE scxa_marker_genes CASCADE;

CREATE TABLE IF NOT EXISTS scxa_marker_genes
(
    experiment_accession VARCHAR(255)     NOT NULL,
    gene_id              VARCHAR(255)     NOT NULL,
    k                    INTEGER          NOT NULL,
    cluster_id           INTEGER          NOT NULL,
    p                    DOUBLE PRECISION NOT NULL
);

-- TODO Evaluate performance hit if index is unique --
-- CREATE UNIQUE INDEX IF NOT EXISTS scxa_marker_genes_experiment_accession_gene_id_k_index
-- ON scxa_marker_genes (experiment_accession, gene_id, k); --


CREATE INDEX IF NOT EXISTS scxa_marker_genes_experiment_accession_gene_id_k_index
    ON scxa_marker_genes (experiment_accession, gene_id, k);

