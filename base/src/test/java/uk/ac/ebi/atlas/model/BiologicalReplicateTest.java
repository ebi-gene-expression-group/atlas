package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BiologicalReplicateTest {

    @Test(expected=RuntimeException.class)
    public void replicateIdCantBeAnAssayId() throws Exception {
        new BiologicalReplicate("Foobar", ImmutableSet.of("Foo", "Foobar", "Bar"));
    }

    @Test(expected=RuntimeException.class)
    public void replicateNeedsAtLeastOneAssayId() throws Exception {
        new BiologicalReplicate("Foobar", Collections.emptySet());
    }

    @Test
    public void retrievesAssayIdsAndItself() throws Exception {
        ImmutableSet<String> assayIds = ImmutableSet.of("assayId1", "assayId2", "assayId3");
        BiologicalReplicate subject = new BiologicalReplicate("replicateId", assayIds);

        assertThat(subject.biologicalReplicatesForThisDataColumn().iterator().next(), is(equalTo(subject)));
        assertThat(subject.assaysAnalyzedForThisDataColumn(), is(equalTo(assayIds)));
    }

    @Test
    public void toStringIsNice(){
        assertThat(
                new BiologicalReplicate("assay").toString(),
                is("BiologicalReplicate {id=assay}")
        );
    }
}