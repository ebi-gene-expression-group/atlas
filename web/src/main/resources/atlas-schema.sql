CREATE TABLE IF NOT EXISTS bioentity_name(
    identifier VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    organism  VARCHAR(255),
    type VARCHAR(50),
    PRIMARY KEY (identifier, organism, type)
);

CREATE TABLE IF NOT EXISTS designelement_mapping(
    designelement VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    arraydesign  VARCHAR(255),
    PRIMARY KEY (designelement, arraydesign)
);