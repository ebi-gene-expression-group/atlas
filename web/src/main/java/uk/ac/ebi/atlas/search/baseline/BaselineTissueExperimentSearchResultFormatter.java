package uk.ac.ebi.atlas.search.baseline;


import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.Iterator;
import java.util.SortedSet;

public class BaselineTissueExperimentSearchResultFormatter implements Iterable<String[]> {
    final BaselineExperimentProfilesList experimentProfiles;
    final SortedSet<Factor> tissueFactorsAcrossAllExperiments;

    public BaselineTissueExperimentSearchResultFormatter(BaselineTissueExperimentSearchResult searchResult) {
        this(searchResult.getExperimentProfiles(), searchResult.getTissueFactorsAcrossAllExperiments());
    }

    public BaselineTissueExperimentSearchResultFormatter(BaselineExperimentProfilesList experimentProfiles, SortedSet<Factor> tissueFactorsAcrossAllExperiments) {
        this.experimentProfiles = experimentProfiles;
        this.tissueFactorsAcrossAllExperiments = tissueFactorsAcrossAllExperiments;
    }

    private String[] format(SortedSet<Factor> tissueFactorsAcrossAllExperiments) {
        String[] factors = new String[tissueFactorsAcrossAllExperiments.size()];

        int i = 0;

        for (Factor factor : tissueFactorsAcrossAllExperiments) {
            factors[i++] = factor.getValue();
        }

        return factors;
    }

    public String[] getHeaders() {
        return ArrayUtils.addAll(new String[] {"Experiment"} , format(tissueFactorsAcrossAllExperiments));
    }

    public boolean isEmpty() {
        return experimentProfiles.isEmpty();
    }

    @Override
    public Iterator<String[]> iterator() {
        return new FormatIterator(experimentProfiles.iterator());
    }

    private class FormatIterator implements Iterator<String[]> {

        private final Iterator<BaselineExperimentProfile> iterator;

        public FormatIterator(Iterator<BaselineExperimentProfile> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public String[] next() {
            return format(iterator.next());
        }

        private String[] format(BaselineExperimentProfile profile) {
            String[] row = new String[tissueFactorsAcrossAllExperiments.size() + 1];

            int i = 0;

            row[i++] = profile.getName();

            for (Factor factor : tissueFactorsAcrossAllExperiments) {
                BaselineExpression expression = profile.getExpression(factor);
                if (expression == null) {
                    row[i++] = "";
                } else {
                    String levelAsString = expression.getLevelAsString();
                    row[i++] = "NT".equals(levelAsString) ? "NA" : levelAsString;
                }
            }

            return row;
        }

        @Override
        public void remove() {
            throw new NotImplementedException();
        }
    }
}