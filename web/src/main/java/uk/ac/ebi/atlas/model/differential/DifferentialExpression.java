package uk.ac.ebi.atlas.model.differential;

public class DifferentialExpression {

    private double pValue;

    private double foldChange;

    private Contrast contrast;

    public DifferentialExpression(double pValue, double foldChange, Contrast contrast) {
        this.pValue = pValue;
        this.foldChange = foldChange;
        this.contrast = contrast;
    }

    public double getPValue() {
        return pValue;
    }

    public double getFoldChange() {
        return foldChange;
    }

    public Contrast getContrast() {
        return contrast;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        DifferentialExpression that = (DifferentialExpression) other;

        if (Double.compare(that.foldChange, foldChange) != 0) return false;
        if (Double.compare(that.pValue, pValue) != 0) return false;
        if (!contrast.equals(that.contrast)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = pValue != +0.0d ? Double.doubleToLongBits(pValue) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = foldChange != +0.0d ? Double.doubleToLongBits(foldChange) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + contrast.hashCode();
        return result;
    }
}
