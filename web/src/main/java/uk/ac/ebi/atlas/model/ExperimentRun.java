package uk.ac.ebi.atlas.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class ExperimentRun implements Comparable<ExperimentRun> {

    private String runAccession;

    private Set<Factor> factors = new LinkedHashSet<>();

    public ExperimentRun(String accession) {
        this.runAccession = checkNotNull(accession);
    }

    public ExperimentRun addFactor(Factor factor) {
        factors.add(factor);
        return this;
    }

    public String getRunAccession() {
        return runAccession;
    }

    public Set<Factor> getFactors() {
        return factors;
    }

    public Factor getFactor(String type) {
        for (Factor factor : factors) {
            if (factor.getType().equalsIgnoreCase(type)) {
                return factor;
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
                .add("factors", factors).toString();
    }

    @Override
    public int compareTo(ExperimentRun other) {
        return runAccession.compareTo(other.runAccession);
    }
}
