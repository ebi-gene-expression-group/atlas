package uk.ac.ebi.atlas.profiles.differential;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class ExpressionsRowDeserializerDifferentialBuilder<T extends Expression, K extends DifferentialExperiment> implements ExpressionsRowDeserializerBuilder<String, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionsRowDeserializerDifferentialBuilder.class);

    final DifferentialExperiment experiment;


    public ExpressionsRowDeserializerDifferentialBuilder(DifferentialExperiment experiment) {
        this.experiment = experiment;
    }

    @Override
    public ExpressionsRowTsvDeserializer<T> build(String... tsvFileHeaders) {
        LOGGER.debug("<withHeaders> data file headers: {}", Arrays.toString(tsvFileHeaders));

         List<Contrast> orderedContrasts = new LinkedList<>();;

        for (String columnHeader : Arrays.asList(tsvFileHeaders)) {
            if (columnHeader.endsWith(".p-value")) {
                String contrastId = StringUtils.substringBefore(columnHeader, ".");
                orderedContrasts.add(experiment.getContrast(contrastId));
            }
        }

        return getBufferInstance(orderedContrasts);

    }

    protected abstract ExpressionsRowTsvDeserializer<T> getBufferInstance(List<Contrast> orderedContrasts);
}
