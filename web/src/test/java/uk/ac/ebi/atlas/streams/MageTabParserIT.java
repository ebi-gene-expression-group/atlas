package uk.ac.ebi.atlas.streams;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.FactorValue;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MageTabParserIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String RUN_ACCESSION_FIRST = "ERR030872";

    private MageTabInvestigation subject;


    @Before
    public void initSubject() throws IOException, ParseException {
        URL mageTabURL = MageTabParserIT.class.getResource("magetab/E-MTAB-513.idf.txt");

        subject = MageTabInvestigation.parse(mageTabURL);
    }

    @Test
    public void parseExperimentRunsReturnsMoreThanOneRun() {
        Set<ExperimentRun> experimentRuns = subject.extractExperimentRuns();
        assertThat(experimentRuns.size(), is(48));
    }

    @Test
    public void RunsReturnsMoreThanOneRun() {
        //given
        Set<ExperimentRun> experimentRuns = subject.extractExperimentRuns();
        Iterator<ExperimentRun> experimentRunIterator = experimentRuns.iterator();
        ExperimentRun firstExperimentRun = experimentRunIterator.next();
        Iterator<FactorValue> factorValueIterator = firstExperimentRun.getFactorValues().iterator();
        //then
        assertThat(firstExperimentRun.getRunAccession(), startsWith("ERR"));
        assertThat(firstExperimentRun.getFactorValues().size(), is(3));
        assertThat(factorValueIterator.next(), is(equalTo(new FactorValue("ORGANISMPART", "adipose"))));

    }

}
