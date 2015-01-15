package uk.ac.ebi.atlas.search.baseline;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineAnalyticsSearchDaoIT {

    @Inject
    BaselineAnalyticsSearchDao subject;

    @Test
    public void fetchFacetsForHomoSapiens() {
        String json = subject.fetchFacets("species:\"Homo sapiens\"", 0.5);

        ReadContext jsonCtx = JsonPath.parse(json);

        List<Integer> count = jsonCtx.read("$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].count");
        List<String> species = jsonCtx.read("$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].species.buckets[*].val");
        List<String> sourcesForHomoSapiens = jsonCtx.read("$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].species.buckets[?(@.val=='Homo sapiens')].defaultQueryFactorType.buckets[*].val");
        List<String> experimentsForHomoSapiensOrganismPart = jsonCtx.read("$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].species.buckets[?(@.val=='Homo sapiens')].defaultQueryFactorType.buckets[?(@.val=='ORGANISM_PART')].experimentAccession.buckets[*].val");

        assertThat(count, contains(456088));
        assertThat(species, contains("Homo sapiens"));
        assertThat(sourcesForHomoSapiens, contains("ORGANISM_PART", "CELL_LINE"));
        assertThat(experimentsForHomoSapiensOrganismPart, contains("E-MTAB-1733", "E-MTAB-513"));
    }

    @Test
    public void constructQueryParameters() {
        assertThat(subject.constructQueryParameters("identifierSearch:ENSG00000126549", 0.5), is("query?q=identifierSearch:ENSG00000126549&rows=0&omitHeader=true&fq=expressionLevel:%5B0.5%20TO%20*%5D"));

    }
}