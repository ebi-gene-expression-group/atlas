package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Stream;

public class FactorAcrossExperiments extends DescribesDataColumns implements Comparable<FactorAcrossExperiments> {

    private final Set<OntologyTerm> valueOntologyTerms;

    public FactorAcrossExperiments(Factor factor) {
        this(factor.getValue(), factor.getValueOntologyTerms());
    }

    private FactorAcrossExperiments(String label, Set<OntologyTerm> valueOntologyTerms) {
        super(label);
        this.valueOntologyTerms = valueOntologyTerms;
    }

    public Stream<OntologyTerm> getValueOntologyTerms() {
        return valueOntologyTerms.stream();
    }

    @Override
    public Set<String> assaysAnalyzedForThisDataColumn() {
        return ImmutableSet.of();
    }

    @Override
    public Set<BiologicalReplicate> biologicalReplicatesForThisDataColumn() {
        return ImmutableSet.of();
    }

    @Override
    public String toString() {
        return "FactorAcrossExperiments id " + id;
    }

    @Override
    public int compareTo(FactorAcrossExperiments o) {
        return Comparator.comparing(FactorAcrossExperiments::getId).compare(this, o);
    }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.addProperty("assayGroupId", id);
        o.addProperty("factorValue", id);
        o.add("factorValueOntologyTermId", OntologyTerm.jsonForHeaders(valueOntologyTerms));
        return o;
    }
}
