package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionTest {

    private Expression subject;

    @Before
    public void initSubject() {

        FactorValue factorValue = new FactorValue("aType", "organ", "heart");

        subject = new Expression(2.3, Sets.newHashSet(factorValue));
    }

    @Test
    public void testIsGreaterThan() throws Exception {

        assertThat(subject.isGreaterThan(0.0), is(true));

        assertThat(subject.isGreaterThan(3.0), is(false));

        assertThat(subject.isGreaterThan(2.3), is(false));

    }
}
