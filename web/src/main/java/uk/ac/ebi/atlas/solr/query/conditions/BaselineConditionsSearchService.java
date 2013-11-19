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
import com.google.common.collect.*;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

@Named
@Scope("singleton")
public class BaselineConditionsSearchService {

    private SolrServer baselineConditionsSolrServer;

    private ConditionsSolrQueryBuilder queryBuilder;

    @Inject
    public BaselineConditionsSearchService(@Qualifier("baselineConditionsSolrServer") SolrServer baselineConditionsSolrServer, ConditionsSolrQueryBuilder queryBuilder) {
        this.baselineConditionsSolrServer = baselineConditionsSolrServer;
        this.queryBuilder = queryBuilder;
    }

    public Collection<IndexedAssayGroup> findAssayGroups(String queryString) {

        try {
            QueryResponse queryResponse = baselineConditionsSolrServer.query(queryBuilder.buildFullTestSearchQuery(queryString));
            List<Condition> beans = queryResponse.getBeans(Condition.class);

            Collection<IndexedAssayGroup> result = Collections2.transform(beans, new Function<Condition, IndexedAssayGroup>() {
                @Override
                public IndexedAssayGroup apply(Condition conditionProperty) {
                    return new IndexedAssayGroup(conditionProperty.getExperimentAccession(), conditionProperty.getAssayGroupId());
                }
            });

            return Sets.newHashSet(result);
        } catch (SolrServerException e) {
            throw new IllegalStateException("Conditions index query failed!", e);
        }
    }

    public SetMultimap<String, String> findAssayGroupsPerExperiment(String queryString) {

           try {
               QueryResponse queryResponse = baselineConditionsSolrServer.query(queryBuilder.buildFullTestSearchQuery(queryString));
               List<Condition> beans = queryResponse.getBeans(Condition.class);

               SetMultimap<String, String> result = HashMultimap.create();
               for (Condition condition : beans) {
                    result.put(condition.getExperimentAccession(), condition.getAssayGroupId());
               }

               return result;
           } catch (SolrServerException e) {
               throw new IllegalStateException("Conditions index query failed!", e);
           }
       }
}
