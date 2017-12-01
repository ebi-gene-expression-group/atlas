DROP TABLE scxa_experiment CASCADE;
CREATE TABLE scxa_experiment(
  accession VARCHAR(255) NOT NULL,
  type VARCHAR(50) NOT NULL,
  species VARCHAR(255) NOT NULL,
  access_key CHAR(36) NOT NULL,
  private BOOLEAN DEFAULT TRUE,
  last_update DATE DEFAULT CURRENT_DATE,
  pubmed_ids VARCHAR(255),
  title VARCHAR(500),
  PRIMARY KEY (ACCESSION)
);

CREATE OR REPLACE VIEW scxa_public_experiment AS
  SELECT accession, type, last_update
  FROM scxa_experiment WHERE private IS FALSE;
