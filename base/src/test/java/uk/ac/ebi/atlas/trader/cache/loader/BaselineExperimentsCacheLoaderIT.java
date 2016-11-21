package uk.ac.ebi.atlas.trader.cache.loader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class BaselineExperimentsCacheLoaderIT {

    @Inject
    private RnaSeqBaselineExperimentsCacheLoader subject;


    @Test
    public void correctSpeciesReadFromDatabase() throws Exception {
        //given
        BaselineExperiment experiment = subject.load("E-MTAB-513");
        //then
        String species = experiment.getSpecies().originalName;
        assertThat(species, is("Homo sapiens"));
    }


    @Test
    public void experimentShouldOnlyContainRunsFromDataFile() throws IOException {
        BaselineExperiment experiment = subject.load("E-MTAB-513");

        assertThat(experiment.getExperimentRunAccessions(), hasItems(
            "ERR030872", "ERR030873", "ERR030874", "ERR030875",
            "ERR030876", "ERR030877", "ERR030878", "ERR030879",
            "ERR030880", "ERR030881", "ERR030882", "ERR030883",
            "ERR030884", "ERR030885", "ERR030886", "ERR030887"
        ));

    }

    @Test
    public void experimentShouldContainAssayGroups() throws IOException {
        BaselineExperiment experiment = subject.load("E-MTAB-513");

        assertThat(experiment.getAssayGroups().getAssayGroupIds(), hasSize(16));
    }

    @Test(expected = IllegalStateException.class)
    public void loadNonExistentExperimentThrowsIllegalStateException() throws IOException {
        subject.load("FOOBAR");
    }

}
