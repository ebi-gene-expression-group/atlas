package uk.ac.ebi.atlas.model;

import java.util.Objects;

public class Expression {
    private double level;

    private FactorValue organismPart;

    public Expression(FactorValue organismPart, double level) {
        this.organismPart = organismPart;
        this.level = level;
    }

    public String getOrganismPart() {
        return organismPart.getValue();
    }

    public double getLevel() {
        return level;
    }

    public boolean isGreaterThan(double level) {
        return Double.compare(this.level, level) > 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(organismPart, level);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Expression other = (Expression) obj;

        return Objects.equals(organismPart, other.organismPart)
                && Objects.equals(level, level);
    }
}