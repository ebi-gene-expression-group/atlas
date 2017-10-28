package uk.ac.ebi.atlas.experimentpage.json;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonBaselineExperimentControllerTest {

    @Test
    public void columnHeadersShowUp() throws Exception {

        BaselineExperiment experiment = mock(BaselineExperiment.class);

        when(experiment.getDisplayDefaults()).thenReturn(ExperimentDisplayDefaults.simpleDefaults());

        when(experiment.getDataColumnDescriptors()).thenReturn(ImmutableList.of(new AssayGroup("assay_group_id", "run_1")));



        assertThat(JsonBaselineExperimentController.toJson(
                new GeneProfilesList<>(), new BaselineRequestContext<>(new RnaSeqBaselineRequestPreferences(), experiment)
        ), is(new Gson().fromJson("{\n" +
                "    \"profiles\": {\n" +
                "        \"searchResultTotal\": \"0\",\n" +
                "        \"rows\": []\n" +
                "    },\n" +
                "    \"columnHeaders\": [{\n" +
                "        \"id\": \"assay_group_id\",\n" +
                "        \"assayAccessions\": [\"run_1\"],\n" +
                "        \"replicates\": 1\n" +
                "    }]\n" +
                "}", JsonObject.class)));
    }
}