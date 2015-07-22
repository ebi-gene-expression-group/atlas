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
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Named
@Scope("singleton")
public class DifferentialConditionsSearchService {

    private static final Logger LOGGER = Logger.getLogger(DifferentialConditionsSearchService.class);

    private SolrClient differentialConditionsSolrClient;

    private ConditionsSolrQueryBuilder queryBuilder;

    @Inject
    public DifferentialConditionsSearchService(SolrClient differentialConditionsSolrClient, ConditionsSolrQueryBuilder queryBuilder) {
        this.differentialConditionsSolrClient = differentialConditionsSolrClient;
        this.queryBuilder = queryBuilder;
    }

    public Collection<IndexedAssayGroup> findContrasts(String queryString) {

        try {

            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();

            QueryResponse queryResponse = differentialConditionsSolrClient.query(queryBuilder.build(queryString), SolrRequest.METHOD.POST);
            List<DifferentialCondition> beans = queryResponse.getBeans(DifferentialCondition.class);

            stopWatch.stop();
            LOGGER.info(String.format("<findContrasts: %s> %s results, took %s seconds", queryString, beans.size(), stopWatch.getTotalTimeSeconds()));

            return Collections2.transform(beans, new Function<DifferentialCondition, IndexedAssayGroup>() {
                @Override
                public IndexedAssayGroup apply(DifferentialCondition conditionProperty) {
                    return new IndexedAssayGroup(conditionProperty.getExperimentAccession(), conditionProperty.getContrastId());
                }
            });
        } catch (SolrServerException e) {
            throw new IllegalStateException("Conditions index query failed!", e);
        } catch (IOException e) {
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }

}
