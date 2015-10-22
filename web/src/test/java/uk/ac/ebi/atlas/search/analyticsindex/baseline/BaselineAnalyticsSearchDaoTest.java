package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BaselineAnalyticsSearchDaoTest {

    private static final String SOLR_BASE_URL = "http://TEST";
    private static final String JSON_FACET = "{}";

    @Mock
    private RestTemplate restTemplate;

    private final BaselineAnalyticsSearchDao subject = new BaselineAnalyticsSearchDao(restTemplate, SOLR_BASE_URL, JSON_FACET);

    @Test
    public void buildQueryParameters() {
        assertThat(subject.buildQueryParameters("identifierSearch:ENSG00000126549", 0.5, 0), is("query?q=identifierSearch:ENSG00000126549&rows=0&omitHeader=true&fq=(experimentType:rnaseq_mrna_baseline%20AND%20expressionLevel:%5B0.5%20TO%20*%5D)%20OR%20(experimentType:proteomics_baseline%20AND%20expressionLevel:%5B0%20TO%20*%5D)"));
    }

}