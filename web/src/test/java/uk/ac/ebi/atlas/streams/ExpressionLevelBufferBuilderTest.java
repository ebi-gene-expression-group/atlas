package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import utils.ExperimentRunsBuilder;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExpressionLevelBufferBuilderTest {

    private static final String RUN_ACCESSION_1 = "ERR030872";
    private static final String RUN_ACCESSION_2 = "ERR030873";
    private static final String RUN_ACCESSION_3 = "ERR030874";

    private ExperimentRun experimentRun1;
    private ExperimentRun experimentRun2;
    private ExperimentRun experimentRun3;

    private ExpressionLevelsBuffer.Builder subject;

    @Before
    public void initializeSubject() {
        experimentRun1 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_1).create();
        experimentRun2 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_2).create();
        experimentRun3 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_3).create();

        List<ExperimentRun> experimentRuns = Lists.newArrayList(experimentRun1, experimentRun2, experimentRun3);

        subject = ExpressionLevelsBuffer.forExperimentRuns(experimentRuns);
    }

    @Test(expected = IllegalStateException.class)
    public void builderShouldThrowIllegalStateExceptionWhenOrderSpecificationIsNotSet() {
        //when
        subject.create();

        //then an exception should be thrown because run accession headers haven't been assigned
    }

    @Test
    public void createShouldSucceedWhenSpecificationHasBeenSet() {
        //given
        String[] headers = new String[]{"", RUN_ACCESSION_2, RUN_ACCESSION_3};
        //then
        assertThat(subject.withHeaders(headers).create(), is(notNullValue()));
    }

    @Test
    public void removeUnwantedExperimentRunsTest() {
        //given
        List<String> wantedRunAccessions = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //when
        Collection<ExperimentRun> experimentRuns = subject.removeUnrequiredExperimentRuns(wantedRunAccessions);
        //then
        assertThat(experimentRuns, hasItems(experimentRun2, experimentRun3));
        assertThat(experimentRuns, not(hasItem(experimentRun1)));

    }

    @Test
    public void experimentRunIsRequiredWhenItIsIncludedInOrderSpecification() {
        //given
        List<String> orderSpecification = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //when
        boolean isRequired = subject.isExperimentRunRequired(orderSpecification).apply(experimentRun2);
        //then
        assertThat(isRequired, is(true));
    }

    @Test
    public void experimentRunIsNotRequiredWhenItIsNotIncludedInOrderSpecification() {
        //given
        List<String> orderSpecification = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //when
        boolean isRequired = subject.isExperimentRunRequired(orderSpecification).apply(experimentRun1);
        //then
        assertThat(isRequired, is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void experimentRunComparatorShouldThrowExceptionWhenExperimentRunIsNotIncludedInsOrderBySpecification() {
        //given
        List<String> orderSpecification = Lists.newArrayList(RUN_ACCESSION_2, RUN_ACCESSION_3);
        //and
        Comparator<ExperimentRun> experimentRunsComparator = subject.experimentRunComparator(orderSpecification);
        //when
        experimentRunsComparator.compare(experimentRun2, experimentRun1);
        //then expect IllegalStateExceptionToBeThrown;
    }

    @Test
    public void experimentRunComparatorShouldOrderByPositionInOrderSpecification() {
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
