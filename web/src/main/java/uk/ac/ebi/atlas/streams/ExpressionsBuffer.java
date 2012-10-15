package uk.ac.ebi.atlas.streams;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

class ExpressionsBuffer {

    private static final String DATA_FILE_RECORD_VALIDATION_MESSAGE = "Data file record should contain gene id and expression levels for all experiment runs";

    private Queue<String> expressionLevelsBuffer = new LinkedList<>();

    private Iterator<ExperimentRun> runsCircularQueue;

    private int expectedNumberOfValues;

    public static final int GENE_ID_COLUMN = 0;

    protected ExpressionsBuffer(List<ExperimentRun> orderedRuns) {
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

    @Named("expressionsBufferBuilder")
    @Scope("singleton")
    public static class Builder {

        private ExperimentsCache experimentsCache;

        private List<ExperimentRun> experimentRuns;

        private boolean readyToCreate;

        @Inject
        public Builder(ExperimentsCache experimentsCache) {

            this.experimentsCache = experimentsCache;

        }

        public Builder forExperiment(String experimentAccession) {

            this.experimentRuns = experimentsCache.getExperimentRuns(experimentAccession);

            return this;

        }

        public Builder withHeaders(String... dataFileHeaders) {

            checkState(experimentRuns != null, "Builder not properly initialized!");

            List<String> orderedRunAccessions = Arrays.asList(ArrayUtils.remove(dataFileHeaders, GENE_ID_COLUMN));

            experimentRuns = removeUnrequiredExperimentRuns(orderedRunAccessions);

            Collections.sort(experimentRuns, experimentRunComparator(orderedRunAccessions));

            readyToCreate = true;

            return this;
        }

        List<ExperimentRun> removeUnrequiredExperimentRuns(List<String> orderedRunAccessions) {
            Collection filteredExperimentRuns = Collections2.filter(experimentRuns, isExperimentRunRequired(orderedRunAccessions));
            return Lists.newArrayList(filteredExperimentRuns);
        }

        public ExpressionsBuffer create() {

            checkState(readyToCreate, "Builder state not ready for creating the ExpressionBuffer");

            return new ExpressionsBuffer(experimentRuns);

        }


        Predicate<ExperimentRun> isExperimentRunRequired(final List<String> orderedRunAccessions) {
            return new Predicate<ExperimentRun>() {
                @Override
                public boolean apply(ExperimentRun experimentRun) {
                    return orderedRunAccessions.contains(experimentRun.getRunAccession());
                }
            };
        }


        Comparator<ExperimentRun> experimentRunComparator(final List<String> orderedRunAccessions) {

            return Ordering.natural().onResultOf(new Function<ExperimentRun, Integer>() {
                @Override
                public Integer apply(ExperimentRun experimentRun) {
                    int orderIndexOfRun = orderedRunAccessions.indexOf(experimentRun.getRunAccession());
                    checkState(orderIndexOfRun >= 0, "Illegal state, experimentRun with accession = " + experimentRun.getRunAccession() + "is not included in ordered run accessions : " + orderedRunAccessions);
                    return orderIndexOfRun;
                }
            });
        }

    }
}
