package uk.ac.ebi.atlas.search.baseline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class BaselineExperimentSearchResultProducerIT {


    @Inject
    BaselineExperimentSearchResultProducer subject;

    @Inject
    ExperimentTrader experimentTrader;


    @Test
    public void weHaveTwoColumnsCorrespondingToAdultAndFetus(){
        BaselineExperiment E_PROT_1 = (BaselineExperiment) experimentTrader.getPublicExperiment("E-PROT-1");


        Map<String, Map<String, Double>> expressionsPerColumnPerExperiment = new HashMap<>();

        Map<String, Double> fakeResults = new HashMap<>();
        for(AssayGroup assayGroup: E_PROT_1.getDataColumnDescriptors()) {
            fakeResults.put(assayGroup.getId(), Math.random()* 10000);
        }
        expressionsPerColumnPerExperiment.put(E_PROT_1.getAccession(), fakeResults);

        BaselineExperimentSearchResult result = subject.buildProfilesForExpressions(expressionsPerColumnPerExperiment, "ORGANISM_PART");
        assertThat(result.getFactorsAcrossAllExperiments().size(), lessThan(E_PROT_1.getDataColumnDescriptors().size()));
        assertThat(result.getExperimentProfiles().size(), is(2));

    }
}