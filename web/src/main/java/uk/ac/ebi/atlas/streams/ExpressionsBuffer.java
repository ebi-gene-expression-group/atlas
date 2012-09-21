package uk.ac.ebi.atlas.streams;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Expression;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

class ExpressionsBuffer {

    private static final String DATA_FILE_RECORD_VALIDATION_MESSAGE = "Data file record should contain transcript id and expression levels for all experiment runs";
    //private String transcriptId;
    private Queue<String> expressionLevelsBuffer = new LinkedList<>();

    private Iterator<ExperimentRun> runsCircularQueue;

    private int expectedNumberOfValues;

    public static final int TRANSCRIPT_ID_COLUMN = 0;

    public static Builder forExperimentRuns(List<ExperimentRun> experimentRuns) {

        return new Builder(experimentRuns);

    }

    private ExpressionsBuffer(List<ExperimentRun> orderedRuns) {
        expectedNumberOfValues = orderedRuns.size();
        this.runsCircularQueue = Iterables.cycle(orderedRuns).iterator();
    }


    public Expression poll() {
        String expressionLevelString = expressionLevelsBuffer.poll();

        if (expressionLevelString == null) {
            return null;
        }
        double expressionLevel = Double.parseDouble(expressionLevelString);

        return new Expression(runsCircularQueue.next(), expressionLevel);
    }


    public ExpressionsBuffer reload(String... values) {
        checkState(this.expressionLevelsBuffer.isEmpty(), "Reload must be invoked only when readNext returns null");

        checkArgument(values.length == expectedNumberOfValues + 1, DATA_FILE_RECORD_VALIDATION_MESSAGE);

        expressionLevelsBuffer.clear();

        Collections.addAll(this.expressionLevelsBuffer, values);

        expressionLevelsBuffer.poll();

        return this;
    }

    public static class Builder {

        private List<ExperimentRun> experimentRuns;

        private boolean readyToCreate;

        private Builder(List<ExperimentRun> experimentRuns) {

            this.experimentRuns = experimentRuns;

        }

        public Builder withHeaders(String... dataFileHeaders) {

            List<String> orderedAccessions = Arrays.asList(ArrayUtils.remove(dataFileHeaders, TRANSCRIPT_ID_COLUMN));

            experimentRuns = removeUnrequiredExperimentRuns(orderedAccessions);

            Collections.sort(experimentRuns, experimentRunComparator(orderedAccessions));

            readyToCreate = true;

            return this;
        }

        List<ExperimentRun> removeUnrequiredExperimentRuns(List<String> orderedAccessions) {
            Collection filteredExperimentRuns = Collections2.filter(experimentRuns, isExperimentRunRequired(orderedAccessions));
            return Lists.newArrayList(filteredExperimentRuns);
        }

        public ExpressionsBuffer create() {

            checkState(readyToCreate, "Please specify the order specification with withOrderRunsSpecification before invoking the create method.");

            return new ExpressionsBuffer(experimentRuns);

        }


        Predicate<ExperimentRun> isExperimentRunRequired(final List<String> orderSpecification) {
            return new Predicate<ExperimentRun>() {
                @Override
                public boolean apply(ExperimentRun experimentRun) {
                    return orderSpecification.contains(experimentRun.getRunAccession());
                }
            };
        }


        Comparator<ExperimentRun> experimentRunComparator(final List<String> orderSpecification) {

            return Ordering.natural().onResultOf(new Function<ExperimentRun, Integer>() {
                @Override
                public Integer apply(ExperimentRun experimentRun) {
                    int orderIndexOfRun = orderSpecification.indexOf(experimentRun.getRunAccession());
                    checkState(orderIndexOfRun >= 0, "Illegal state, experimentRun with accession = " + experimentRun.getRunAccession() + "is not included in orderSpecification = " + orderSpecification);
                    return orderIndexOfRun;
                }
            });
        }

    }
}
