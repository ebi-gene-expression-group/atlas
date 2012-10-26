package uk.ac.ebi.atlas.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class ExperimentRun implements Comparable<ExperimentRun> {

    public static final String ORGANISM_PART_FACTOR_NAME = "ORGANISMPART";
    private String runAccession;

    private Set<FactorValue> factorValues = new LinkedHashSet<>();

    public ExperimentRun(String accession) {
        this.runAccession = checkNotNull(accession);
    }

    public ExperimentRun addFactorValue(String factor, String value) {
        factorValues.add(new FactorValue(factor, value));
        return this;
    }

    public ExperimentRun addOrganismPartFactorValue(String value) {
        return addFactorValue(ORGANISM_PART_FACTOR_NAME, value);
    }

    public String getRunAccession() {
        return runAccession;
    }

    public Set<FactorValue> getFactorValues() {
        return factorValues;
    }

    public String getOrganismPart() {
        for (FactorValue factorValue : factorValues) {
            if (ORGANISM_PART_FACTOR_NAME.equalsIgnoreCase(factorValue.getFactor())) {
                return factorValue.getValue();
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
