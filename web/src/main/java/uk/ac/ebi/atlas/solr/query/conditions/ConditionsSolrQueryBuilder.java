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

package uk.ac.ebi.atlas.solr.query.conditions;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

@Named
@Scope("prototype")
public class ConditionsSolrQueryBuilder {

    public static final String CONDITIONS_SEARCH_FIELD = "conditions_search";

    public SolrQuery buildFullTestSearchQuery(String queryString) {

        SolrQuery solrQuery = new SolrQuery(buildQueryString(queryString));
        return solrQuery;
    }

    String buildQueryString(String queryString){
        return CONDITIONS_SEARCH_FIELD + ":\"" + queryString.trim()+"\"";
    }
}
