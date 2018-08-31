package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.resource.DataFileHubFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MicroarrayExperimentConfigurationIT {

    @Inject
    private DataFileHubFactory dataFileHubFactory;

    private MicroarrayExperimentConfiguration subject;

    @Before
    public void setUp() {
        subject =
                new ConfigurationTrader(dataFileHubFactory.getGxaDataFileHub())
                        .getMicroarrayExperimentConfiguration("E-GEOD-13316");
    }

    @Test
    public void testGetArrayDesignNames() {
        assertThat(subject.getArrayDesignAccessions().size(), greaterThan(0));
    }

    @Test
    public void testGetContrasts() {
        List<Contrast> contrasts = subject.getContrasts();
        assertThat(contrasts.size(), greaterThan(0));
        for (Contrast contrast: contrasts) {
            assertNotNull(contrast.getId());
            assertNotNull(contrast.getDisplayName());
            assertThat(contrast.getReferenceAssayGroup().assaysAnalyzedForThisDataColumn(), is(not(empty())));
            assertThat(contrast.getTestAssayGroup().assaysAnalyzedForThisDataColumn(), is(not(empty())));
        }
    }
}
