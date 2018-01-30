DROP TABLE scxa_tsne;

CREATE TABLE IF NOT EXISTS scxa_tsne
(
  experiment_accession VARCHAR(255) NOT NULL,
  cell_id              VARCHAR(255) NOT NULL,
  perplexity           INTEGER      NOT NULL
  x                    DOUBLE PRECISION,
  y                    DOUBLE PRECISION
);

CREATE INDEX IF NOT EXISTS scxa_tsne_experiment_accession_cell_id_perplexity_index
  ON scxa_tsne (experiment_accession, cell_id, perplexity);

