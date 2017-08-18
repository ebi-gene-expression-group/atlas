package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentsInfoListServiceTest {

    private static final String EXPECTED_JSON =
          "{\n" +
          "  \"aaData\": [\n" +
          "    {\n" +
          "      \"experimentType\": \"RNASEQ_MRNA_BASELINE\",\n" +
          "      \"experimentAccession\": \"accession\",\n" +
          "      \"experimentDescription\": \"description\",\n" +
          "      \"lastUpdate\": \"\\d+-\\d+-\\d+\",\n" +
          "      \"numberOfAssays\": \\d+,\n" +
          "      \"numberOfContrasts\": \\d+,\n" +
          "      \"species\": \"species\",\n" +
          "      \"kingdom\": \"kingdom\",\n" +
          "      \"experimentalFactors\": [\"type1\",\"type2\"],\n" +
          "      \"arrayDesigns\": [],\n" +
          "      \"arrayDesignNames\": []\n" +
          "    }\n" +
          "  ]\n" +
          "}";


    @Mock
    private ExperimentTrader experimentTrader;

    private ExperimentInfoListService subject;

    @Before
    public void setUp() throws Exception {
        when(experimentTrader.getPublicExperiments(ExperimentType.RNASEQ_MRNA_BASELINE))
                .thenReturn(ImmutableSet.of(BaselineExperimentTest.mockExperiment()));

        subject =
                new ExperimentInfoListService(experimentTrader, ImmutableSet.of(ExperimentType.RNASEQ_MRNA_BASELINE));
    }



    @Test
    public void sizeIsRight() throws Exception {
        JsonArray result = subject.getExperimentsJson().get("aaData").getAsJsonArray();
        assertThat(result.size(), is(1));
    }
    
    @Test
    public void formatIsInSyncWithWhatWeExpectAndTheDataOfMockBaselineExperiment() throws Exception {
        JsonElement result = subject.getExperimentsJson();
        assertThat(result.toString().matches(
                EXPECTED_JSON.replace(" ", "")
                             .replace("\n", "")
                             .replace("[","\\[")
                             .replace("]","\\]")
                             .replace("{","\\{")
                             .replace("}","\\}")),
                is(true));
    }
}