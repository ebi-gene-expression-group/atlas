package uk.ac.ebi.atlas.solr.query.conditions;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

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
//        try {
//            QueryResponse queryResponse = conditionsSolrServer.query(queryBuilder.buildFullTestSearchQuery(queryString));
//            List<ConditionProperty> beans = queryResponse.getBeans(ConditionProperty.class);
//
//            Collection<IndexedContrast> result = Collections2.transform(beans, new Function<ConditionProperty, IndexedContrast>() {
//                @Override
//                public IndexedContrast apply(ConditionProperty conditionProperty) {
//                    return new IndexedContrast(conditionProperty.getExperimentAccession(), conditionProperty.getContrastId());
//                }
//            });
//
//            return result;
//        } catch (SolrServerException e) {
//            throw new IllegalStateException("Conditions index query failed!", e);
//        }
        return Lists.newArrayList();
    }

}
