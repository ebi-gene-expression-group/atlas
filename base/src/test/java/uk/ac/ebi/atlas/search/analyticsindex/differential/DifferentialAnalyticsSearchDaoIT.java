package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class DifferentialAnalyticsSearchDaoIT {

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private DifferentialAnalyticsSearchDao subject;

    @Test
    public void differentialSearchWithUrlParams() {
        String json = subject.fetchDifferentialResultsAboveDefaultFoldChangeForQuery(SemanticQuery.create("zinc finger"), SemanticQuery.create(), speciesFactory.create("mus musculus").mappedName);

        ReadContext jsonCtx = JsonPath.parse(json);

        List<String> speciesFromJson = jsonCtx.read("$.response.docs[*].species");
        List<String> experimentTypesFromJson = jsonCtx.read("$.response.docs[*].experimentType");

        assertThat(speciesFromJson, hasItem("mus musculus"));
        assertThat(experimentTypesFromJson, hasItem("MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL"));

    }

}
