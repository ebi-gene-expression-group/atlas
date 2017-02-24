package uk.ac.ebi.atlas.profiles.differential;

import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class DifferentialProfileStreamFactory<Expr extends DifferentialExpression,
        E extends DifferentialExperiment, T extends DifferentialProfileStreamOptions, Prof extends Profile<Contrast, Expr>>
        extends ProfileStreamFactory<Contrast, Expr, E, T, Prof> {
    protected DifferentialProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected Predicate<Expr> filterExpressions(E experiment, T
            options) {
        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(options.getPValueCutOff());
        expressionFilter.setFoldChangeCutOff(options.getFoldChangeCutOff());
        expressionFilter.setRegulation(options.getRegulation());
        return expressionFilter;
    }

    protected static abstract class DifferentialExpressionsRowDeserializerBuilder<Expr extends DifferentialExpression>
            implements ExpressionsRowDeserializerBuilder<Expr> {

        private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialExpressionsRowDeserializerBuilder.class);

        final DifferentialExperiment experiment;


        public DifferentialExpressionsRowDeserializerBuilder(DifferentialExperiment experiment) {
            this.experiment = experiment;
        }

        @Override
        public ExpressionsRowDeserializer<Expr> build(String... tsvFileHeaders) {
            LOGGER.debug("<withHeaders> data file headers: {}", Arrays.toString(tsvFileHeaders));

            List<Contrast> orderedContrasts = new LinkedList<>();

            for (String columnHeader : Arrays.asList(tsvFileHeaders)) {
                if (columnHeader.endsWith(".p-value")) {
                    String contrastId = StringUtils.substringBefore(columnHeader, ".");
                    orderedContrasts.add(experiment.getDataColumnDescriptor(contrastId));
                }
            }

            return getBufferInstance(orderedContrasts);

        }

        protected abstract ExpressionsRowDeserializer<Expr> getBufferInstance(List<Contrast> orderedContrasts);
    }

}
