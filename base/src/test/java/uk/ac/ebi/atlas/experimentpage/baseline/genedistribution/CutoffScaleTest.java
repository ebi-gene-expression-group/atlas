package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CutoffScaleTest {
    // We want the first value to be low enough to catch everything to the left of it - so that we can have as many
    // bins as cutoffs. The UI filters genes with cutoff zero anyway as they're not interesting to the user.
    @Test
    public void scaledFirstValueisZero() {
        assertThat(new CutoffScale.Scaled().get()[0], is(0.0));
    }

    @Test
    public void logarithmicFirstValueisZero() {
        assertThat(new CutoffScale.Logarithmic().get()[0], is(0.0));
    }
}
