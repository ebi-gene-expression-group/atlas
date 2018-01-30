DROP TABLE scxa_marker_genes CASCADE;

CREATE TABLE IF NOT EXISTS scxa_marker_genes
(
    experiment_accession VARCHAR(255)     NOT NULL,
    gene_id              VARCHAR(255)     NOT NULL,
    k                    INTEGER          NOT NULL,
    cluster_id           INTEGER          NOT NULL,
    p                    DOUBLE PRECISION NOT NULL,
    CONSTRAINT scxa_marker_genes_experiment_accession_gene_id_k_pk
    PRIMARY KEY (experiment_accession, gene_id, k)
);
