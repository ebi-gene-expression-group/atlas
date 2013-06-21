CREATE TABLE IF NOT EXISTS bioentity_name(
    bioentityid INT NOT NULL auto_increment,
    identifier VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    organism  VARCHAR(50),
    PRIMARY KEY (identifier, bioentityid)
);
