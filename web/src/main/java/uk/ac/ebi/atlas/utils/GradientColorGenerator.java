package uk.ac.ebi.atlas.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

import static java.lang.Double.parseDouble;

public class GradientColorGenerator {

    /**
     * A basic logarithmic scale value of 0.3.
     */
    public static final double SCALE_LOGARITHMIC = 0.3;

    /**
     * The linear scale value of 1.0.
     */
    public static final double SCALE_LINEAR = 1.0;

    /**
     * A basic exponential scale value of 3.0.
     */
    public static final double SCALE_EXPONENTIAL = 3;

    // Heat map colour settings.
    private Color highValueColour;
    private Color lowValueColour;


    // How many RGB steps there are between the high and low colours.
    private int colourValueDistance;

    private double colourScale = SCALE_LOGARITHMIC;

    public GradientColorGenerator() {
        this(Color.BLUE, Color.RED);
    }

    public GradientColorGenerator(Color lowValueColour, Color highValueColour) {
        this.highValueColour = highValueColour;
        this.lowValueColour = lowValueColour;

        this.colourValueDistance = calculateColourDistance(lowValueColour, highValueColour);
    }

    public void setColourScale(double colourScale) {
        this.colourScale = colourScale;
    }

    public String getCellColourString(String data, String min, String max) {

        if (StringUtils.isEmpty(data)) {
            return colorToHexString(Color.WHITE);
        }
        Color cellColour = getCellColour(parseDouble(data), parseDouble(min), parseDouble(max));

        return colorToHexString(cellColour);
    }

    /*
    * Determines what colour a heat map cell should be based upon the cell
    * values.
    */

    protected Color getCellColour(double data, double min, double max) {

        double percentPosition = calculatePercentPosition(data, min, max);

        // Which colour group does that put us in.
        int colourPosition = getColourPosition(percentPosition, colourValueDistance);

        return calculateColorForPosition(colourPosition, lowValueColour, highValueColour);
    }

    /**
     * Calculates what proportion of the way through the possible values is that.
     *
     * @param data
     * @param min
     * @param max
     * @return values in range [0..1]
     */
    protected double calculatePercentPosition(double data, double min, double max) {
        double range = max - min;
        double position = data - min;

        return position / range;
    }

    protected Color calculateColorForPosition(int colourPosition, Color lowValueColour, Color highValueColour) {
        int r = lowValueColour.getRed();
        int g = lowValueColour.getGreen();
        int b = lowValueColour.getBlue();

        // Make n shifts of the colour, where n is the colourPosition.
        for (int i = 0; i < colourPosition; i++) {
            int rDistance = r - highValueColour.getRed();
            int gDistance = g - highValueColour.getGreen();
            int bDistance = b - highValueColour.getBlue();

            if ((Math.abs(rDistance) >= Math.abs(gDistance))
                    && (Math.abs(rDistance) >= Math.abs(bDistance))) {
                // Red must be the largest.
                r = updateColourValue(r, rDistance);
            } else if (Math.abs(gDistance) >= Math.abs(bDistance)) {
                // Green must be the largest.
                g = updateColourValue(g, gDistance);
            } else {
                // Blue must be the largest.
                b = updateColourValue(b, bDistance);
            }
        }

        return new Color(r, g, b);
    }

    /*
    * Returns how many colour shifts are required from the lowValueColour to
    * get to the correct colour position. The result will be different
    * depending on the colour scale used: LINEAR, LOGARITHMIC, EXPONENTIAL.
    */

    protected int getColourPosition(double percentPosition, int colourValueDistance) {
        Preconditions.checkArgument(percentPosition >= 0 && percentPosition <= 1);

        return (int) Math.round(colourValueDistance * Math.pow(percentPosition, colourScale));
    }

    protected int updateColourValue(int colourValue, int colourDistance) {

        if (colourDistance < 0) {
            return colourValue + 1;
        } else if (colourDistance > 0) {
            return colourValue - 1;
        } else {
            // This shouldn't actually happen here.
            return colourValue;
        }
    }

    /*
    * Calculate and update the field for the distance between the low colour
    * and high colour. The distance is the number of steps between one colour
    * and the other using an RGB coding with 0-255 values for each of red,
    * green and blue. So the maximum colour distance is 255 + 255 + 255.
    */

    protected int calculateColourDistance(Color lowValueColour, Color highValueColour) {
        int r1 = lowValueColour.getRed();
        int g1 = lowValueColour.getGreen();
        int b1 = lowValueColour.getBlue();
        int r2 = highValueColour.getRed();
        int g2 = highValueColour.getGreen();
        int b2 = highValueColour.getBlue();

        colourValueDistance = Math.abs(r1 - r2);
        colourValueDistance += Math.abs(g1 - g2);
        colourValueDistance += Math.abs(b1 - b2);

        return colourValueDistance;
    }

    protected String colorToHexString(Color colour) {
        return "#" + Integer.toHexString(colour.getRGB()).substring(2).toUpperCase();
    }
}

