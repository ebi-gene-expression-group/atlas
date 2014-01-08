package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.util.*;

public class DifferentialExperimentContrastDetailsFormatter implements Iterable<String[]> {

    private final LinkedHashSet<ImmutableList<String>> contrastDetails;

    public DifferentialExperimentContrastDetailsFormatter(DifferentialExperiment experiment) {
        this.contrastDetails = buildContrastDetails(experiment);
    }

    private LinkedHashSet<ImmutableList<String>> buildContrastDetails(DifferentialExperiment experiment) {
        LinkedHashSet<ImmutableList<String>> result = new LinkedHashSet<>();

        for (Contrast contrast : experiment.getContrasts()) {

            for (String assayAccession : contrast.getReferenceAssayGroup()) {

                for (Map.Entry<String, String> sample : experiment.getExperimentDesign().getSamples(assayAccession).entrySet()) {
                    ImmutableList<String> line = ImmutableList.<String>of(experiment.getAccession(), contrast.getId(), "reference", "characteristic", sample.getKey(), sample.getValue());
                    result.add(line);
                }

                for(Map.Entry<String, String> factor : experiment.getExperimentDesign().getFactors(assayAccession).entrySet()){
                    ImmutableList<String> line = ImmutableList.<String>of(experiment.getAccession(), contrast.getId(), "reference", "factor", factor.getKey(), factor.getValue());
                    result.add(line);
                }
            }

            for (String assayAccession : contrast.getTestAssayGroup()) {
                for (Map.Entry<String, String> sample : experiment.getExperimentDesign().getSamples(assayAccession).entrySet()) {
                    ImmutableList<String> line = ImmutableList.<String>of(experiment.getAccession(), contrast.getId(), "test", "characteristic", sample.getKey(), sample.getValue());
                    result.add(line);
                }

                for(Map.Entry<String, String> factor : experiment.getExperimentDesign().getFactors(assayAccession).entrySet()){
                    ImmutableList<String> line = ImmutableList.<String>of(experiment.getAccession(), contrast.getId(), "test", "factor", factor.getKey(), factor.getValue());
                    result.add(line);
                }

            }

        }


        return result;
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
