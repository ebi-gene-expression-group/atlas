package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

public abstract class DifferentialProfileStreamFactory<
        X extends DifferentialExpression,
        E extends DifferentialExperiment,
        T extends DifferentialProfileStreamOptions,
        P extends Profile<Contrast, X, P>> extends CreatesProfilesFromTsvFiles<Contrast, X, E, T, P> {
    protected DifferentialProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected Predicate<X> filterExpressions(T options) {
        IsDifferentialExpressionAboveCutOff<X> expressionFilter = new IsDifferentialExpressionAboveCutOff<>();
        expressionFilter.setPValueCutoff(options.getPValueCutoff());
        expressionFilter.setFoldChangeCutOff(options.getFoldChangeCutoff());
        expressionFilter.setRegulation(options.getRegulation());
        return expressionFilter;
    }

    private Map<Integer, Contrast> lookUpIndices(String[] header, E experiment) {
        ImmutableMap.Builder<Integer, Contrast> b = ImmutableMap.builder();
        for (int i = 0; i < header.length; i++) {
            String columnHeader = header[i];
            if (columnHeader.endsWith(".p-value")) {
                if (experiment.getDataColumnDescriptor(StringUtils.substringBefore(columnHeader, ".")) != null) {
                    b.put(i, experiment.getDataColumnDescriptor(StringUtils.substringBefore(columnHeader, ".")));
                }
            }
        }
        return b.build();
    }

    protected abstract class DifferentialGoThroughTsvLineAndPickUpExpressionsByIndex
                             extends GoThroughTsvLineAndPickUpExpressionsByIndex {

        protected DifferentialGoThroughTsvLineAndPickUpExpressionsByIndex(String[] header,
                                                                          E experiment,
                                                                          Predicate<X> filterExpressions) {
            super(lookUpIndices(header, experiment), filterExpressions);
        }

        protected double parseDouble(String value) {
            if (value.equalsIgnoreCase("inf")) {
                return Double.POSITIVE_INFINITY;
            }
            if (value.equalsIgnoreCase("-inf")) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.parseDouble(value);
        }

        private boolean notADouble(@Nullable String value) {
            return value == null || "NA".equalsIgnoreCase(value);
        }

        protected boolean notAllDoubles(String... values) {
            for (String value: values) {
                if (notADouble(value)) {
                    return true;
                }
            }
            return false;
        }
    }

}
