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
        assertThat(subject.calculateColourDistance(Color.WHITE, Color.RED), is(510));
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
        this.subject = new ColourGradient(Color.WHITE, Color.RED, Color.WHITE, ColourGradient.SCALE_LOGARITHMIC);

        assertThat(subject.getColourPosition(0.5, Color.WHITE, Color.RED), is(414));
        assertThat(subject.getColourPosition(0.0, Color.WHITE, Color.RED), is(0));
        assertThat(subject.getColourPosition(1.0, Color.WHITE, Color.RED), is(510));
    }

    @Test
    public void getColourPositionLinearScale() throws Exception {
        //given
        this.subject = new ColourGradient(Color.WHITE, Color.RED, Color.WHITE, ColourGradient.SCALE_LINEAR);

        assertThat(subject.getColourPosition(0.5, Color.WHITE, Color.RED), is(255));
        assertThat(subject.getColourPosition(0.0, Color.WHITE, Color.RED), is(0));
        assertThat(subject.getColourPosition(1.0, Color.WHITE, Color.RED), is(510));
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
    public void getColourShouldReturnLowLevelColourWhenValueEqualsToMinLevel(){
        String hexColor = subject.getGradientColour(2, 2, 300);
        assertThat(hexColor, is(subject.colorToHexString(Color.WHITE)));
    }

    @Test
    public void getColourShouldReturnHighLevelColourWhenValueEqualsToMaxLevel(){
        String hexColor = subject.getGradientColour(300, 2, 300);
        assertThat(hexColor, is(subject.colorToHexString(Color.RED)));
    }

    @Test
    public void getColourByNameTest(){
        assertThat(subject.getColourByName("blue"), is(Color.BLUE));
        assertThat(subject.getColourByName("pink"), is(Color.PINK));
        assertThat(subject.getColourByName("lightGray"), is(Color.LIGHT_GRAY));
    }

    @Test
    public void getHexByColourByNameTest(){
        assertThat(subject.getHexByColourName("blue"), is("#0000FF"));
        assertThat(subject.getHexByColourName("pink"), is("#FFAFAF"));
    }

}
