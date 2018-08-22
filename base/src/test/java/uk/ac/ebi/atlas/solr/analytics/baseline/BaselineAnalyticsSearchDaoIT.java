package uk.ac.ebi.atlas.solr.analytics.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BaselineAnalyticsSearchDaoIT {
    @Inject
    private BaselineAnalyticsSearchDao subject;

    // In case of failure check:
    // https://gist.github.com/alfonsomunozpomer/1ed495e8ea26d1f11655beef00e109e7
    // kinase is a popular protein and at least one experiment should have it
    @Test
    public void fetchExpressionLevelsFromSolr() {
        String species = "homo sapiens";
        String defaultQueryFactorType = "ORGANISM_PART";
        List<Map<String, Object>> result =
                subject.fetchExpressionLevelsPayload(
                        SemanticQuery.create("kinase"), SemanticQuery.create(), species, defaultQueryFactorType);

        assertThat(result.size(), greaterThan(0));

        for (Map<String, Object> m : result) {
            for (String key: ImmutableList.of("val", "uniqueIdentifiers", "assayGroupId")) {
                assertNotNull(m.get(key));
            }
        }
    }
}
