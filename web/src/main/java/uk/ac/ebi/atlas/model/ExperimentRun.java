package uk.ac.ebi.atlas.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

//This class will probably be renamed or refactored when we will want to represent runs as a factor values + expression levels
public class ExperimentRun implements Comparable<ExperimentRun>{

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

    public String getOrganismPart() {
        for (FactorValue factorValue: factorValues) {
            if ("ORGANISMPART".equalsIgnoreCase(factorValue.getFactor())){
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
