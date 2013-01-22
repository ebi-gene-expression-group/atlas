package uk.ac.ebi.atlas.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class ExperimentRun implements Comparable<ExperimentRun> {

    private String runAccession;

    private Set<FactorValue> factorValues = new LinkedHashSet<>();

    public ExperimentRun(String accession) {
        this.runAccession = checkNotNull(accession);
    }

    public ExperimentRun addFactorValue(FactorValue value) {
        factorValues.add(value);
        return this;
    }

    public String getRunAccession() {
        return runAccession;
    }

    public Set<FactorValue> getFactorValues() {
        return factorValues;
    }

    public FactorValue getFactorValue(String type) {
        for (FactorValue factorValue : factorValues) {
            if (factorValue.getType().equalsIgnoreCase(type)) {
                return factorValue;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExperimentRun other = (ExperimentRun) obj;

        return Objects.equals(this.runAccession, other.runAccession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runAccession);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("runAccession", runAccession)
                .add("factorValues", factorValues).toString();
    }

    @Override
    public int compareTo(ExperimentRun other) {
        return runAccession.compareTo(other.runAccession);
    }
}
