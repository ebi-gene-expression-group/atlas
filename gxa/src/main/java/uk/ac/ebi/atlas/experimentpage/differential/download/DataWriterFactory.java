package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

@Named
public class DataWriterFactory {

    private final DataFileHub dataFileHub;

    @Inject
    public DataWriterFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public ExpressionsWriter getRnaSeqAnalyticsDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {
        final AnalyticsDataHeaderBuilder analyticsDataHeaderBuilder = new AnalyticsDataHeaderBuilder(experiment);
        return new ExpressionsWriterReadingFromAtlasResource(dataFileHub.getDifferentialExperimentFiles(experiment
                .getAccession()).analytics, new Function<String[], String[]>() {
            @Nullable
            @Override
            public String[] apply(@Nullable String[] strings) {
                return analyticsDataHeaderBuilder.buildHeader(strings);
            }
        }, new CSVWriter(responseWriter, '\t'));

    }

    public ExpressionsWriter getRnaSeqRawDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {
        return new ExpressionsWriterReadingFromAtlasResource(dataFileHub.getDifferentialExperimentFiles(experiment
                .getAccession()).rawCounts, Functions.<String[]>identity(), new CSVWriter(responseWriter, '\t'));
    }


    public ExpressionsWriter getMicroarrayAnalyticsDataWriter(DifferentialExperiment experiment,
                                                              PrintWriter responseWriter,
                                                              String arrayDesignAccession)
    throws IOException {
        final MicroarrayDataHeaderBuilder headerBuilder = new MicroarrayDataHeaderBuilder(experiment);

        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl();
        microarrayDataWriter.setReader(
                dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesignAccession)
                .analytics.getReader());
        microarrayDataWriter.setHeaderBuilder(headerBuilder);

        initWriter(microarrayDataWriter, responseWriter);
        return microarrayDataWriter;
    }

    public ExpressionsWriter getMicroarrayRawDataWriter(DifferentialExperiment experiment,
                                                        PrintWriter responseWriter,
                                                        String arrayDesignAccession)
    throws IOException {
        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl();
        microarrayDataWriter.setReader(
                dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesignAccession)
                .normalizedExpressions.getReader());

        initWriter(microarrayDataWriter, responseWriter);
        return microarrayDataWriter;
    }

    public ExpressionsWriter getMicroarrayLogFoldDataWriter(DifferentialExperiment experiment,
                                                            PrintWriter responseWriter,
                                                            String arrayDesignAccession)
    throws IOException {
        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl();
        microarrayDataWriter.setReader(
                dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesignAccession)
                .logFoldChanges.getReader());

        initWriter(microarrayDataWriter, responseWriter);
        return microarrayDataWriter;
    }


    private void initWriter(ExpressionsWriterImpl expressionsWriter,
                            PrintWriter responseWriter) {
        expressionsWriter.setResponseWriter(new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER));
    }


}
