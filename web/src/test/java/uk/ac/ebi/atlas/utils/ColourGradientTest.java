package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ColourGradientTest {

    ColourGradient subject;

    @Before
    public void initSubject() {
        this.subject = new ColourGradient(Color.WHITE, Color.RED, Color.WHITE, 0.3d);

    }

    @Test
    public void calculateColourDistance() throws Exception {
        subject.setLowValueColour(new Color(255, 0, 0));
        subject.setHighValueColour(new Color(255, 255, 255));
        assertThat(subject.getColourDistance(), is(510));

        subject.setLowValueColour(new Color(0, 0, 0));
        subject.setHighValueColour(new Color(255, 255, 255));
        assertThat(subject.getColourDistance(), is(765));

        subject.setLowValueColour(new Color(255, 255, 255));
        subject.setHighValueColour(new Color(255, 255, 255));
        assertThat(subject.getColourDistance(), is(0));

        subject.setLowValueColour(new Color(255, 255, 255));
        subject.setHighValueColour(new Color(0, 0, 0));
        assertThat(subject.calculateColourDistance(), is(765));
    }

    @Test
    public void calculatePercentPosition() throws Exception {
        assertThat(subject.calculatePercentPosition(1, 0, 2), is(0.5));
        assertThat(subject.calculatePercentPosition(0, 0, 2), is(0.0));
        assertThat(subject.calculatePercentPosition(2, 0, 2), is(1.0));
    }

    @Test
    public void getColourPositionLogScale() throws Exception {

        //given
        subject.setColourScale(ColourGradient.SCALE_LOGARITHMIC);



        assertThat(subject.getColourPosition(0.5), is(414));
        assertThat(subject.getColourPosition(0.0), is(0));
        assertThat(subject.getColourPosition(1.0), is(510));
    }

    @Test
    public void getColourPositionLinearScale() throws Exception {
        //given
        subject.setColourScale(ColourGradient.SCALE_LINEAR);

        assertThat(subject.getColourPosition(0.5), is(255));
        assertThat(subject.getColourPosition(0.0), is(0));
        assertThat(subject.getColourPosition(1.0), is(510));
    }

    @Test
    public void calculateColorForPosition() throws Exception {

        Color color = subject.calculateColorForPosition(1, new Color(255, 255, 255), new Color(255, 0, 0));
        assertThat(color.getRed(), is(255));
        assertThat(color.getGreen(), is(254));
        assertThat(color.getBlue(), is(255));

        color = subject.calculateColorForPosition(2, new Color(255, 255, 255), new Color(255, 0, 0));
        assertThat(color.getRed(), is(255));
        assertThat(color.getGreen(), is(254));
        assertThat(color.getBlue(), is(254));

        color = subject.calculateColorForPosition(509, new Color(255, 255, 255), new Color(255, 0, 0));
        assertThat(color.getRed(), is(255));
        assertThat(color.getGreen(), is(0));
        assertThat(color.getBlue(), is(1));
    }

    @Test
    public void updateColourValue() throws Exception {

        assertThat(subject.updateColourValue(3, 255), is(2));
        assertThat(subject.updateColourValue(3, -255), is(4));
        assertThat(subject.updateColourValue(3, 0), is(3));
    }

    @Test
    public void colorToHexString() throws Exception {
        assertThat(subject.colorToHexString(Color.RED), is("#FF0000"));
        assertThat(subject.colorToHexString(Color.BLUE), is("#0000FF"));
    }

    @Test
    public void getCellColourStringWithEmptyData() throws Exception {
        subject.setLowValueColour(Color.white);
        subject.setHighValueColour(Color.red);
        subject.getGradientColour(null, "1", "5");
        assertThat(subject.getGradientColour(null, "1", "5"), is("#FFFFFF"));
    }

    public void getColourShouldReturnHighLevelColourWhenValueEqualsToMaxLevel(){
        Color color = subject.getGradientColour(2, 2, 300);
        assertThat(subject.colorToHexString(color), is(subject.getMaxColour()));
    }


}
