package uk.ac.ebi.atlas.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.differential.GseaPlotsBuilder;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import java.util.Map;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class ContrastImageFactoryIT {

    @Inject
    ContrastImageFactory subject;

    @Inject
    GseaPlotsBuilder oldImpl;

    @Inject
    ExperimentTrader experimentTrader;


    @Test
    public void ABTest(){
        for(String accession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()){
            DifferentialExperiment differentialExperiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);

            JsonObject oldImplResult = oldImpl.createJsonByContrastId(differentialExperiment.getAccession(), differentialExperiment.getContrasts());

            JsonObject result = subject.resourcesPerContrast(differentialExperiment);

            for(Map.Entry<String, JsonElement > entry:    oldImplResult.entrySet()){
                String contrastId = entry.getKey();
                JsonObject oldImplResultForThisContrastId = entry.getValue().getAsJsonObject();

                JsonArray resultForThisContrastId = result.getAsJsonArray(contrastId);
                for(JsonElement e: resultForThisContrastId){
                    String name = e.getAsJsonObject().get("type").getAsString();
                    assertThat(name, Matchers.not(Matchers.isEmptyOrNullString()));
                    String uri = e.getAsJsonObject().get("uri").getAsString();
                    assertThat(uri, Matchers.not(Matchers.isEmptyOrNullString()));

                    if(name.matches("PLOT_GSEA_.*")){
                        assertThat(
                            oldImplResult
                            .get(contrastId).getAsJsonObject()
                            .get(name.replace("PLOT_GSEA_", "").toLowerCase())
                            .getAsBoolean(), is(true));
                    } else {
                        assertThat(name, is("PLOT_MA"));
                    }
                }

                int countOfOldResults = 0;
                for(Map.Entry<String, JsonElement > entryOld : oldImplResultForThisContrastId.entrySet()){
                    if(entryOld.getValue().getAsBoolean()){
                        countOfOldResults++;
                    }
                }
                countOfOldResults++ ; // Because of the MA plot which supposedly always exists :)
                if(countOfOldResults!= resultForThisContrastId.size() && countOfOldResults>1 ){
                    fail();
                }

            }
        }

    }


}