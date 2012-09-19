package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperimentRunTest {

    private ExperimentRun subject;

    private FactorValue factorValue1 = new FactorValue("factor1","value1");
    private FactorValue organismPart = new FactorValue("organismpart","finger");
    private FactorValue factorValue2 = new FactorValue("factor2","value2");

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentRun("RUN_ACCESSION", Sets.newHashSet(factorValue1, factorValue2, organismPart));
    }

    @Test
    public void testGetOrganismPart() throws Exception {

        assertThat(subject.getOrganismPart(), is(organismPart.getValue()));

    }

}
