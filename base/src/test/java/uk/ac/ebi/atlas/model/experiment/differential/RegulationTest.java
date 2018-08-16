package uk.ac.ebi.atlas.model.experiment.differential;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RegulationTest {
    @Test
    public void down() {
        assertThat(Regulation.valueOf(-0.0979807106778182), is(Regulation.DOWN));
    }

    @Test
    public void up() {
        assertThat(Regulation.valueOf(0.0452223119926126), is(Regulation.UP));
    }
}
