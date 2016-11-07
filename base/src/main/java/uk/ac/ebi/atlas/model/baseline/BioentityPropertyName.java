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

    public static BioentityPropertyName getByAnalyticsIndexKeyword(String keywordName){
        return getByName(keywordName.replace("keyword_", ""));
    }

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




        /*

        index.property_names.bioentity_name=symbol
index.property_names.synonym=synonym
index.property_names.identifier=pathwayid,gene_biotype,embl,ensfamily,ensgene,ensprotein,enstranscript,entrezgene,flybase_gene_id,flybase_transcript_id,go,interpro,rgd,mgi_id,mirbase_accession,mirbase_id,refseq,unigene,uniprot,mirbase_name,design_element,po
index.property_names.tooltip=synonym,goterm,interproterm
index.property_names.identifier.search=gene_biotype,hgnc_symbol,rgd_symbol,flybasename_gene,symbol,mirbase_name,synonym,description,mgi_id,mgi_description,goterm,go,poterm,po,interproterm,interpro,pathwayname,pathwayid,uniprot,design_element
index.property_names.genepage=symbol,description,synonym,ortholog,go,po,interpro,ensfamily_description,ensgene,entrezgene,uniprot,mgi_id,mgi_description,gene_biotype,mirbase_accession,mirbase_id,mirbase_sequence,design_element
index.property_names.genesetpage=reactome,go,po,interpro,plant_reactome
         */
}

