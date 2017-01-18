package uk.ac.ebi.atlas.solr;

import com.google.common.collect.Sets;

import java.util.Set;

public enum BioentityType {

    GENE("genes", "ensgene", "mirna", "wbpsgene"),
    PROTEIN("proteins", "ensprotein", "wbpsprotein"),
    TRANSCRIPT("genes", "enstranscript", "wbpstranscript");

    private static Set<String> allSolrAliases = Sets.newHashSet();
    private final String bioentityPageName;


    static {
        for (BioentityType bioentityType:BioentityType.values()) {
            allSolrAliases.addAll(bioentityType.getSolrAliases());
        }
    }

    private Set<String> solrAliases;

    BioentityType(String bioentityPageName, String... solrAliases){
        this.bioentityPageName = bioentityPageName;
        this.solrAliases = Sets.newHashSet(solrAliases);
    }

    public static BioentityType get(String solrAlias){
        for (BioentityType bioentityType : BioentityType.values()){
            if (bioentityType.solrAliases.contains(solrAlias)){
                return bioentityType;
            }
        }
        throw new IllegalArgumentException("Unknown bioentityType solrAlias: " + solrAlias);
    }

    public static Set<String> getAllSolrAliases() {
        return Sets.newHashSet(allSolrAliases);
    }

    public Set<String> getSolrAliases(){
        return solrAliases;
    }

    public String getBioentityPageName() {
        return bioentityPageName;
    }
}