package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.MoreObjects;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExperimentRun implements Comparable<ExperimentRun>, Serializable {

    private String runAccession;
    private FactorSet factorGroup = new FactorSet();

    public ExperimentRun(String accession) {
        this.runAccession = checkNotNull(accession);
    }

    public ExperimentRun addFactor(Factor factor) {
        factorGroup.add(factor);
        return this;
    }

    public String getAccession() {
        return runAccession;
    }

    public FactorGroup getFactorGroup() {
        return factorGroup;
    }

    public Factor getFactorByType(String type) {
        return factorGroup.factorOfType(type);
    }

    public boolean containsAll(Set<Factor> factors){
        return factorGroup.containsAll(factors);
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
        return MoreObjects.toStringHelper(this)
                .add("runAccession", runAccession)
                .add("factorGroup", factorGroup).toString();
    }

    @Override
    public int compareTo(ExperimentRun other) {
        return runAccession.compareTo(other.runAccession);
    }

}
