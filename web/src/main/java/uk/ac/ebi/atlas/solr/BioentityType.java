/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.solr;

import com.google.common.collect.Sets;

import java.util.Set;

public enum BioentityType {

    GENE("genes", "ensgene", "mirna"),
    PROTEIN("proteins", "ensprotein"),
    TRANSCRIPT("genes", "enstranscript");

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