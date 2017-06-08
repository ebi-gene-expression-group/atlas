-- Table for storing single cell expression levels
DROP TABLE SINGLE_CELL_EXPRESSION CASCADE;
CREATE TABLE SINGLE_CELL_EXPRESSION (
    geneid VARCHAR(255) NOT NULL,
    experimentaccession VARCHAR(255) NOT NULL,
    cellid  VARCHAR(255) NOT NULL,
    expressionlevel FLOAT(35),
    PRIMARY KEY (geneId, experimentAccession, cellId)
);