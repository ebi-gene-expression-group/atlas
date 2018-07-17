package uk.ac.ebi.atlas.solr;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum BioentityPropertyName {
    UNKNOWN("", false, "", 0),
    BIOENTITY_IDENTIFIER("bioentity_identifier", false, "Bioentity Identifier", 0),
    DESCRIPTION("description", false, "Description", 0),
    DESIGN_ELEMENT("design_element", true, "Design element", 0),
    ENSFAMILY_DESCRIPTION("ensfamily_description", false, "Ensembl family", 0),
    ENSGENE("ensgene", true, "Ensembl gene", 100),
    ENSPROTEIN("ensprotein", true, "Ensembl protein", 0),
    ENSTRANSCRIPT("enstranscript", true, "Ensembl transcript", 0),
    ENTREZGENE("entrezgene", false, "Entrez", 80),
    FLYBASE_GENE_ID("flybase_gene_id", false, "FlyBase gene ID", 80),
    GENE_BIOTYPE("gene_biotype", true, "Gene biotype", 80),
    GO("go", true,"Gene ontology", 0),
    GOTERM("goterm", false, "Gene ontology term", 0),
    HGNC_SYMBOL("hgnc_symbol", true, "HGNC symbol", 80),
    INTERPRO("interpro", true, "InterPro", 0),
    INTERPROTERM("interproterm", false, "InterPro term", 0),
    MGI_ID("mgi_id", true, "MGI ID", 80),
    MGI_DESCRIPTION("mgi_description", false, "MGI description", 0),
    MGI_SYMBOL("mgi_symbol", false, "MGI symbol", 80),
    MIRBASE_ACCESSION("mirbase_accession", false, "miRBase accession", 0),
    MIRBASE_ID("mirbase_id", false, "miRBase ID", 0),
    MIRBASE_NAME("mirbase_name", true, "miRBase name", 0),
    MIRBASE_SEQUENCE("mirbase_sequence", false, "miRBase Sequence", 0),
    ORTHOLOG("ortholog", false, "Ortholog", 0),
    PATHWAYID("pathwayid", true,"Reactome pathway ID", 0),
    PATHWAYNAME("pathwayname", false, "Pathway name", 0),
    PO("po", true,"Plant ontology", 0),
    POTERM("poterm", false, "Plant ontology term", 0),
    RGD_SYMBOL("rgd_symbol", true, "RGD symbol", 0),
    SYMBOL("symbol", true, "Symbol", 90),
    SYNONYM("synonym", true, "Synonym", 0),
    UNIPROT("uniprot", true, "UniProt", 80),
    WBPSGENE("wbpsgene", true, "WBPS gene", 0),
    WBPSTRANSCRIPT("wbpstranscript", true, "WBPS transcript", 0),
    WBPSPROTEIN("wbpsprotein", true, "WBPS protein", 0);

    final static private ImmutableMap<String, BioentityPropertyName> PROPERTIES_BY_NAME =
            ImmutableMap.copyOf(Arrays.stream(values()).collect(Collectors.toMap(v -> v.name, v -> v)));

    public final String name;
    public final boolean isKeyword;
    public final String label;
    public final int idWeight;

    BioentityPropertyName(String name, boolean isKeyword, String label, int idWeight) {
        this.name = name.toLowerCase();
        this.isKeyword = isKeyword;
        this.label = label;
        this.idWeight = idWeight;
    }

    public static BioentityPropertyName getByName(String propertyName) {
        return propertyName == null ? UNKNOWN : PROPERTIES_BY_NAME.getOrDefault(propertyName.toLowerCase(), UNKNOWN);
    }
}
