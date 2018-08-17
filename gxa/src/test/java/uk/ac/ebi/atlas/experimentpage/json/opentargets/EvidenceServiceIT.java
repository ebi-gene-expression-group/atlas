package uk.ac.ebi.atlas.experimentpage.json.opentargets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;

import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class EvidenceServiceIT {
    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    private MicroarrayProfileStreamFactory microarrayProfileStreamFactory;

    @Inject
    private DataFileHub dataFileHub;

    private EvidenceService<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile>
            subject;

    @Before
    public void setUp() {
        subject = new EvidenceService<>(microarrayProfileStreamFactory, dataFileHub, "test");
    }

    @Test
    public void organismPartIsIncludedIfAvailable() {
        ImmutableList.Builder<JsonObject> listBuilder = ImmutableList.builder();
        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment("E-GEOD-12685");
        subject.evidenceForExperiment(experiment, contrast -> {
            MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
            requestPreferences.setFoldChangeCutoff(0.0);
            requestPreferences.setCutoff(1.0);
            requestPreferences.setHeatmapMatrixSize(1000);
            requestPreferences.setSelectedColumnIds(ImmutableSet.of(contrast.getId()));
            return new MicroarrayRequestContext(requestPreferences, experiment);
        }, listBuilder::add);

        for (JsonObject jsonObject : listBuilder.build()) {
            assertThat(
                    jsonObject.get("evidence").getAsJsonObject().get("organism_part").getAsString(),
                    is(not(emptyString())));
        }
    }
}
