package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.util.*;

public class DifferentialExperimentContrastLines implements Iterable<String[]> {

    private final LinkedHashSet<ImmutableList<String>> contrastDetails;
    private final LinkedHashSet<ImmutableList<String>> result = new LinkedHashSet<>();

    public DifferentialExperimentContrastLines(DifferentialExperiment experiment) {
        this.contrastDetails = buildContrastDetails(experiment);
    }

    private LinkedHashSet<ImmutableList<String>> buildContrastDetails(DifferentialExperiment experiment) {
        for (Contrast contrast : experiment.getContrasts()) {
            for (String assayAccession : contrast.getReferenceAssayGroup()) {
                this.populateSamples(experiment, assayAccession, contrast, "reference");
                this.populateFactors(experiment, assayAccession, contrast, "reference");
            }

            for (String assayAccession : contrast.getTestAssayGroup()) {
                this.populateSamples(experiment, assayAccession, contrast, "test");
                this.populateFactors(experiment, assayAccession, contrast, "test");
            }
        }

        return result;
    }

    private void populateSamples(DifferentialExperiment experiment, String assayAccession, Contrast contrast, String value){
        for (Map.Entry<String, String> sample : experiment.getExperimentDesign().getSamples(assayAccession).entrySet()) {
            ImmutableList<String> line = ImmutableList.of(experiment.getAccession(), contrast.getId(), value, "characteristic",
                    sample.getKey(), sample.getValue());
            result.add(line);
        }
    }

    private void populateFactors(DifferentialExperiment experiment, String assayAccession, Contrast contrast, String value){
        for (Map.Entry<String, String> factor : experiment.getExperimentDesign().getFactors(assayAccession).entrySet()) {
            ImmutableList<String> line = ImmutableList.of(experiment.getAccession(), contrast.getId(), value, "factor",
                    factor.getKey(), factor.getValue());
            result.add(line);
        }
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
