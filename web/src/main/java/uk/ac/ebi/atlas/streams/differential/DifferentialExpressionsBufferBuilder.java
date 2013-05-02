package uk.ac.ebi.atlas.streams.differential;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.model.GeneExpression;
import uk.ac.ebi.atlas.model.cache.ExperimentsCache;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.streams.TsvRowBuffer;
import uk.ac.ebi.atlas.streams.TsvRowBufferBuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public abstract class DifferentialExpressionsBufferBuilder<T extends GeneExpression, K extends DifferentialExperiment> implements TsvRowBufferBuilder<T> {

    private static final Logger LOGGER = Logger.getLogger(RnaSeqExpressionsBufferBuilder.class);
    protected ExperimentsCache<K> experimentsCache;
    private String experimentAccession;
    private List<Contrast> orderedContrasts = new LinkedList<>();


    public DifferentialExpressionsBufferBuilder(ExperimentsCache<K> experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Override
    public DifferentialExpressionsBufferBuilder forExperiment(String experimentAccession) {

        this.experimentAccession = experimentAccession;

        return this;

    }

    @Override
    public DifferentialExpressionsBufferBuilder withHeaders(String... tsvFileHeaders) {

        LOGGER.debug("<withHeaders> data file headers: " + Arrays.toString(tsvFileHeaders));

        checkState(experimentAccession != null, "Builder not properly initialized!");

        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        List<String> columnHeaders = Arrays.asList(tsvFileHeaders);

        for (String columnHeader : columnHeaders) {
            if (columnHeader.endsWith(".p-value")) {
                String contrastId = StringUtils.substringBefore(columnHeader, ".");
                orderedContrasts.add(experiment.getContrast(contrastId));
            }
        }

        return this;
    }

    @Override
    public TsvRowBuffer<T> build() {

        checkState(!orderedContrasts.isEmpty(), "Builder state not ready for creating the ExpressionBuffer");

        return getBufferInstance(orderedContrasts);

    }

    protected abstract TsvRowBuffer<T> getBufferInstance(List<Contrast> orderedContrasts);
}
