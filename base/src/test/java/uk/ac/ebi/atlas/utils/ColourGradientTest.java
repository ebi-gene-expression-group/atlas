package uk.ac.ebi.atlas.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ColourGradientTest {

    @Test
    public void maxValueIsHighValueColour() {
        double max = 1.0;
        assertThat(ColourGradient.getGradientColour(max, 0.0, max, "#000000", "#FF0000"), is("#FF0000"));
    }

    @Test
    public void minValueIsLowValueColour() {
        double min = 0.0;
        assertThat(ColourGradient.getGradientColour(min, min, 1.0, "#000000", "#FF0000"), is("#000000"));
    }


    @Test
    public void itCanUnderstandColourNames() {
        double min = 0.0;
        double max = 1.0;
        assertThat(ColourGradient.getGradientColour(max, min, max, "white", "red"), is("#FF0000"));
        assertThat(ColourGradient.getGradientColour(min, min, max, "white", "red"), is("#FFFFFF"));
    }

    @Test
    public void returnsMinimumIfColourSpaceIsEmpty() {
        assertThat(ColourGradient.getGradientColour(1.0, 1.0, 2.0, "red", "blue"), is("#FF0000"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIfValueIsAboveMax() {
        ColourGradient.getGradientColour(2.0, 0.0, 1.0, "white", "red");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIfValueIsBelowMin() {
        ColourGradient.getGradientColour(-1.0, 0.0, 1.0, "white", "red");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsOnUnknownColourNames() {
        ColourGradient.getGradientColour(0.0, 0.0, 1.0, "white", "foobar");
    }

    @Test
    public void updateColourValue() {
        assertThat(ColourGradient.updateColourValue(3, 255), is(2));
        assertThat(ColourGradient.updateColourValue(3, -255), is(4));
        assertThat(ColourGradient.updateColourValue(3, 0), is(3));
    }

}
