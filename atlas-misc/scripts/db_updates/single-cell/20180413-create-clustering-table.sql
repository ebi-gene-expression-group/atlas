DROP TABLE scxa_cell_clusters;

CREATE TABLE IF NOT EXISTS scxa_cell_clusters
(
  experiment_accession VARCHAR(255) NOT NULL,
  cell_id              VARCHAR(255) NOT NULL,
  k                    INTEGER NOT NULL,
  cluster_id           INTEGER NOT NULL,
  CONSTRAINT scxa_cell_clusters_experiment_accession_cell_id_k_pk
  PRIMARY KEY (experiment_accession, k, cell_id)
);
