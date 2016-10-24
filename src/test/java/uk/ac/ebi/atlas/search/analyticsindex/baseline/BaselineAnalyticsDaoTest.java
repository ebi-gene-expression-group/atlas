package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BaselineAnalyticsDaoTest {

    private static final String SPECIES_HOMO_SAPIENS = "species:\"homo sapiens\"";

    private final String solrBaseUrl = "<solr_base_url>";
    private final String baselineFacetsQuery = "<baseline_facets_query>";

    @Mock
    RestTemplate restTemplate;

    BaselineAnalyticsDao subject ;


    @Before
    public void setUp(){
        subject = new BaselineAnalyticsDao(restTemplate, solrBaseUrl, baselineFacetsQuery);
    }

    @Test
    public void buildQueryParameters() {
        assertThat(subject.buildQueryParameters("identifierSearch:ENSG00000126549"), is("query?q=identifierSearch:ENSG00000126549&rows=0&omitHeader=true&fq=experimentType:(rnaseq_mrna_baseline%20OR%20proteomics_baseline)"));
    }

    @Test
    public void buildQueryUrl() {
        assertThat(subject.buildQueryUrl(SPECIES_HOMO_SAPIENS), Matchers.endsWith
                (solrBaseUrl+"query?q=species:%22homo%20sapiens%22&rows=0&omitHeader=true" +
                "&fq=experimentType:(rnaseq_mrna_baseline%20OR%20proteomics_baseline)" +"<baseline_facets_query>"));
    }
}
