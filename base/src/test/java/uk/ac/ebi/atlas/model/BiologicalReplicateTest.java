package uk.ac.ebi.atlas.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class BiologicalReplicateTest {


    @Test
    public void toStringIsNice(){
        assertThat(
                new BiologicalReplicate("assay").toString(),
                is("BiologicalReplicate {id=assay}")
        );
    }

}