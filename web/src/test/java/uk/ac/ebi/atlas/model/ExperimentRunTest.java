package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperimentRunTest {

    private ExperimentRun subject;

    private static final String organismPart = "finger";

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentRun("RUN_ACCESSION")
                                .addFactorValue("A_TYPE", "factor1","value1")
                                .addFactorValue("ORGANISM_PART", "org", organismPart);
    }

    @Test
    public void testGetOrganismPart() throws Exception {

        assertThat(subject.getOrganismPart().getValue(), is(organismPart));

    }

}
