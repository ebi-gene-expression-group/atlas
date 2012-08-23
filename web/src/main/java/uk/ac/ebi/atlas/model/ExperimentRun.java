package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.Set;

public class ExperimentRun {

    private String accession;

    private Set<FactorValue> factorValues = new HashSet<>();

    public ExperimentRun(String accession, Set<FactorValue> factorValues) {
        this.accession = accession;
        if (factorValues != null) {
            this.factorValues.addAll(factorValues);
        }
    }

    public ExperimentRun(String accession) {
        this.accession = accession;
    }

    public ExperimentRun addFactorValue(String factor, String value) {
        factorValues.add(new FactorValue(factor, value));
        return this;
    }

    public String getAccession() {
        return accession;
    }

    public Set<FactorValue> getFactorValues() {
        return factorValues;
    }
}
