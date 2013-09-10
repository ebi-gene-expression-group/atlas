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

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionProperty;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

@Named
@Scope("singleton")
public class ConditionsSearchService {

    private SolrServer conditionsSolrServer;

    private ConditionsSolrQueryBuilder queryBuilder;

    @Inject
    public ConditionsSearchService(SolrServer conditionsSolrServer, ConditionsSolrQueryBuilder queryBuilder) {
        this.conditionsSolrServer = conditionsSolrServer;
        this.queryBuilder = queryBuilder;
    }

    public Collection<IndexedContrast> findContrasts(String queryString) {
        try {
            QueryResponse queryResponse = conditionsSolrServer.query(queryBuilder.buildFullTestSearchQuery(queryString));
            List<ConditionProperty> beans = queryResponse.getBeans(ConditionProperty.class);

            Collection<IndexedContrast> result = Collections2.transform(beans, new Function<ConditionProperty, IndexedContrast>() {
                @Override
                public IndexedContrast apply(ConditionProperty conditionProperty) {
                    return new IndexedContrast(conditionProperty.getExperimentAccession(), conditionProperty.getContrastId());
                }
            });

            return result;
        } catch (SolrServerException e) {
            throw new IllegalStateException("Conditions index query failed!", e);
        }
    }

}
