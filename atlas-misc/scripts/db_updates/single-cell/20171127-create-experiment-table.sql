DROP TABLE scxa_experiment CASCADE;

CREATE TABLE IF NOT EXISTS scxa_experiment
(
  accession   VARCHAR(255) NOT NULL
    CONSTRAINT scxa_experiment_pkey
    PRIMARY KEY,
  type        VARCHAR(50)  NOT NULL,
  species     VARCHAR(255) NOT NULL,
  access_key  CHAR(36)     NOT NULL,
  private     BOOLEAN DEFAULT TRUE,
  last_update DATE    DEFAULT ('now' :: TEXT) :: DATE,
  pubmed_ids  VARCHAR(255),
  title       VARCHAR(500)
);

---

CREATE VIEW scxa_public_experiment AS
  SELECT
    scxa_experiment.accession,
    scxa_experiment.type,
    scxa_experiment.last_update
  FROM scxa_experiment
  WHERE (scxa_experiment.private IS FALSE);
