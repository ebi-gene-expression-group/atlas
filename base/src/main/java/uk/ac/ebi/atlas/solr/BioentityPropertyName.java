package uk.ac.ebi.atlas.solr;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum BioentityPropertyName {
    UNKNOWN("", false, ""),
    BIOENTITY_IDENTIFIER("bioentity_identifier", false, "Bioentity Identifier"),
    DESCRIPTION("description", false, "Description"),
    DESIGN_ELEMENT("design_element", true, "Design element"),
    ENSFAMILY_DESCRIPTION("ensfamily_description", false, "Ensembl family"),
    ENSGENE("ensgene", true, "Ensembl gene"),
    ENSPROTEIN("ensprotein", true, "Ensembl protein"),
    ENSTRANSCRIPT("enstranscript", true, "Ensembl transcript"),
    ENTREZGENE("entrezgene", false, "Entrez"),
    FLYBASE_GENE_ID("flybase_gene_id", false, "FlyBase gene ID"),
    GENE_BIOTYPE("gene_biotype", true, "Gene biotype"),
    GO("go", true,"Gene ontology"),
    GOTERM("goterm", false, "Gene ontology term"),
    HGNC_SYMBOL("hgnc_symbol", true, "HGNC symbol"),
    INTERPRO("interpro", true, "InterPro"),
    INTERPROTERM("interproterm", false, "InterPro term"),
    MGI_DESCRIPTION("mgi_description", false, "MGI description"),
    MGI_ID("mgi_id", true, "MGI ID"),
    MIRBASE_ACCESSION("mirbase_accession", false, "miRBase accession"),
    MIRBASE_ID("mirbase_id", false, "miRBase ID"),
    MIRBASE_NAME("mirbase_name", true, "miRBase name"),
    MIRBASE_SEQUENCE("mirbase_sequence", false, "miRBase Sequence"),
    ORTHOLOG("ortholog", false, "Ortholog"),
    PATHWAYID("pathwayid", true,"Reactome pathway ID"),
    PATHWAYNAME("pathwayname", false, "Pathway name"),
    PO("po", true,"Plant ontology"),
    POTERM("poterm", false, "Plant ontology term"),
    RGD_SYMBOL("rgd_symbol", true, "RGD symbol"),
    SYMBOL("symbol", true, "Symbol"),
    SYNONYM("synonym", true, "Synonym"),
    UNIPROT("uniprot", true, "UniProt"),
    WBPSGENE("wbpsgene", false, "WBPS gene"),
    WBPSTRANSCRIPT("wbpstranscript", true, "WBPS transcript"),
    WBPSPROTEIN("wbpsprotein", true, "WBPS protein");   // Not used for analytics index now

    final static private ImmutableMap<String, BioentityPropertyName> PROPERTIES_BY_NAME =
            ImmutableMap.copyOf(Arrays.stream(values()).collect(Collectors.toMap(v -> v.name, v -> v)));

    public final String name;
    public final boolean isKeyword;
    public final String label;

    BioentityPropertyName(String name, boolean isKeyword, String label) {
        this.name = name;
        this.isKeyword = isKeyword;
        this.label = label;
    }

    public static BioentityPropertyName getByName(String propertyName) {
        return propertyName == null ? UNKNOWN : PROPERTIES_BY_NAME.getOrDefault(propertyName.toLowerCase(), UNKNOWN);
    }
}
