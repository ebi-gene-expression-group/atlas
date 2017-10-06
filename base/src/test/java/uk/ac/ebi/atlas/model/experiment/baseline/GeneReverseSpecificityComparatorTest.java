package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneReverseSpecificityComparatorTest {

    private BaselineProfileComparator subject;

    private BaselineProfile profileWithOneExpression;

    private BaselineProfile profileWithTwoExpressions;

    AssayGroup g1 = new AssayGroup("g1", "run_11", "run_12", "run_13");
    AssayGroup g2 = new AssayGroup("g2", "run_21", "run_22", "run_23", "run_24");

    List<AssayGroup> assayGroups = ImmutableList.of(g1, g2);

    @Before
    public void initGeneExpressions() {
        profileWithOneExpression = new BaselineProfile("profileWithOneExpression","profileWithOneExpression");
        profileWithOneExpression.add(g1, BaselineExpression.create("1"));
        profileWithTwoExpressions = new BaselineProfile("profileWithTwoExpressions","profileWithTwoExpressions");
        profileWithTwoExpressions.add(g1, BaselineExpression.create("1"));
        profileWithTwoExpressions.add(g2, BaselineExpression.create("1"));
    }

    @Before
    public void initSubject() {
        subject = new BaselineProfileComparator(false, assayGroups, assayGroups);
    }

    @Test
    public void lowSpecificityShouldFollowHighSpecificity() {
        //when
        int comparison = subject.compare(profileWithTwoExpressions, profileWithOneExpression);
        //then
        assertThat(comparison, is(lessThan(0)));

        //when
        comparison = subject.compare(profileWithOneExpression, profileWithTwoExpressions);
        //then
        assertThat(comparison, is(greaterThan(0)));
    }

}
