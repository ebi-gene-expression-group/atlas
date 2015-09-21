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

import org.apache.solr.client.solrj.SolrQuery;

/**
 * This is a builder, keep always in mind that builders are potentially stateful.
 * If you need to build different query strings within the same client process
 * you must use a new instance of builder for each query.
 */
public class FacetedPropertyValueQueryBuilder extends SolrQueryBuilder<FacetedPropertyValueQueryBuilder>{

    private static final int DEFAULT_LIMIT = 15;
    public static final String PROPERTY_LOWER_FIELD = "property_value_lower";

    public SolrQuery buildPropertyValueAutocompleteQuery(String queryString) {
        String solrQueryString = "property_value_edgengram:\"" + queryString + "\"" + queryStringBuilder.toString();
        return buildQueryObject(solrQueryString);
    }

    public SolrQuery buildBioentityQuery(String bioentityId) {
        String solrQueryString = "bioentity_identifier:\"" + bioentityId + "\"" + queryStringBuilder.toString();
        return buildQueryObject(solrQueryString);
    }

    private SolrQuery buildQueryObject(String queryString) {
        SolrQuery solrQuery = new SolrQuery(queryString);

        solrQuery.addFacetField(PROPERTY_LOWER_FIELD);
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.setFacetLimit(DEFAULT_LIMIT);
        solrQuery.setFacetMinCount(1);

        return solrQuery;
    }

    @Override
    protected FacetedPropertyValueQueryBuilder getThis() {
        return this;
    }
}
