package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

//ToDo: could we rename this class FactorValues and make it Iterable<FactorValue> ?
//that way it would sound like each set factor values is related to a run
//while model now is like: a run is characterized by factor values only
public class ExperimentRun {

    private String runAccession;

    private Set<FactorValue> factorValues = new LinkedHashSet<>();

    public ExperimentRun(String accession, Set<FactorValue> factorValues) {
        this.runAccession = checkNotNull(accession);
        if (factorValues != null) {
            this.factorValues.addAll(factorValues);
        }
    }

    public ExperimentRun(String accession) {
        this.runAccession = accession;
    }

    public ExperimentRun addFactorValue(String factor, String value) {
        factorValues.add(new FactorValue(factor, value));
        return this;
    }

    public String getRunAccession() {
        return runAccession;
    }

    public Set<FactorValue> getFactorValues() {
        return factorValues;
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

        return Objects.equals(this.runAccession, other.runAccession)
                && Objects.equals(this.factorValues, other.factorValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runAccession, getFactorValues());
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("runAccession", runAccession)
                .add("factorValues", factorValues).toString();
    }

}
