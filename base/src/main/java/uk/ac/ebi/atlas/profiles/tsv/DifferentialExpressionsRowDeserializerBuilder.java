package uk.ac.ebi.atlas.profiles.tsv;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class DifferentialExpressionsRowDeserializerBuilder<Expr extends DifferentialExpression>
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
