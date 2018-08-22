package uk.ac.ebi.atlas.solr.analytics.differential;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DifferentialAnalyticsSearchDaoIT {

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private DifferentialAnalyticsSearchDao subject;

    @Test
    public void differentialSearchWithUrlParams() {
        String json =
                subject.fetchResultsAboveDefaultFoldChange(
                        SemanticQuery.create("zinc finger"),
                        SemanticQuery.create(),
                        speciesFactory.create("mus musculus").getReferenceName());

        ReadContext jsonCtx = JsonPath.parse(json);

        List<String> speciesFromJson = jsonCtx.read("$.response.docs[*].species");
        List<String> experimentTypesFromJson = jsonCtx.read("$.response.docs[*].experiment_type");

        assertThat(speciesFromJson, hasItem("mus musculus"));
        assertThat(experimentTypesFromJson, hasItem("MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL"));

    }
}
