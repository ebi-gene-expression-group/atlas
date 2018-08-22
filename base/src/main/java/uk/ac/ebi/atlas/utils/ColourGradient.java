package uk.ac.ebi.atlas.utils;

import com.google.common.base.Preconditions;

import java.awt.Color;

public class ColourGradient {
    protected ColourGradient() {
        throw new UnsupportedOperationException();
    }

    public static String getGradientColour(double value,
                                           double min,
                                           double max,
                                           String lowValueColourString,
                                           String highValueColourString) {
        Preconditions.checkArgument(
                value >= 0.0 && value >= min && value <= max ||
                value < 0 && value <= min && value >= max);

        Color lowValueColour = getColour(lowValueColourString);
        Color highValueColour = getColour(highValueColourString);

        return colourToHexString(getGradientColour(value, min, max, lowValueColour, highValueColour));
    }

    private static Color getColour(String colourString) {
        if (colourString.startsWith("#")) {
            return Color.decode(colourString);
        }
        return getColourByName(colourString);
    }

    private static String colourToHexString(Color colour) {
        return "#" + Integer.toHexString(colour.getRGB()).substring(2).toUpperCase();
    }

    private static Color getColourByName(String colourName) {
        try {
            return (Color) Color.class.getField(colourName).get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // Determines what colour a heat map cell should be based upon the cell values.
    private static Color getGradientColour(double value,
                                           double min,
                                           double max,
                                           Color lowValueColour,
                                           Color highValueColour) {
        double percentPosition = calculatePercentPosition(value, min, max);

        // Which colour group does that put us in.
        int colourPosition = getColourPosition(percentPosition, lowValueColour, highValueColour);

        return calculateColorForPosition(colourPosition, lowValueColour, highValueColour);
    }

    private static Color calculateColorForPosition(int colourPosition, Color lowValueColour, Color highValueColour) {
        int r = lowValueColour.getRed();
        int g = lowValueColour.getGreen();
        int b = lowValueColour.getBlue();

        // Make n shifts of the colour, where n is the colourPosition.
        for (int i = 0; i < colourPosition; i++) {
            int rDistance = r - highValueColour.getRed();
            int gDistance = g - highValueColour.getGreen();
            int bDistance = b - highValueColour.getBlue();

            if ((Math.abs(rDistance) >= Math.abs(gDistance)) && (Math.abs(rDistance) >= Math.abs(bDistance))) {
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

    private static double calculatePercentPosition(double data, double min, double max) {
        double range = max - min;
        double position = data - min;

        return position / range;
    }

    // Returns how many colour shifts are required from the lowValueColour to get to the correct colour position
    private static int getColourPosition(double percentPosition, Color lowValueColour, Color highValueColour) {
        int colourDistance = calculateColourDistance(lowValueColour, highValueColour);
        return (int) Math.round(colourDistance * percentPosition);
    }

    static int updateColourValue(int colourValue, int colourDistance) {
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
    private static int calculateColourDistance(Color lowValueColour, Color highValueColour) {
        int r1 = lowValueColour.getRed();
        int g1 = lowValueColour.getGreen();
        int b1 = lowValueColour.getBlue();
        int r2 = highValueColour.getRed();
        int g2 = highValueColour.getGreen();
        int b2 = highValueColour.getBlue();

        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }
}

