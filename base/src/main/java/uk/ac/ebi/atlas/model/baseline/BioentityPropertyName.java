package uk.ac.ebi.atlas.model.baseline;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import java.text.MessageFormat;

public enum BioentityPropertyName {

    UNKNOWN,
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
    GOTERM("goterm", false),
    GO("go", true),
    POTERM("poterm", false),
    PO("po", true),
    INTERPROTERM("interproterm", false),
    INTERPRO("interpro", true),
    PATHWAYNAME("pathwayname", false),
    PATHWAYID("pathwayid", true),
    UNIPROT("uniprot", true),
    DESIGN_ELEMENT("design_element", true),
    ORTHOLOG("ortholog", false),
    MIRBASE_ACCESSION("mirbase_accession", false),
    ENSGENE("ensgene", false),
    ENTREZGENE("entrezgene", false),
    ENSFAMILY_DESCRIPTION("ensfamily_description", false),
    MIRBASE_ID("mirbase_id", false),
    MIRBASE_SEQUENCE("mirbase_sequence", false);

    public final String name;
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
        this.name = name;
        this.isId = isId;
    }

}
