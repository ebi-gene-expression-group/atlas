package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CellTest {

    private Cell subject = new Cell("123");

    @Test
    public void returnsValidData() {
        assertThat(subject.assaysAnalyzedForThisDataColumn().size(), is(1));
        assertThat(subject.assaysAnalyzedForThisDataColumn(), is(ImmutableSet.of("123")));
        assertThat(subject.biologicalReplicatesForThisDataColumn().isEmpty(), is(true));
    }
}
