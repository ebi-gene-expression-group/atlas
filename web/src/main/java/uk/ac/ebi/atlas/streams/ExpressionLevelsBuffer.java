package uk.ac.ebi.atlas.streams;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

class ExpressionLevelsBuffer {

    private static final String DATA_FILE_RECORD_VALIDATION_MESSAGE = "Data file record should contain transcript id and rpkm values for all experiment runs";
    //private String transcriptId;
    private Queue<String> rpkmValuesBuffer = new LinkedList<>();

    private Iterator<ExperimentRun> runsCircularQueue;

    private int expectedNumberOfValues;

    public static Builder forExperimentRuns(List<ExperimentRun> experimentRuns){

        return new Builder(experimentRuns);

    }

    private ExpressionLevelsBuffer(List<ExperimentRun> orderedRuns) {
        expectedNumberOfValues = orderedRuns.size();
        this.runsCircularQueue = Iterables.cycle(orderedRuns).iterator();
    }


    public ExpressionLevel poll() {
        String rpkmStringValue = rpkmValuesBuffer.poll();

        if (rpkmStringValue == null) {
            return null;
        }
        double rpkmValue = Double.parseDouble(rpkmStringValue);

        return new ExpressionLevel(runsCircularQueue.next(), rpkmValue);
    }


    public ExpressionLevelsBuffer reload(String... values) {
        checkState(this.rpkmValuesBuffer.isEmpty(), "Reload must be invoked only when readNext returns null");

        checkArgument(values.length == expectedNumberOfValues + 1, DATA_FILE_RECORD_VALIDATION_MESSAGE);

        rpkmValuesBuffer.clear();

        Collections.addAll(this.rpkmValuesBuffer, values);

        rpkmValuesBuffer.poll();

        return this;
    }

    public static class Builder {

        private List<ExperimentRun> experimentRuns;

        private boolean readyToCreate;

        private Builder(List<ExperimentRun> experimentRuns){

            this.experimentRuns = experimentRuns;

        }

        public Builder withOrderSpecification(List<String> orderedAccessions){

            experimentRuns = Lists.newArrayList(Collections2.filter(experimentRuns, getPredicate(orderedAccessions)));

            Collections.sort(experimentRuns, buildExperimentRunComparator(orderedAccessions));

            readyToCreate = true;

            return this;
        }

        public ExpressionLevelsBuffer create(){

            checkState(readyToCreate, "Please specify the order specification with withOrderRunsSpecification before invoking the create method.");

            return new ExpressionLevelsBuffer(experimentRuns);

        }


        Predicate getPredicate(final List<String> orderSpecification){
            return new Predicate<ExperimentRun>() {
                @Override
                public boolean apply(ExperimentRun input) {
                    return orderSpecification.contains(input.getRunAccession());
                }
            };
        }


        Comparator<ExperimentRun> buildExperimentRunComparator(final List<String> orderSpecification) {

            return Ordering.natural().onResultOf(new Function<ExperimentRun, Integer>() {
                @Override
                public Integer apply(ExperimentRun experimentRun) {
                    int orderIndexOfRun = orderSpecification.indexOf(experimentRun.getRunAccession());
                    return orderIndexOfRun;
                }
            });
        }

    }
}
