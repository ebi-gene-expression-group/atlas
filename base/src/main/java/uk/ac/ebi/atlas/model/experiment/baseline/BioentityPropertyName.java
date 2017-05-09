package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public enum BioentityPropertyName {
    UNKNOWN,
    BIOENTITY_IDENTIFIER("bioentityIdentifier", false),
    DESCRIPTION("description", false),
    DESIGN_ELEMENT("design_element", true),
    ENSFAMILY_DESCRIPTION("ensfamily_description", false, "Ensembl Family"),
    ENSGENE("ensgene", true, "Ensembl Gene"),
    ENSPROTEIN("ensprotein", true, "Ensembl Protein"),
    ENSTRANSCRIPT("enstranscript", true, "Ensembl Transcript"),
    ENTREZGENE("entrezgene", false, "Entrez"),
    FLYBASENAME_GENE("flybasename_gene", false),
    GENE_BIOTYPE("gene_biotype", true),
    GO("go", true,"Gene Ontology"),
    GOTERM("goterm", false, "Gene Ontology"),
    HGNC_SYMBOL("hgnc_symbol", true),
    IDENTIFIER_SEARCH("identifierSearch", false),
    INTERPRO("interpro", true,"InterPro"),
    INTERPROTERM("interproterm", false, "InterPro"),
    MGI_DESCRIPTION("mgi_description", false),
    MGI_ID("mgi_id", true),
    MIRBASE_ACCESSION("mirbase_accession", false),
    MIRBASE_ID("mirbase_id", false, "miRBase Identifier"),
    MIRBASE_NAME("mirbase_name", true),
    MIRBASE_SEQUENCE("mirbase_sequence", false, "miRBase Sequence"),
    ORTHOLOG("ortholog", false),
    PATHWAYID("pathwayid", true,"Pathway Id"),
    PATHWAYNAME("pathwayname", false, "Pathway Name"),
    PO("po", true,"Plant Ontology"),
    POTERM("poterm", false, "Plant Ontology"),
    REACTOME("reactome", false),
    RGD_SYMBOL("rgd_symbol", true),
    SYMBOL("symbol", true),
    SYNONYM("synonym", true),
    UNIPROT("uniprot", true, "UniProt"),
    WBPSGENE("wbpsgene", false, "WBPS Gene"),
    WBPSTRANSCRIPT("wbpstranscript", true, "WBPS Transcript"),
    WBPSPROTEIN("wbpsprotein", true, "WBPS Protein");   //not used for analytics index now

    public final String name;
    public final String label;
    public final boolean isId;

    static private ImmutableMap<String, BioentityPropertyName> propertiesByName;

    static {
        ImmutableMap.Builder<String, BioentityPropertyName> b = ImmutableMap.builder();
        for (BioentityPropertyName v : values()) {
            b.put(v.name, v);
        }
        propertiesByName = b.build();
    }

    public String asAnalyticsIndexKeyword(){
        return MessageFormat.format("keyword_{0}",name);
    }

//    public static BioentityPropertyName getByAnalyticsIndexKeyword(String keywordName){
//        return getByName(keywordName.replace("keyword_", ""));
//    }

    public static BioentityPropertyName getByName(String propertyName){
        if (Strings.isNullOrEmpty(propertyName)){
            return UNKNOWN;
        }
        BioentityPropertyName n = propertiesByName.get(propertyName.toLowerCase());
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
