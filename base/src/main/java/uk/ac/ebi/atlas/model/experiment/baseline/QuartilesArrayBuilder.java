package uk.ac.ebi.atlas.model.experiment.baseline;

public class QuartilesArrayBuilder {

    public static double[] create(String commaSeparatedValues) {
        String[] vals = commaSeparatedValues.split(",", 5);

        //min, lower, median, upper, max
        return new double[] {
                Double.parseDouble(vals[0]),
                Double.parseDouble(vals[1]),
                Double.parseDouble(vals[2]),
                Double.parseDouble(vals[3]),
                Double.parseDouble(vals[4])
        };
    }
}
