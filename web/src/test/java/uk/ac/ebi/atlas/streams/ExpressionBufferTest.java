package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import utils.ExperimentRunsBuilder;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpressionBufferTest {

    private static final String RUN_ACCESSION_1 = "ERR030872";
    private static final String RUN_ACCESSION_2 = "ERR030873";
    private static final String RUN_ACCESSION_3 = "ERR030874";
    private static final String GENE_ID = "ENST00000000233";
    private static final String[] THREE_EXPRESSION_LEVELS = new String[]{GENE_ID, "0", "42.9134", "0.0001"};

    private ExpressionsBuffer subject;
    private static List<ExperimentRun> experimentRuns;
    private ExperimentsCache experimentsCache = mock(ExperimentsCache.class);


    @Before
    public void initializeSubject() {
        ExperimentRun experimentRun1 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_1).create();
        ExperimentRun experimentRun2 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_2).create();
        ExperimentRun experimentRun3 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_3).create();

        experimentRuns = Lists.newArrayList(experimentRun1, experimentRun2, experimentRun3);

        when(experimentsCache.getExperimentRuns(anyString())).thenReturn(experimentRuns);

        subject = new ExpressionsBuffer.Builder(experimentsCache)
                .forExperiment("FAKE_EXPERIMENT_ACCESSION")
                .withHeaders("", RUN_ACCESSION_1, RUN_ACCESSION_2, RUN_ACCESSION_3)
                .create();
    }

    @Test(expected = IllegalStateException.class)
    public void builderShouldThrowIllegalStateExceptionWhenOrderSpecificationIsNotSet() {
        new ExpressionsBuffer.Builder(experimentsCache).create();
    }

    @Test
    public void pollShouldReturnExpressionsInTheRightOrder() throws Exception {
        subject.reload(THREE_EXPRESSION_LEVELS);
        //given the object was just initialized
        Expression expression = subject.poll();
        //then we expect first expression
        assertThat(expression.getLevel(), is(0d));
        assertThat(expression.getFactorValues(), is(experimentRuns.get(0).getFactorValues()));

        //given we poll again
        expression = subject.poll();
        //then we expect second Expression
        assertThat(expression.getLevel(), is(42.9134d));
        assertThat(expression.getFactorValues(), is(experimentRuns.get(1).getFactorValues()));

    }

    @Test
    public void bufferShouldBeExhaustedAfterThreePolls() throws Exception {
        //given we do first reload
        subject.reload(THREE_EXPRESSION_LEVELS);
        //and we poll three times
        subject.poll();
        subject.poll();
        subject.poll();
        //then we expect next poll to return null
        assertThat(subject.poll(), Matchers.is(nullValue()));
    }

    @Test
    public void reloadWhenBufferIsExhaustedShouldFillTheBufferAgain() throws Exception {
        //given we do first reload
        subject.reload(THREE_EXPRESSION_LEVELS);
        //and we poll until exhaustion
        Expression run;
        do {
            run = subject.poll();
        } while (run != null);
        //when we reload again with new values
        subject.reload("T1", "1", "2", "3");
        //and we poll
        Expression expression = subject.poll();
        //then we expect to find the new values
        assertThat(expression.getLevel(), is(1d));
        assertThat(expression.getRunAccession(), is(experimentRuns.get(0).getRunAccession()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfMoreValuesThanRuns() throws Exception {
        //given that we initialized subject with three runs
        //when
        subject.reload(GENE_ID, "0", "42.9134", "0.0001", "666");

    }

    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfLessValuesThanRuns() throws Exception {
        //given that we initialized subject with three runs
        //when
        subject.reload(GENE_ID, "0", "42.9134");

    }

    @Test(expected = IllegalStateException.class)
    public void reloadShouldThrowExceptionIfBufferIsNotEmpty() throws Exception {
        //given
        subject.reload(THREE_EXPRESSION_LEVELS);
        //and
        subject.poll();
        //when we reload again
        subject.reload(THREE_EXPRESSION_LEVELS);

    }


}
