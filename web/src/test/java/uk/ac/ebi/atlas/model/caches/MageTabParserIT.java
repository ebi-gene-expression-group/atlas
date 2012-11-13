package uk.ac.ebi.atlas.model.caches;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentRun;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MageTabParserIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String RUN_ACCESSION = "ERR030880";

    private static final String MAGE_TAB_URL_TEMPLATE = "http://www.ebi.ac.uk/arrayexpress/files/%s/%s.idf.txt";


    private MageTabInvestigationLoader subject;

    @Before
    public void initSubject() throws IOException, ParseException {

        subject = new MageTabInvestigationLoader();

        subject.setIdfFileUrlTemplate(MAGE_TAB_URL_TEMPLATE);
    }

    @Test
    public void parseExperimentRunsReturnsMoreThanOneRun() throws Exception {
        Experiment experiment = subject.load(EXPERIMENT_ACCESSION);
        assertThat(experiment.getNumberOfRuns(), is(48));
    }

    @Test
    public void firstRunIsCorrect() throws Exception {
        //given
        Experiment experiment = subject.load(EXPERIMENT_ACCESSION);
        ExperimentRun experimentRun = experiment.getExperimentRun(RUN_ACCESSION);
        //then
        assertThat(experimentRun.getRunAccession(), is(RUN_ACCESSION));
        assertThat(experimentRun.getFactorValues().size(), is(3));
        assertThat(experimentRun.getOrganismPart().getValue(), is(equalTo("adipose")));
    }

}
