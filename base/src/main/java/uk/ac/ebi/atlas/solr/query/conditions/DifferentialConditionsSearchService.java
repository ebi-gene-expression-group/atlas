package uk.ac.ebi.atlas.solr.query.conditions;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Named
public class DifferentialConditionsSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialConditionsSearchService.class);

    private SolrClient differentialConditionsSolrClient;

    private ConditionsSolrQueryBuilder queryBuilder;

    @Inject
    public DifferentialConditionsSearchService(@Qualifier("solrClientDifferentialConditions") SolrClient solrClient, ConditionsSolrQueryBuilder queryBuilder) {
        this.differentialConditionsSolrClient = solrClient;
        this.queryBuilder = queryBuilder;
    }

    public Collection<IndexedAssayGroup> findContrasts(String queryString) {

        try {

            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();

            QueryResponse queryResponse = differentialConditionsSolrClient.query(queryBuilder.build(queryString), SolrRequest.METHOD.POST);
            List<DifferentialCondition> beans = queryResponse.getBeans(DifferentialCondition.class);

            stopWatch.stop();
            LOGGER.info("<findContrasts: {}> {} results, took {} seconds", queryString, beans.size(), stopWatch.getTotalTimeSeconds());

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
