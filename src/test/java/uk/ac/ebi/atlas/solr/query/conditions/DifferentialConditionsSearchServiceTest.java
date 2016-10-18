package uk.ac.ebi.atlas.solr.query.conditions;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;

import java.io.IOException;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class DifferentialConditionsSearchServiceTest {

    @Mock
    SolrClient solrClient;

    @Mock
    ConditionsSolrQueryBuilder queryBuilder;

    @Mock
    QueryResponse queryResponse;

    @Mock
    SolrQuery solrQuery;

    DifferentialConditionsSearchService subject;

    String q = "dibenzazepine";

    @Before
    public void setUp() throws IOException, SolrServerException {
        MockitoAnnotations.initMocks(this);

        when(queryBuilder.build(q)).thenReturn(solrQuery);

        when(solrClient.query(solrQuery, SolrRequest.METHOD.POST)).thenReturn(queryResponse);


        subject = new DifferentialConditionsSearchService(solrClient, queryBuilder);

    }

    @Test
    public void test() throws IOException, SolrServerException {
        subject.findContrasts(q);

        Mockito.verify(queryBuilder).build(q);
        Mockito.verify(solrClient).query(solrQuery, SolrRequest.METHOD.POST);
        Mockito.verify(queryResponse).getBeans(DifferentialCondition.class);

        verifyNoMoreInteractions(queryBuilder,solrClient,solrQuery,queryResponse);


    }
}
