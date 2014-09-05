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

import org.apache.commons.lang.StringUtils;
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

    public static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    public static final String SPECIES_FIELD = "species";

    public static final String PROPERTY_LOWER_FIELD = "property_value_lower";

    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    private GxaSolrServer solrServer;

    @Inject
    public SpeciesLookupService(BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer,
                                GxaSolrServer solrServer) {
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
        this.solrServer = solrServer;
    }

    public String fetchSpeciesByBioentityId(String identifier) {
        return fetchSpeciesByField(BIOENTITY_IDENTIFIER_FIELD, identifier);
    }

    public String fetchSpeciesByPropertyValue(String propertyValue) {
        return fetchSpeciesByField(null, propertyValue);
    }

    public String fetchSpeciesByField(String fieldName, String fieldValue) {
        List<String> propertyValueTokens = bioentityPropertyValueTokenizer.split(fieldValue);
        for (String propertyValueToken : propertyValueTokens) {
            Collection<String> species = executeSpeciesQuery(propertyValueToken, fieldName);
            if (!species.isEmpty()) {
                return species.iterator().next();
            }
        }
        throw new ResourceNotFoundException("Species can't be determined for " + fieldName + ":" + fieldValue);
    }

    Collection<String> executeSpeciesQuery(String propertyValue, String propertyName) {
        SolrQuery query;
        if (StringUtils.isBlank(propertyName)) {
            // eg: property_value_lower:"IPR027417"
            query = new SolrQuery(PROPERTY_LOWER_FIELD + ":\"" + propertyValue + "\"");
        } else {
            query = new SolrQuery(propertyName + ":" + propertyValue);
        }

        //fields to be returned, ie: fl=species
        query.setFields(SPECIES_FIELD);
        query.setRows(100);

        return solrServer.query(query, SPECIES_FIELD, false);
    }

}