package uk.ac.ebi.atlas.model.caches;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.FactorValue;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MageTabParserIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String RUN_ACCESSION_FIRST = "ERR030872";

    private static final String MAGE_TAB_URL_TEMPLATE = "http://www.ebi.ac.uk/arrayexpress/files/%s/%s.idf.txt";


    private MageTabInvestigationLoader subject;

    @Before
    public void initSubject() throws IOException, ParseException {

        subject = new MageTabInvestigationLoader();

        subject.setIdfFileUrlTemplate(MAGE_TAB_URL_TEMPLATE);
    }

    @Test
    public void parseExperimentRunsReturnsMoreThanOneRun() throws Exception{
        List<ExperimentRun> experimentRuns = subject.load(EXPERIMENT_ACCESSION);
        assertThat(experimentRuns.size(), is(48));
    }

    @Test
    public void RunsReturnsMoreThanOneRun() throws Exception{
        //given
        List<ExperimentRun> experimentRuns = subject.load(EXPERIMENT_ACCESSION);
        Iterator<ExperimentRun> experimentRunIterator = experimentRuns.iterator();
        ExperimentRun firstExperimentRun = experimentRunIterator.next();
        Iterator<FactorValue> factorValueIterator = firstExperimentRun.getFactorValues().iterator();
        //then
        assertThat(firstExperimentRun.getRunAccession(), startsWith("ERR"));
        assertThat(firstExperimentRun.getFactorValues().size(), is(3));
        assertThat(factorValueIterator.next(), is(equalTo(new FactorValue("ORGANISMPART", "adipose"))));

    }

}
