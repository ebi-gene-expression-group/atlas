package uk.ac.ebi.atlas.commands.download;

interface HeaderBuilder {

    String GENE_NAME = "Gene name";
    String GENE_ID = "Gene Id";
    String DESIGN_ELEMENT = "Design Element";

    String[] buildHeader(String[] header);
}
