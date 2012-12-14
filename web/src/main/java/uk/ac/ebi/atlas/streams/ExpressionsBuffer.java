package uk.ac.ebi.atlas.streams;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.*;

class ExpressionsBuffer {

    private static final Logger logger = Logger.getLogger(ExpressionsBuffer.class);

    private Queue<String> expressionLevelsBuffer = new LinkedList<>();

    private Iterator<FactorValue> expectedFactorValues;

    private int expectedNumberOfValues;

    public static final int GENE_ID_COLUMN = 0;

    protected ExpressionsBuffer(List<FactorValue> orderedFactorValues) {
        expectedNumberOfValues = orderedFactorValues.size() + 1;
        this.expectedFactorValues = Iterables.cycle(orderedFactorValues).iterator();
    }


    public Expression poll() {
        String expressionLevelString = expressionLevelsBuffer.poll();

        if (expressionLevelString == null) {
            return null;
        }
        double expressionLevel = Double.parseDouble(expressionLevelString);

        return new Expression(expectedFactorValues.next(), expressionLevel);
    }

    private String gene;

    public ExpressionsBuffer reload(String... values) {
        checkState(this.expressionLevelsBuffer.isEmpty(), "Reload must be invoked only when readNext returns null");

        checkArgument(values.length == expectedNumberOfValues, "Expected " + expectedNumberOfValues + " values but " +
                "found: " + values.length);

        expressionLevelsBuffer.clear();

        Collections.addAll(this.expressionLevelsBuffer, values);

        gene = expressionLevelsBuffer.poll();

        return this;
    }

    @Named("expressionsBufferBuilder")
    @Scope("prototype")
    public static class Builder {

        String experimentAccession;

        private ExperimentsCache experimentsCache;

        private List<FactorValue> orderedFactorValues = new LinkedList<FactorValue>();

        private boolean readyToCreate;
        private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found for Experiment {1}";
        private static final String ORGANISM_PART_NOT_FOUND = "Organism part not found for Run Accessions {0} and Experiment Accession {1}";

        @Inject
        public Builder(ExperimentsCache experimentsCache) {

            this.experimentsCache = experimentsCache;

        }

        public Builder forExperiment(String experimentAccession) {

            this.experimentAccession = experimentAccession;

            return this;

        }

        public Builder withHeaders(String... tsvFileHeaders) {

            logger.debug("<withHeaders> data file headers: " + tsvFileHeaders);

            checkState(experimentAccession != null, "Builder not properly initialized!");

            List<String> columnHeaders = Arrays.asList(ArrayUtils.remove(tsvFileHeaders, GENE_ID_COLUMN));

            for (String columnHeader : columnHeaders) {

                orderedFactorValues.add(getOrganismPart(columnHeader, experimentAccession));

            }
            readyToCreate = true;

            return this;
        }

        private FactorValue getOrganismPart(String columnHeader, String experimentAccession) {

            String[] columnRuns = columnHeader.split(",");

            for (String columnRun : columnRuns) {
                columnRun = columnRun.trim();

                Experiment experiment = experimentsCache.getExperiment(experimentAccession);

                checkNotNull(experiment, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, columnRun, experimentAccession));

                ExperimentRun experimentRun = experiment.getExperimentRun(columnRun);
                if (experimentRun.getExperimentalFactor(FactorValue.FactorType.ORGANISM_PART) != null) {
                    return experimentRun.getExperimentalFactor(FactorValue.FactorType.ORGANISM_PART);
                }
            }

            throw new IllegalStateException(MessageFormat.format(ORGANISM_PART_NOT_FOUND, columnHeader, experimentAccession));
        }

        public ExpressionsBuffer create() {

            checkState(readyToCreate, "Builder state not ready for creating the ExpressionBuffer");

            return new ExpressionsBuffer(orderedFactorValues);

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
