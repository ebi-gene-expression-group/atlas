
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CutoffScaleTest {

    private CutoffScale subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new CutoffScale();
    }

    @Test
    public void getScaledValuesTest(){

        List<Double> expectedValues = Lists.newArrayList(0d, 0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d
                , 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d
                , 10d, 20d, 30d, 40d, 50d, 60d, 70d, 80d, 90d
                , 100d);

        SortedSet expectedSet = Sets.newTreeSet(expectedValues);

        assertThat(subject.getValuesSmallerThan(132.33), is(expectedSet));

        assertThat(subject.getValuesSmallerThan(0), is(empty()));

        assertThat(subject.getValuesSmallerThan(0.001), contains(0d));


    }

    @Test
    public void cutoffStringValuesShouldBeMagnified(){

        assertThat(subject.getNthValue(0), is(0d));
        assertThat(subject.getNthValue(1), is(0.1d));
        assertThat(subject.getNthValue(2), is(0.2d));
        assertThat(subject.getNthValue(3), is(0.3d));
        assertThat(subject.getNthValue(4), is(0.4d));
        assertThat(subject.getNthValue(5), is(0.5d));
        assertThat(subject.getNthValue(6), is(0.6d));

        assertThat(subject.getNthValue(10), is(1d));
        assertThat(subject.getNthValue(11), is(2d));
        assertThat(subject.getNthValue(14), is(5d));

        assertThat(subject.getNthValue(30), is(300d));
        assertThat(subject.getNthValue(33), is(600d));
        assertThat(subject.getNthValue(34), is(700d));

        assertThat(subject.getNthValue(50), is(50000d));
        assertThat(subject.getNthValue(53), is(80000d));
        assertThat(subject.getNthValue(54), is(90000d));

    }

}
