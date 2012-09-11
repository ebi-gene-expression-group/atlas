package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionLevelTest {

    private ExpressionLevel subject;

    @Mock
    private ExperimentRun experimentRun;

    @Before
    public void initSubject() {

        subject = new ExpressionLevel(experimentRun, 2.3);
    }

    @Test
    public void testIsGreaterThan() throws Exception {

        assertThat(subject.isGreaterThan(0.0), is(true));

        assertThat(subject.isGreaterThan(3.0), is(false));

        assertThat(subject.isGreaterThan(2.3), is(false));

    }
}
