package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperimentRunTest {

    private ExperimentRun subject;

    private String organismPart = "finger";

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentRun("RUN_ACCESSION")
                                .addFactorValue("factor1","value1")
                                .addFactorValue("factor2","value2")
                                .addOrganismPartFactorValue(organismPart);
    }

    @Test
    public void testGetOrganismPart() throws Exception {

        assertThat(subject.getOrganismPart(), is(organismPart));

    }

}
