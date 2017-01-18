package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public enum BioentityPropertyName {
    UNKNOWN,
    BIOENTITY_IDENTIFIER("bioentityIdentifier", false),
    IDENTIFIER_SEARCH("identifierSearch", false),
    GENE_BIOTYPE("gene_biotype", true),
    HGNC_SYMBOL("hgnc_symbol", true),
    RGD_SYMBOL("rgd_symbol", true),
    FLYBASENAME_GENE("flybasename_gene", false),
    SYMBOL("symbol", true),
    MIRBASE_NAME("mirbase_name", true),
    SYNONYM("synonym", true),
    DESCRIPTION("description", false),
    MGI_ID("mgi_id", true),
    MGI_DESCRIPTION("mgi_description", false),
    GOTERM("goterm", false, "Gene Ontology"),
    GO("go", true,"Gene Ontology"),
    POTERM("poterm", false, "Plant Ontology"),
    PO("po", true,"Plant Ontology"),
    INTERPROTERM("interproterm", false, "InterPro"),
    INTERPRO("interpro", true,"InterPro"),
    PATHWAYNAME("pathwayname", false, "Pathway Name"),
    PATHWAYID("pathwayid", true,"Pathway Id"),
    UNIPROT("uniprot", true, "UniProt"),
    DESIGN_ELEMENT("design_element", true),
    ORTHOLOG("ortholog", false),
    MIRBASE_ACCESSION("mirbase_accession", false),
    ENSGENE("ensgene", false,"Ensembl Gene"),
    WBPSGENE("wbpsgene", false, "WBPS Gene"),
    ENTREZGENE("entrezgene", false, "Entrez"),
    ENSFAMILY_DESCRIPTION("ensfamily_description", false, "Ensembl Family"),
    MIRBASE_ID("mirbase_id", false, "miRBase Identifier"),
    MIRBASE_SEQUENCE("mirbase_sequence", false, "miRBase Sequence"),
    REACTOME("reactome", false), //does not come from the files- currently,fetched from Uniprot
    ENSTRANSCRIPT("enstranscript", true, "Ensembl Transcript"), ENSPROTEIN("ensprotein", true, "Ensembl Protein"),
    WBPSTRANSCRIPT("wbpstranscript", true, "WBPS Transcript"), WBPSPROTEIN("wbpsprotein", true, "WBPS Protein");//not used for analytics index now

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
