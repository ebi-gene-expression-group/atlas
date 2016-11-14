
package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

@Named
public class DataWriterFactory {

    @Value("#{configuration['diff.experiment.raw-counts.path.template']}")
    private String differentialExperimentRawCountsFileUrlTemplate;

    @Value("#{configuration['microarray.experiment.data.path.template']}")
    private String microarrayExperimentAnalyticsFileUrlTemplate;

    @Value("#{configuration['microarray.normalized.data.path.template']}")
    private String microarrayExperimentNormalizedFileUrlTemplate;

    @Value("#{configuration['microarray.log-fold-changes.data.path.template']}")
    private String microarrayExperimentLogFoldFileUrlTemplate;

    private final DataFileHub dataFileHub;
    private CsvReaderFactory csvReaderFactory;


    @Inject
    public DataWriterFactory(DataFileHub dataFileHub, CsvReaderFactory csvReaderFactory) {
        this.dataFileHub = dataFileHub;
        this.csvReaderFactory = csvReaderFactory;
    }


    public ExpressionsWriter getRnaSeqAnalyticsDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {

        final AnalyticsDataHeaderBuilder headerBuilder = new AnalyticsDataHeaderBuilder();
        headerBuilder.setExperiment(experiment);

        return new ExpressionsWriterReadingFromAtlasResource(dataFileHub
                .getDifferentialExperimentFiles(experiment.getAccession()).analytics, headerBuilder, new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER));

    }

    public ExpressionsWriter getRnaSeqRawDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {

        ExpressionsWriterImpl expressionsWriter = new ExpressionsWriterImpl(csvReaderFactory);
        expressionsWriter.setFileUrlTemplate(differentialExperimentRawCountsFileUrlTemplate);

        initWriter(expressionsWriter, experiment.getAccession(), responseWriter);

        return expressionsWriter;
    }


    public ExpressionsWriter getMicroarrayAnalyticsDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter, String arrayDesignAccession) {

        final MicroarrayDataHeaderBuilder headerBuilder = new MicroarrayDataHeaderBuilder();
        headerBuilder.setExperiment(experiment);

        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl(csvReaderFactory);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentAnalyticsFileUrlTemplate);
        microarrayDataWriter.setArrayDesignAccession(arrayDesignAccession);
        microarrayDataWriter.setHeaderBuilder(headerBuilder);

        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter);

        return microarrayDataWriter;

    }

    public ExpressionsWriter getMicroarrayRawDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter, String arrayDesignAccession) {

        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl(csvReaderFactory);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentNormalizedFileUrlTemplate);
        microarrayDataWriter.setArrayDesignAccession(arrayDesignAccession);

        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter);

        return microarrayDataWriter;
    }

    public ExpressionsWriter getMicroarrayLogFoldDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter
            , String arrayDesignAccession) {

        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl(csvReaderFactory);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentLogFoldFileUrlTemplate);
        microarrayDataWriter.setArrayDesignAccession(arrayDesignAccession);


        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter);

        return microarrayDataWriter;
    }


    private void initWriter(ExpressionsWriterImpl expressionsWriter, String experimentAccession,
                            PrintWriter responseWriter) {

        expressionsWriter.setResponseWriter(new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER));
        expressionsWriter.setExperimentAccession(experimentAccession);
    }


}
