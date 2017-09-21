package uk.ac.ebi.atlas.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.util.Objects;
import java.util.Set;

public class FactorAcrossExperiments extends DescribesDataColumns implements Comparable<FactorAcrossExperiments> {

    private final Set<OntologyTerm> valueOntologyTerms;

    public FactorAcrossExperiments(Factor factor){
        this(factor.getValue(), factor.getValueOntologyTerms());
    }

    private FactorAcrossExperiments(String label, Set<OntologyTerm> valueOntologyTerms) {
        super(label);
        this.valueOntologyTerms = valueOntologyTerms;
    }

    public Set<OntologyTerm> getValueOntologyTerms(){
        return valueOntologyTerms;
    }


    @Override
    public Set<String> assaysAnalyzedForThisDataColumn() {
        return ImmutableSet.of();
    }

    @Override
    public String toString(){
        return "FactorAcrossExperiments id "+id;
    }

    @Override
    public int compareTo(FactorAcrossExperiments o) {
        return ComparisonChain.start().compare(this.id, o.id).result();
    }

    public JsonObject toJson(){
        JsonObject o = new JsonObject();
        o.addProperty("assayGroupId", id);
        o.addProperty("factorValue", id);
        o.add("factorValueOntologyTermId", OntologyTerm.jsonForHeaders(valueOntologyTerms));
        return o;
    }
}
