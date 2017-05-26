package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Predicate;
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

public abstract class DifferentialProfileStreamFactory<Expr extends DifferentialExpression,
        E extends DifferentialExperiment, T extends DifferentialProfileStreamOptions, Prof extends Profile<Contrast, Expr, Prof>>
        extends CreatesProfilesFromTsvFiles<Contrast, Expr, E, T, Prof> {
    protected DifferentialProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected Predicate<Expr> filterExpressions(E experiment, T
            options) {
        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(options.getPValueCutoff());
        expressionFilter.setFoldChangeCutOff(options.getFoldChangeCutoff());
        expressionFilter.setRegulation(options.getRegulation());
        return expressionFilter;
    }

    Map<Integer, Contrast> lookUpIndices(String [] header, E experiment){
        ImmutableMap.Builder<Integer, Contrast> b = ImmutableMap.builder();
        for(int i = 0; i < header.length ; i++){
            String columnHeader = header[i];
            if (columnHeader.endsWith(".p-value")) {
                b.put(i,experiment.getDataColumnDescriptor(StringUtils.substringBefore(columnHeader, ".")) );
            }
        }
        return b.build();
    }

    protected abstract class DifferentialProfileFromTsvLine extends ProfileFromTsvLine {

        protected DifferentialProfileFromTsvLine(String [] header, E experiment, Predicate<Expr> filterExpressions) {
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

        private boolean notADouble(@Nullable String value){
            return value == null || "NA".equalsIgnoreCase(value);
        }

        protected boolean notAllDoubles(String ... values){
            for(String value: values){
                if(notADouble(value)){
                    return true;
                }
            }
            return false;
        }
    }

}
