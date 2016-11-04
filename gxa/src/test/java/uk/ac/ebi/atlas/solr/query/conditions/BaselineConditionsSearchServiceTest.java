package uk.ac.ebi.atlas.solr.query.conditions;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;

import java.io.IOException;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BaselineConditionsSearchServiceTest {

    @Mock
    SolrClient solrClient;

    @Mock
    ConditionsSolrQueryBuilder queryBuilder;

    @Mock
    QueryResponse queryResponse;

    @Mock
    SolrQuery solrQuery;

    BaselineConditionsSearchService subject;

    public void setUp(String q) throws IOException, SolrServerException {
        MockitoAnnotations.initMocks(this);

        when(queryBuilder.build(q)).thenReturn(solrQuery);

        //The current code is undecided which one is better. :)
        when(solrClient.query(solrQuery)).thenReturn(queryResponse);
        when(solrClient.query(solrQuery, SolrRequest.METHOD.POST)).thenReturn(queryResponse);

        subject = new BaselineConditionsSearchService(solrClient, queryBuilder);

    }

    @Test
    public void findAssayGroups() throws IOException, SolrServerException {
        String q = "liver";
        setUp(q);
        subject.findAssayGroups(q);

        Mockito.verify(queryBuilder).build(q);
        Mockito.verify(solrClient).query(solrQuery);
        Mockito.verify(queryResponse).getBeans(Condition.class);

        verifyNoMoreInteractions(queryBuilder, solrClient, solrQuery, queryResponse);
    }

    @Test
    public void findAssayGroupsPerExperiment() throws IOException, SolrServerException {
        String q = "EFO:0001265";
        setUp(q);

        subject.findAssayGroupsPerExperiment(q);

        Mockito.verify(queryBuilder).build(q);
        Mockito.verify(solrClient).query(solrQuery, SolrRequest.METHOD.POST);
        Mockito.verify(queryResponse).getBeans(Condition.class);

        verifyNoMoreInteractions(queryBuilder, solrClient, solrQuery, queryResponse);
    }


}
