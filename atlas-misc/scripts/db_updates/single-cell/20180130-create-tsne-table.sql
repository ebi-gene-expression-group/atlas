DROP TABLE scxa_tsne;

CREATE TABLE IF NOT EXISTS scxa_tsne
(
  experiment_accession VARCHAR(255) NOT NULL,
  cell_id              VARCHAR(255) NOT NULL,
  x                    DOUBLE PRECISION,
  y                    DOUBLE PRECISION,
  perplexity           INTEGER      NOT NULL,
  CONSTRAINT scxa_tsne_experiment_accession_cell_id_perplexity_pk
  PRIMARY KEY (experiment_accession, cell_id, perplexity)
);
