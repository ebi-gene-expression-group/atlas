package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineAnalyticsSearchDaoIT {

    public static final String SPECIES_HOMO_SAPIENS = "species:\"homo sapiens\"";
    @Inject
    BaselineAnalyticsSearchDao subject;

    @Test
    public void buildQueryUrl() {
        assertThat(subject.buildQueryUrl(SPECIES_HOMO_SAPIENS, 0.5, 0), Matchers.endsWith("solr/analytics/query?q=species:%22homo%20sapiens%22&rows=0&omitHeader=true&" +
                "fq=(experimentType:rnaseq_mrna_baseline%20AND%20expressionLevel:%5B0.5%20TO%20*%5D)%20OR%20(experimentType:proteomics_baseline%20AND%20expressionLevel" +
                ":%5B0%20TO%20*%5D)&json.facet=%7B%22experimentType%22:%7B%22terms%22:%7B%22field%22:%22experimentType%22,%22facet%22:%7B%22species%22:%7B%22terms%22:" +
                "%7B%22field%22:%22species%22,%22facet%22:%7B%22defaultQueryFactorType%22:%7B%22terms%22:%7B%22field%22:%22defaultQueryFactorType%22,%22facet%22:%7B%22" +
                "experimentAccession%22:%7B%22terms%22:%7B%22field%22:%22experimentAccession%22,%22facet%22:%7B%22assayGroupId%22:%7B%22terms%22:%7B%22field%22:%22assayGroupId%22" +
                ",%22limit%22:1000,%22facet%22:%7B%22sumExpressionLevel%22:%22sum(expressionLevel)%22%7D%7D%7D,%22uniqueIdentifiers%22:%22unique(bioentityIdentifier)" +
                "%22%7D%7D%7D%7D%7D%7D%7D%7D%7D%7D%7D%7D%7D"));
    }

    @Test
    public void fetchFacetsForHomoSapiens() {
        String json = subject.fetchFacets(SPECIES_HOMO_SAPIENS, 0.5, 0);

        ReadContext jsonCtx = JsonPath.parse(json);

        List<Integer> count = jsonCtx.read("$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].count");
        List<String> species = jsonCtx.read("$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].species.buckets[*].val");
        List<String> sourcesForHomoSapiens = jsonCtx.read("$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].species.buckets[?(@.val=='homo sapiens')].defaultQueryFactorType.buckets[*].val");
        List<String> experimentsForHomoSapiensOrganismPart = jsonCtx.read("$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].species.buckets[?(@.val=='homo sapiens')].defaultQueryFactorType.buckets[?(@.val=='ORGANISM_PART')].experimentAccession.buckets[*].val");

        assertThat(count, hasSize(1));
        assertThat(count.get(0), is(greaterThan(30000)));
        assertThat(species, contains("homo sapiens"));
        assertThat(sourcesForHomoSapiens, hasItems("ORGANISM_PART", "CELL_LINE"));
        // TODO E-MTAB-513 isn’t included until https://www.pivotaltracker.com/story/show/101118548
        assertThat(experimentsForHomoSapiensOrganismPart, containsInAnyOrder("E-MTAB-1733", "E-MTAB-2836", "E-GEOD-30352"));
    }

}