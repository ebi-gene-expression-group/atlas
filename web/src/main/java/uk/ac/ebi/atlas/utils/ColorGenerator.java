package uk.ac.ebi.atlas.utils;

import java.awt.*;

public class ColorGenerator {

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
    private double colourScale;

    public ColorGenerator() {
        this(Color.BLACK, Color.WHITE, SCALE_LINEAR);
    }

    public ColorGenerator(Color highValueColour, Color lowValueColour, double scale) {
        this.highValueColour = highValueColour;
        this.lowValueColour = lowValueColour;

        this.colourScale = SCALE_LINEAR;

        updateColourDistance();
    }

    /*
    This method is used by functional tag.
    //ToDo: maybe extract in a separate class
     */
    public static String getColor(String data, String min, String max) {
        ColorGenerator colorGenerator = new ColorGenerator(Color.RED, Color.WHITE, SCALE_LOGARITHMIC);

        Color cellColour = colorGenerator.getCellColour(Double.parseDouble(data), Double.parseDouble(min), Double.parseDouble(max));


        return "#" + Integer.toHexString(cellColour.getRGB()).substring(2).toUpperCase();
    }


    /*
    * Determines what colour a heat map cell should be based upon the cell
    * values.
    */

    public Color getCellColour(double data, double min, double max) {
        double range = max - min;
        double position = data - min;

        // What proportion of the way through the possible values is that.
        double percentPosition = position / range;

        // Which colour group does that put us in.
        int colourPosition = getColourPosition(percentPosition);

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
                r = changeColourValue(r, rDistance);
            } else if (Math.abs(gDistance) >= Math.abs(bDistance)) {
                // Green must be the largest.
                g = changeColourValue(g, gDistance);
            } else {
                // Blue must be the largest.
                b = changeColourValue(b, bDistance);
            }
        }

        return new Color(r, g, b);
    }

    /*
    * Returns how many colour shifts are required from the lowValueColour to
    * get to the correct colour position. The result will be different
    * depending on the colour scale used: LINEAR, LOGARITHMIC, EXPONENTIAL.
    */

    private int getColourPosition(double percentPosition) {
        return (int) Math.round(colourValueDistance * Math.pow(percentPosition, colourScale));
    }

    private int changeColourValue(int colourValue, int colourDistance) {
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

    private void updateColourDistance() {
        int r1 = lowValueColour.getRed();
        int g1 = lowValueColour.getGreen();
        int b1 = lowValueColour.getBlue();
        int r2 = highValueColour.getRed();
        int g2 = highValueColour.getGreen();
        int b2 = highValueColour.getBlue();

        colourValueDistance = Math.abs(r1 - r2);
        colourValueDistance += Math.abs(g1 - g2);
        colourValueDistance += Math.abs(b1 - b2);
    }

    public static void main(String[] args) {
        ColorGenerator colorGenerator = new ColorGenerator(Color.RED, Color.WHITE, SCALE_LOGARITHMIC);

        Color colour = colorGenerator.getCellColour(50, 1, 100);
//        Color colour = colorGenerator.getCellColour(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
        System.out.println("colour = " + colour);
    }
}

