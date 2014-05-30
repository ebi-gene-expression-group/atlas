package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

public class BaselineExpressionViewModel {

    private final String factorName;
    private final String color;
    private final String value;
    private final String svgPathId;

    public BaselineExpressionViewModel(String factorName, String color, String value, String svgPathId) {
        this.factorName = factorName;
        this.color = color;
        this.value = value;
        this.svgPathId = svgPathId;
    }

    public String getFactorName() {
        return factorName;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    public String getSvgPathId() {
        return svgPathId;
    }
}
