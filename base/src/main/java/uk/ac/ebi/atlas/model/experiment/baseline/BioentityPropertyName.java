package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum BioentityPropertyName {
    UNKNOWN,
    BIOENTITY_IDENTIFIER("bioentity_identifier", false),
    DESCRIPTION("description", false),
    DESIGN_ELEMENT("design_element", true),
    ENSFAMILY_DESCRIPTION("ensfamily_description", false, "Ensembl Family"),
    ENSGENE("ensgene", true, "Ensembl Gene"),
    ENSPROTEIN("ensprotein", true, "Ensembl Protein"),
    ENSTRANSCRIPT("enstranscript", true, "Ensembl Transcript"),
    ENTREZGENE("entrezgene", false, "Entrez"),
    FLYBASE_GENE_ID("flybase_gene_id", false),
    GENE_BIOTYPE("gene_biotype", true),
    GO("go", true,"Gene Ontology"),
    GOTERM("goterm", false, "Gene Ontology"),
    HGNC_SYMBOL("hgnc_symbol", true),
    IDENTIFIER_SEARCH("identifier_search", false),
    INTERPRO("interpro", true,"InterPro"),
    INTERPROTERM("interproterm", false, "InterPro"),
    MGI_DESCRIPTION("mgi_description", false),
    MGI_ID("mgi_id", true),
    MIRBASE_ACCESSION("mirbase_accession", false),
    MIRBASE_ID("mirbase_id", false, "miRBase Identifier"),
    MIRBASE_NAME("mirbase_name", true),
    MIRBASE_SEQUENCE("mirbase_sequence", false, "miRBase Sequence"),
    ORTHOLOG("ortholog", false),
    PATHWAYID("pathwayid", true,"Reactome Pathway"),
    PATHWAYNAME("pathwayname", false, "Pathway Name"),
    PO("po", true,"Plant Ontology"),
    POTERM("poterm", false, "Plant Ontology"),
    RGD_SYMBOL("rgd_symbol", true),
    SYMBOL("symbol", true),
    SYNONYM("synonym", true),
    UNIPROT("uniprot", true, "UniProt"),
    WBPSGENE("wbpsgene", false, "WBPS Gene"),
    WBPSTRANSCRIPT("wbpstranscript", true, "WBPS Transcript"),
    WBPSPROTEIN("wbpsprotein", true, "WBPS Protein");   //not used for analytics index now

    final static private ImmutableMap<String, BioentityPropertyName> PROPERTIES_BY_NAME =
            ImmutableMap.copyOf(Arrays.stream(values()).collect(Collectors.toMap(v -> v.name, v -> v)));

    public final String name;
    public final String label;
    public final boolean isId;

    public String asAnalyticsIndexKeyword() {
        return MessageFormat.format("keyword_{0}", name);
    }

    public static BioentityPropertyName getByName(String propertyName) {
        if (StringUtils.isBlank(propertyName)) {
            return UNKNOWN;
        }
        BioentityPropertyName n = PROPERTIES_BY_NAME.get(propertyName.toLowerCase());
        return n == null ? UNKNOWN : n;
    }

    BioentityPropertyName() {
        this("", false);
    }

    BioentityPropertyName(String name, boolean isId) {
        this(name, isId, StringUtils.capitalize(name.replace("_", " ")));
    }

    BioentityPropertyName(String name, boolean isId, String label) {
        this.name = name;
        this.isId = isId;
        this.label = label;
    }
}
