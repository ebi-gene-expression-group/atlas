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

package uk.ac.ebi.atlas.solr.query;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

@Named
@Scope("singleton")
//can be singleton because HttpSolrServer is documented to be thread safe, please be careful not to add any other non thread safe state!
public class SpeciesLookupService {

    private static final Logger LOGGER = Logger.getLogger(SpeciesLookupService.class);

    public static final String SPECIES_FIELD = "species";
    public static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    public static final String PROPERTY_LOWER_FIELD = "property_value_lower";

    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    private GxaSolrServer solrServer;

    @Inject
    public SpeciesLookupService(BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer,
                                GxaSolrServer solrServer) {
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
        this.solrServer = solrServer;
    }

    // used for looking up species for a gene/protein/transcript/mirna/etc. id
    public String fetchSpeciesForBioentityId(String identifier) {
        // eg: bioentity_identifier:ENSMUSG00000021789
        return fetchSpeciesByField(BIOENTITY_IDENTIFIER_FIELD, identifier);
    }

    // used for looking up species for gene sets (goterm, interpro, react etc.)
    public String fetchSpeciesForGeneSet(String propertyValue) {
        // eg: property_value_lower:"IPR027417"
        return fetchSpeciesByField(PROPERTY_LOWER_FIELD, propertyValue);
    }

    public String fetchSpeciesByField(String fieldName, String query) {
        List<String> queryTokens = bioentityPropertyValueTokenizer.split(query);
        for (String queryToken : queryTokens) {
            Collection<String> species = executeSpeciesQuery(fieldName, encloseInQuotes(queryToken));
            if (!species.isEmpty()) {
                return species.iterator().next();
            }
        }
        throw new ResourceNotFoundException("Species can't be determined for " + fieldName + ":" + query);
    }

    // surround in quotes, so queries with special chars work, eg: "GO:0003674"
    private String encloseInQuotes(String queryToken) {
        return "\"" + queryToken + "\"";
    }

    Collection<String> executeSpeciesQuery(String fieldName, String queryToken) {
        LOGGER.debug("lookup species for " + fieldName + ":" + queryToken);

        SolrQuery query = new SolrQuery(fieldName + ":" + queryToken);

        //fields to be returned, ie: fl=species
        query.setFields(SPECIES_FIELD);
        query.setRows(100);

        return solrServer.query(query, SPECIES_FIELD, false);
    }

}