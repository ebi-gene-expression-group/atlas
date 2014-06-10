package uk.ac.ebi.atlas.profiles.differential.viewmodel;

public class DifferentialExpressionViewModel {

    private final String contrastName;
    private final String color;
    private final String foldChange;
    private final String pValue;
    private final String tStat;

    public DifferentialExpressionViewModel(String contrastName, String color, String foldChange, String pValue, String tStat) {
        this.contrastName = contrastName;
        this.color = color;
        this.foldChange = foldChange;
        this.pValue = pValue;
        this.tStat = tStat;
    }

    public String getContrastName() {
        return contrastName;
    }

    public String getColor() {
        return color;
    }

    public String getFoldChange() {
        return foldChange;
    }

    public String getpValue() {
        return pValue;
    }

    public String gettStat() {
        return tStat;
    }
}
