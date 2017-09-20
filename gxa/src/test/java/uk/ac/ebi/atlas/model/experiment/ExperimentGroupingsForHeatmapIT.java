package uk.ac.ebi.atlas.model.experiment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class ExperimentGroupingsForHeatmapIT {

    @Inject
    ExperimentTrader experimentTrader;

    @Test
    public void testSomeGoodExperiments() throws Exception {

        outputInFineFormatForExperiment("E-MTAB-513");
        outputInFineFormatForExperiment("E-MTAB-2706");
        outputInFineFormatForExperiment("E-GEOD-54705");
        outputInFineFormatForExperiment("E-MTAB-4260");

    }

    /*
    For differential experiments, a few groupings repeat and we might need something else for them.
     */

    public void outputInFineFormatForExperiment(String accession){
        Experiment<DescribesDataColumns> experiment = experimentTrader.getPublicExperiment(accession);

        List<String> allDescriptorIds = experiment.getDataColumnDescriptors().stream()
                .map(DescribesDataColumns::getId)
                .collect(Collectors.toList());

        JsonArray result = experiment.groupingsForHeatmap();

        assertThat(result.size(), greaterThan(0));

        for(JsonElement element: result){
            assertTrue(element.getAsJsonObject().has("name"));
            assertTrue(element.getAsJsonObject().has("selected"));
            assertThat(element.getAsJsonObject().get("groupings").getAsJsonArray().size(), greaterThan(0));
            for(JsonElement grouping: element.getAsJsonObject().get("groupings").getAsJsonArray()){
                assertThat(grouping.getAsJsonArray().size(), is(2));
                assertThat(grouping.getAsJsonArray().get(1).getAsJsonArray().size(), greaterThan(0));
                for(JsonElement groupingValue : grouping.getAsJsonArray().get(1).getAsJsonArray()){
                    assertTrue(allDescriptorIds.contains(groupingValue.getAsString()));
                }
            }
        }
    }
}