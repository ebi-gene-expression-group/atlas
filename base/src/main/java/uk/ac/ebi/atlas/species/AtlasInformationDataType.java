package uk.ac.ebi.atlas.species;

public enum AtlasInformationDataType {
  ENSEMBL("ensembl"),
  GENOMES("ensembl_genomes"),
  PARASITE("wormbase_parasite"),
  EFO("efo");

  private final String id;

  AtlasInformationDataType(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

}
