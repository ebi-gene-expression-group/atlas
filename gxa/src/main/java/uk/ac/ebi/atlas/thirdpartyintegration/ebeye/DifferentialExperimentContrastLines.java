package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class DifferentialExperimentContrastLines implements Iterable<String[]> {

    private final LinkedHashSet<ImmutableList<String>> contrastDetails;
    private final LinkedHashSet<ImmutableList<String>> result = new LinkedHashSet<>();

    public DifferentialExperimentContrastLines(DifferentialExperiment experiment) {
        this.contrastDetails = buildContrastDetails(experiment);
    }

    private LinkedHashSet<ImmutableList<String>> buildContrastDetails(DifferentialExperiment experiment) {
        for (Contrast contrast : experiment.getDataColumnDescriptors()) {
            for (String assayAccession : contrast.getReferenceAssayGroup().assaysAnalyzedForThisDataColumn()) {
                this.populateSamples(experiment, assayAccession, contrast, "reference");
                this.populateFactors(experiment, assayAccession, contrast, "reference");
            }

            for (String assayAccession : contrast.getTestAssayGroup().assaysAnalyzedForThisDataColumn()) {
                this.populateSamples(experiment, assayAccession, contrast, "test");
                this.populateFactors(experiment, assayAccession, contrast, "test");
            }
        }

        return result;
    }

    private void populateSamples(DifferentialExperiment experiment, String assayAccession, Contrast contrast, String value){
        for (SampleCharacteristic sample : experiment.getExperimentDesign().getSampleCharacteristics(assayAccession)) {
            ImmutableList<String> line = ImmutableList.of(experiment.getAccession(), contrast.getId(), value, "characteristic",
                    sample.header(), sample.value(), joinURIs(sample.valueOntologyTerms()));
            result.add(line);
        }
    }

    private void populateFactors(DifferentialExperiment experiment, String assayAccession, Contrast contrast, String value){
        for (Factor factor : experiment.getExperimentDesign().getFactors(assayAccession)) {
            ImmutableList<String> line = ImmutableList.of(experiment.getAccession(), contrast.getId(), value, "factor",
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
        return new ContrastIterator();
    }

    class ContrastIterator implements Iterator<String[]> {

        private final Iterator<ImmutableList<String>> contrastDetailsIterator;

        ContrastIterator() {
            contrastDetailsIterator = contrastDetails.iterator();
        }

        @Override
        public boolean hasNext() {
            return contrastDetailsIterator.hasNext();
        }

        @Override
        public String[] next() {
            ImmutableList<String> line = contrastDetailsIterator.next();
            return line.toArray(new String[line.size()]);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
