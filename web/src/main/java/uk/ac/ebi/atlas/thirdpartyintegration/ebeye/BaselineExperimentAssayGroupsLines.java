package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;


import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class BaselineExperimentAssayGroupsLines implements Iterable<String[]> {

    private final LinkedHashSet<ImmutableList<String>> result = new LinkedHashSet<>();
    private final LinkedHashSet<ImmutableList<String>> assayGroupsDetails;

    public BaselineExperimentAssayGroupsLines(BaselineExperiment experiment) {
        assayGroupsDetails = buildAssayGroupsDetails(experiment);
    }

    private LinkedHashSet<ImmutableList<String>> buildAssayGroupsDetails(BaselineExperiment experiment) {
        AssayGroups assayGroups = experiment.getAssayGroups();

        for(AssayGroup assayGroupById : assayGroups) {
            for(String assayAccession : assayGroupById) {
                this.populateSamples(experiment, assayAccession, assayGroupById);
                this.populateFactors(experiment, assayAccession, assayGroupById);
            }
        }

        return result;
    }

    private void populateSamples(BaselineExperiment experiment, String assayAccession, AssayGroup assayGroup){
        for (SampleCharacteristic sample : experiment.getExperimentDesign().getSampleCharacteristics(assayAccession)) {
            ImmutableList<String> line = ImmutableList.of(experiment.getAccession(), assayGroup.getId(), "characteristic",
                    sample.header(), sample.value(), joinURIs(sample.valueOntologyTerms()));
            result.add(line);
        }
    }

    private void populateFactors(BaselineExperiment experiment, String assayAccession, AssayGroup assayGroup){
        for (Factor factor : experiment.getExperimentDesign().getFactors(assayAccession)) {
            ImmutableList<String> line = ImmutableList.of(experiment.getAccession(), assayGroup.getId(), "factor",
                    factor.getHeader(), factor.getValue(), joinURIs(factor.getValueOntologyTerms()));
            result.add(line);
        }
    }

    private static String joinURIs(Set<OntologyTerm> ontologyTerms) {
        final String ONTOLOGY_TERM_DELIMITER = " ";

        StringBuilder sb = new StringBuilder();
        for (OntologyTerm ontologyTerm : ontologyTerms) {
            sb.append(ontologyTerm.uri()).append(ONTOLOGY_TERM_DELIMITER);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }


    @Override
    public Iterator<String[]> iterator() {
        return new AssayGroupsIterator();
    }

    class AssayGroupsIterator implements Iterator<String[]> {

        private final Iterator<ImmutableList<String>> assayGroupsDetailsIterator;

        AssayGroupsIterator() {
            assayGroupsDetailsIterator = assayGroupsDetails.iterator();
        }

        @Override
        public boolean hasNext() {
            return assayGroupsDetailsIterator.hasNext();
        }

        @Override
        public String[] next() {
            ImmutableList<String> line = assayGroupsDetailsIterator.next();
            return line.toArray(new String[line.size()]);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
