package uk.ac.ebi.atlas.model;

public class FactorValue {

    private String factor;

    private String value;

    public FactorValue(String factor, String value) {
        this.factor = factor;
        this.value = value;
    }

    public String getFactor() {
        return factor;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return factor + ":" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FactorValue that = (FactorValue) o;

        if (factor != null ? !factor.equals(that.factor) : that.factor != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = factor != null ? factor.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

}
