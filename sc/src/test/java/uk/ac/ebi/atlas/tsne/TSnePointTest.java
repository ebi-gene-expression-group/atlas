package uk.ac.ebi.atlas.tsne;

import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

public class TSnePointTest {
    @Test
    public void create() throws Exception {
        assertThat(TSnePoint.create(0.0, 0.0, "A"), is(TSnePoint.create(0.0, 0.0, 0.0, "A")));
    }

    @Test
    public void compareTo() throws Exception {
        assertThat(TSnePoint.create(0.0, 0.0, 0.0, "B").compareTo(TSnePoint.create(1.0, 1.0, 1.0, "A")), is(greaterThan(0)));
    }
}