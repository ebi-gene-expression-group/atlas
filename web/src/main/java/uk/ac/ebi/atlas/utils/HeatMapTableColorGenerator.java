package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang.StringUtils;

import java.awt.*;

public class HeatMapTableColorGenerator {

    private GradientColorGenerator colorGenerator;

    public static String getColor(String data, String min, String max) {

        GradientColorGenerator colorGenerator = new GradientColorGenerator(Color.RED, Color.WHITE);

        colorGenerator.setColourScale(GradientColorGenerator.SCALE_LOGARITHMIC);

        if (StringUtils.isEmpty(data)) {
            return colorToHexString(Color.WHITE);
        }
        Color cellColour = colorGenerator.getCellColour(Double.parseDouble(data), Double.parseDouble(min), Double.parseDouble(max));


        return colorToHexString(cellColour);
    }

    private static String colorToHexString(Color colour) {
        return "#" + Integer.toHexString(colour.getRGB()).substring(2).toUpperCase();
    }
}
