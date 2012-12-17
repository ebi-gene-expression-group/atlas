//ToDo ... this is impossible to test without bootstrapping spring, as most other IT tests


package uk.ac.ebi.atlas.model.caches;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.FactorValue;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MageTabParserIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String RUN_ACCESSION = "ERR030880";

    @Inject
    private ExperimentMetadataLoader subject;

    @Before
    public void initSubject() throws IOException, ParseException {
    }

    @Test
    public void parseExperimentRunsReturnsMoreThanOneRun() throws Exception {
        Experiment experiment = subject.load(EXPERIMENT_ACCESSION);
        assertThat(experiment.getNumberOfRuns(), is(16));
    }

    @Test
    public void firstRunIsCorrect() throws Exception {
        //given
        Experiment experiment = subject.load(EXPERIMENT_ACCESSION);
        ExperimentRun experimentRun = experiment.getExperimentRun(RUN_ACCESSION);
        //then
        assertThat(experimentRun.getRunAccession(), is(RUN_ACCESSION));
        assertThat(experimentRun.getFactorValues().size(), is(3));
        assertThat(experimentRun.getExperimentalFactor(FactorValue.FactorType.ORGANISM_PART).getValue(), is(equalTo("adipose")));
    }

}
