package uk.ac.ebi.atlas.species;

import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import java.util.Arrays;

public enum AtlasInformationDataType {
  ENSEMBL("ensembl"),
  GENOMES("genomes"),
  PARASITE("paraSite"),
  EFO("efo");

  private final String id;

  AtlasInformationDataType(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

}
