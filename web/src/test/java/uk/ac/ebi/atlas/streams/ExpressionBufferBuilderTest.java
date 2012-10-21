package uk.ac.ebi.atlas.streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import utils.ExperimentRunsBuilder;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionBufferBuilderTest {

    private static final String RUN_ACCESSION_1 = "ERR030872";
    private static final String RUN_ACCESSION_2 = "ERR030873";
    private static final String RUN_ACCESSION_3 = "ERR030874";

    private ExperimentRun experimentRun1;
    private ExperimentRun experimentRun2;
    private ExperimentRun experimentRun3;

    private static final String MOCK_EXPERIMENT_ACCESSION = "MOCK_EXPERIMENT_ACCESSION";

    @Mock
    private ExperimentsCache experimentsCacheMock;

    private ExpressionsBuffer.Builder subject;

    @Before
    public void initializeSubject() {
        experimentRun1 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_1).create();
        experimentRun2 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_2).create();
        experimentRun3 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_3).create();

        List<ExperimentRun> experimentRuns = Lists.newArrayList(experimentRun1, experimentRun2, experimentRun3);

        when(experimentsCacheMock.getExperimentRuns(MOCK_EXPERIMENT_ACCESSION)).thenReturn(experimentRuns);

        subject = new ExpressionsBuffer.Builder(experimentsCacheMock);
    }

    @Test
    public void builderShouldFetchExperimentRunsFromACache(){

        //when
        subject.forExperiment(MOCK_EXPERIMENT_ACCESSION);
        //then
        verify(experimentsCacheMock).getExperimentRuns(MOCK_EXPERIMENT_ACCESSION);

    }

    @Test(expected = IllegalStateException.class)
    public void createThrowsExceptionGivenThatExperimentAccessionHasNotBeenProvided() {
        //when
        subject.create();
    }

    @Test(expected = IllegalStateException.class)
    public void createThrowsExceptionGivenThatOrderedHeadersHaveNotBeenProvided() {
        //when
        subject.create();
    }

    @Test(expected = IllegalStateException.class)
    public void withHeadersThrowsExceptionWhenExperimentAccessionIsNotSet() {
        //when
        subject.create();
    }

    @Test
    public void createShouldSucceedWhenSpecificationHasBeenSet() {
        //given
        String[] headers = new String[]{"", RUN_ACCESSION_2, RUN_ACCESSION_3};
        //when
        subject.forExperiment(MOCK_EXPERIMENT_ACCESSION);
        //then
        assertThat(subject.withHeaders(headers).create(), is(notNullValue()));
    }

    @Test
    public void removeUnrequiredExperimentRunsTest() {
        //given
        List<String> wantedRunAccessions = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //and
        subject.forExperiment(MOCK_EXPERIMENT_ACCESSION);

        //when
        Collection<ExperimentRun> experimentRuns = subject.removeUnrequiredExperimentRuns(wantedRunAccessions);

        //then
        assertThat(experimentRuns, hasItems(experimentRun2, experimentRun3));
        assertThat(experimentRuns, not(hasItem(experimentRun1)));

    }

    @Test
    public void experimentRunIsRequiredWhenItsAccessionIsIncludedInTheProvidedOrderedRunAccessions() {
        //given
        List<String> orderSpecification = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //when
        boolean isRequired = subject.isExperimentRunRequired(orderSpecification).apply(experimentRun2);
        //then
        assertThat(isRequired, is(true));
    }

    @Test
    public void experimentRunIsNotRequiredWhenItsAccessionIsNotIncludedInTheProvidedOrderedRunAccessions() {
        //given
        List<String> orderSpecification = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //when
        boolean isRequired = subject.isExperimentRunRequired(orderSpecification).apply(experimentRun1);
        //then
        assertThat(isRequired, is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void experimentRunComparatorThrowsExceptionWhenExperimentRunAccessionIsNotInTheOrderedListOfKnownAccessions() {
        //given
        List<String> orderedRunAccessions = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //and
        Comparator<ExperimentRun> experimentRunsComparator = subject.experimentRunComparator(orderedRunAccessions);
        //when
        experimentRunsComparator.compare(experimentRun2, experimentRun1);
        //then expect IllegalStateExceptionToBeThrown;
    }

    @Test
    public void experimentRunComparatorShouldOrderByPositionInTheListOfOrderedRunAccessions() {
        //given
        List<String> orderSpecification = Lists.newArrayList(RUN_ACCESSION_3, RUN_ACCESSION_2);
        //and
        Comparator<ExperimentRun> experimentRunsComparator = subject.experimentRunComparator(orderSpecification);
        //then
        assertThat(experimentRunsComparator.compare(experimentRun2, experimentRun3), is(greaterThan(0)));
        //and
        assertThat(experimentRunsComparator.compare(experimentRun3, experimentRun2), is(lessThan(0)));

    }

}
