-- Table for storing single cell expression levels
DROP TABLE scxa_analytics CASCADE;
CREATE TABLE scxa_analytics (
    experiment_accession VARCHAR(255) NOT NULL,
    gene_id VARCHAR(255) NOT NULL,
    cell_id  VARCHAR(255) NOT NULL,
    expression_level DOUBLE PRECISION
);

-- CREATE UNIQUE INDEX scxa_analytics__uindex ON scxa_analytics (experiment_accession, gene_id, cell_id); --