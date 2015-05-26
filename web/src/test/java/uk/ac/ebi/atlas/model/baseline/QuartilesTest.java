package uk.ac.ebi.atlas.model.baseline;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QuartilesTest {

    @Test
    public void createFromCsvString() {
        assertThat(Arrays.equals(Quartiles.create("0.1, 0.2, 0.3, 0.4, 0.5"), new double[]{0.1,0.2,0.3,0.4,0.5}), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFromInvalidCsvString() {
        Quartiles.create("0.2, 0.3, 0.4, 0.5");
    }

}