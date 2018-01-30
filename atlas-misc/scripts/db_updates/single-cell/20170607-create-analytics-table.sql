DROP TABLE scxa_analytics CASCADE;

CREATE TABLE IF NOT EXISTS scxa_analytics
(
    experiment_accession VARCHAR(255)     NOT NULL,
    gene_id              VARCHAR(255)     NOT NULL,
    cell_id              VARCHAR(255)     NOT NULL,
    expression_level     DOUBLE PRECISION
);

-- TODO Evaluate performance hit if index is unique --
-- CREATE UNIQUE INDEX IF NOT EXISTS scxa_analytics__uindex
-- ON scxa_analytics (experiment_accession, gene_id, cell_id); --

-- CREATE UNIQUE INDEX IF NOT EXISTS scxa_analytics__uindex
-- ON scxa_analytics (experiment_accession, gene_id, cell_id); --