package uk.ac.ebi.atlas.geneannotation.mirna;

public class MiRNAEntity {

    private String identifier;
    private String accession;
    private String organism;
    private String name;
    private String sequence;

    public MiRNAEntity(String identifier, String accession, String organism, String name) {
        this.identifier = identifier;
        this.accession = accession;
        this.organism = organism;
        this.name = name;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getAccession() {
        return accession;
    }

    public String getOrganism() {
        return organism;
    }

    public String getName() {
        return name;
    }

    public String getSequence() {
        return sequence;
    }
}
