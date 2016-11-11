
package uk.ac.ebi.atlas.solr.query.conditions;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Named
@Scope("singleton")
public class BaselineConditionsSearchService {

    private SolrClient baselineConditionsSolrClient;

    private ConditionsSolrQueryBuilder queryBuilder;

    @Inject
    public BaselineConditionsSearchService(@Qualifier("solrClientBaselineConditions") SolrClient baselineConditionsSolrClient, ConditionsSolrQueryBuilder queryBuilder) {
        this.baselineConditionsSolrClient = baselineConditionsSolrClient;
        this.queryBuilder = queryBuilder;
    }

    Collection<IndexedAssayGroup> findAssayGroups(String queryString) {

        try {
            QueryResponse queryResponse = baselineConditionsSolrClient.query(queryBuilder.build(queryString));
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
        } catch (IOException e) {
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }

    public SetMultimap<String, String> findAssayGroupsPerExperiment(String queryString) {

           try {
               SolrQuery solrQuery = queryBuilder.build(queryString);
               QueryResponse queryResponse = baselineConditionsSolrClient.query(solrQuery, SolrRequest.METHOD.POST);
               List<Condition> beans = queryResponse.getBeans(Condition.class);

               SetMultimap<String, String> result = HashMultimap.create();
               for (Condition condition : beans) {
                    result.put(condition.getExperimentAccession(), condition.getAssayGroupId());
               }

               return result;
           } catch (SolrServerException e) {
               throw new IllegalStateException("Conditions index query failed!", e);
           } catch (IOException e) {
               throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
           }
       }
}
