package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineAnalyticsSearchDaoTest {

    private static final String SOLR_BASE_URL = "http://TEST";
    private static final String JSON_FACET = "{}";

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private SimpleClientHttpRequestFactory simpleClientHttpRequestFactoryMock;

    private BaselineAnalyticsSearchDao subject;

    @Before
    public void setUp() {
        doNothing().when(simpleClientHttpRequestFactoryMock).setReadTimeout(anyInt());
        doNothing().when(simpleClientHttpRequestFactoryMock).setConnectTimeout(anyInt());
        when(restTemplateMock.getRequestFactory()).thenReturn(simpleClientHttpRequestFactoryMock);

        subject = new BaselineAnalyticsSearchDao(restTemplateMock, SOLR_BASE_URL, JSON_FACET);
    }

    @Test
    public void buildQueryParameters() {
        assertThat(subject.buildQueryParameters("identifierSearch:ENSG00000126549"), is("query?q=identifierSearch:ENSG00000126549&rows=0&omitHeader=true&fq=experimentType:(rnaseq_mrna_baseline%20OR%20proteomics_baseline)"));
    }

}