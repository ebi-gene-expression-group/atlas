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

package uk.ac.ebi.atlas.solr.query.builders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * This is a builder, keep always in mind that builders are potentially stateful.
 * If you need to build different query strings within the same client process
 * you must use a new instance of builder for each query.
 */
public class BioentityIdentifierQueryBuilder extends SolrQueryBuilder<BioentityIdentifierQueryBuilder>{
    private static final Logger LOGGER = LogManager.getLogger(BioentityIdentifierQueryBuilder.class);

    public static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    public static final String PROPERTY_SEARCH_FIELD = "property_value_search";
    public static final String PROPERTY_LOWER_FIELD = "property_value_lower";
    private static final int MAX_GENE_IDS_TO_FETCH = 1000000;

    private boolean applyOrOnQueryStringContent;
    private String queryString;
    private String propertyName = PROPERTY_LOWER_FIELD; //by default we want exact match

    public BioentityIdentifierQueryBuilder withExactMatch(boolean exactMatch){
        propertyName = exactMatch ? PROPERTY_LOWER_FIELD : PROPERTY_SEARCH_FIELD;
        return this;
    }

    public BioentityIdentifierQueryBuilder forQueryString(String queryString, boolean applyOrOnQueryStringContent){
        this.queryString = queryString.replace(":", "\\:").replace("[", "\\[").replace("]", "\\]");
        this.applyOrOnQueryStringContent = applyOrOnQueryStringContent;
        return this;
    }

    public SolrQuery build() {

        StringBuilder queryConditionBuilder = new StringBuilder(propertyName).append(":");

        if (applyOrOnQueryStringContent){
            // use default "lucene" parser and set default operator to OR and default field to propertyName
            queryConditionBuilder.insert(0, "{!lucene q.op=OR df=" + propertyName + "}(").append(queryString).append(")");
        } else {
            queryConditionBuilder.append("\"").append(queryString).append("\"");
        }

        queryStringBuilder.insert(0, queryConditionBuilder);

        return buildQueryObject(queryStringBuilder.toString());
    }

    private SolrQuery buildQueryObject(String queryString) {

        SolrQuery solrQuery = new SolrQuery(queryString);
        solrQuery.setFields(BIOENTITY_IDENTIFIER_FIELD);
        solrQuery.setParam("group", true);
        solrQuery.setParam("group.field", BIOENTITY_IDENTIFIER_FIELD);
        solrQuery.setParam("group.main", true);
        solrQuery.setRows(MAX_GENE_IDS_TO_FETCH);

        LOGGER.trace("<buildQueryObject> solr query: " + solrQuery.toString());

        return solrQuery;
    }

    @Override
    protected BioentityIdentifierQueryBuilder getThis() {
        return this;
    }
}
