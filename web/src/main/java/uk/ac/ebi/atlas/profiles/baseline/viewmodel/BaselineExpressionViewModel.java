package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

public class BaselineExpressionViewModel {

    private final String factorName;
    private final String color;
    private final String value;
    private final String svgPathId;
    private final double[] quartiles;

    public BaselineExpressionViewModel(String factorName, String color, String value, String svgPathId, double[] quartiles) {
        this.factorName = factorName;
        this.color = color;
        this.value = value;
        this.svgPathId = svgPathId;
        this.quartiles = quartiles;
    }
}
