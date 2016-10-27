package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.Validate;

public enum BioentityPropertyName {
    UNKNOWN,
    GENE_BIOTYPE("gene_biotype", true, true, true, false, false),
    HGNC_SYMBOL("hgnc_symbol", true, true),
    RGD_SYMBOL("rgd_symbol", true, true),
    FLYBASENAME_GENE("flybasename_gene", false, true),
    SYMBOL("symbol", true, true, true, false, true),
    MIRBASE_NAME("mirbase_name", true, true),
    SYNONYM("synonym", true, true, true, false, false),
    DESCRIPTION("description", false, true, true, false, false),
    MGI_ID("mgi_id", true, true, true, false, false),
    MGI_DESCRIPTION("mgi_description", false, true, true, false, false),
    GOTERM("goterm", false, true, false, false, true),
    GO("go", true, true, true, true, false),
    POTERM("poterm", false, true, false, false, true),
    PO("po", true, true, true, false, false),
    INTERPROTERM("interproterm", false, true),
    INTERPRO("interpro", true, true, true, true, false),
    PATHWAYNAME("pathwayname", false, true),
    PATHWAYID("pathwayid", true, true),
    UNIPROT("uniprot", true, true, true, false, false),
    DESIGN_ELEMENT("design_element", true, true, true, false, false),
    ORTHOLOG("ortholog", false, false, true, false, false),
    MIRBASE_ACCESSION("mirbase_accession", false, false, true, false, false),
    ENSGENE("ensgene", false, false, true, false, false),
    ENTREZGENE("entrezgene", false, false, true, false, false),
    ENSFAMILY_DESCRIPTION("ensfamily_description", false, false, true, false, false),
    MIRBASE_ID("mirbase_id", false, false, true, false, false),
    MIRBASE_SEQUENCE("mirbase_sequence", false, false, true, false, false);

    private String name;
    private boolean isId;
    private boolean forAnalyticsIndex;

    //TODO remove these because I'm being silly
    private boolean forGenePage;
    private boolean forGeneSetPage;
    private boolean forExperimentRowTooltip;

    static private ImmutableMap<String, BioentityPropertyName> propertiesByName;

    static {
        ImmutableMap.Builder<String, BioentityPropertyName> b = ImmutableMap.builder();
        for (BioentityPropertyName v : values()) {
            b.put(v.name, v);
        }
        propertiesByName = b.build();
    }

    public static boolean isIdentifierSearchKeyword(String propertyName) {
        BioentityPropertyName n = propertiesByName.get(propertyName.toLowerCase());
        return n != null && n.forAnalyticsIndex && n.isId;
    }

    public static boolean includeInIdentifierSearchAsText(String propertyName) {
        BioentityPropertyName n = propertiesByName.get(propertyName.toLowerCase());
        return n != null && !n.isId && n.forAnalyticsIndex;
    }


    BioentityPropertyName() {
        this("", false, false);
    }

    BioentityPropertyName(String name, boolean isId, boolean forAnalyticsIndex) {
        this(name, isId, forAnalyticsIndex, false, false, false);
    }

    BioentityPropertyName(String name, boolean isId, boolean forAnalyticsIndex, boolean forGenePage, boolean
            forGeneSetPage, boolean forExperimentRowToolTip) {
        /*an id has to be for the analytics index
         because use a notion of being an "id" to:
            - store it in a keyword field there
            - suggest it for searches
            - retrieve it quickly
        */
        Validate.isTrue(!(isId && !forAnalyticsIndex));
        this.name = name;
        this.isId = isId;
        this.forAnalyticsIndex = forAnalyticsIndex;
        this.forGenePage = forGenePage;
        this.forGeneSetPage = forGeneSetPage;
        this.forExperimentRowTooltip = forExperimentRowToolTip;
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

