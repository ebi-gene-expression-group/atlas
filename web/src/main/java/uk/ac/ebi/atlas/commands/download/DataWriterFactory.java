package uk.ac.ebi.atlas.commands.download;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

@Named
public class DataWriterFactory {

    @Value("#{configuration['diff.experiment.raw-counts.path.template']}")
    private String differentialExperimentRawCountsFileUrlTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialExperimentAnalyticsFileUrlTemplate;

    @Value("#{configuration['microarray.experiment.data.path.template']}")
    private String microarrayExperimentAnalyticsFileUrlTemplate;

    @Value("#{configuration['microarray.normalized.data.path.template']}")
    private String microarrayExperimentNormalizedFileUrlTemplate;

    @Value("#{configuration['microarray.log-fold-changes.data.path.template']}")
    private String microarrayExperimentLogFoldFileUrlTemplate;


    private TsvReaderUtils tsvReaderUtils;
    private GeneNamesProvider geneNamesProvider;
    private DesignElementMappingProvider designElementMappingProvider;


    @Inject
    public DataWriterFactory(TsvReaderUtils tsvReaderUtils, GeneNamesProvider geneNamesProvider, DesignElementMappingProvider designElementMappingProvider) {
        this.tsvReaderUtils = tsvReaderUtils;
        this.geneNamesProvider = geneNamesProvider;
        this.designElementMappingProvider = designElementMappingProvider;
    }


    public ExpressionsWriter getRnaSeqAnalyticsDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {

        final AnalyticsDataHeaderBuilder headerBuilder = new AnalyticsDataHeaderBuilder();
        headerBuilder.setExperiment(experiment);

        ExpressionsWriterImpl expressionsWriter = new ExpressionsWriterImpl(tsvReaderUtils, geneNamesProvider);
        expressionsWriter.setFileUrlTemplate(differentialExperimentAnalyticsFileUrlTemplate);
        initWriter(expressionsWriter, experiment.getAccession(), responseWriter, headerBuilder);

        return expressionsWriter;
    }

    public ExpressionsWriter getRnaSeqRawDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {

        RnaSeqRawDataHeaderBuilder headerBuilder = new RnaSeqRawDataHeaderBuilder();

        ExpressionsWriterImpl expressionsWriter = new ExpressionsWriterImpl(tsvReaderUtils, geneNamesProvider);
        expressionsWriter.setFileUrlTemplate(differentialExperimentRawCountsFileUrlTemplate);

        initWriter(expressionsWriter, experiment.getAccession(), responseWriter, headerBuilder);

        return expressionsWriter;
    }


    public ExpressionsWriter getMicroarrayAnalyticsDataWriter(DifferentialExperiment experiment, String arrayDesignAccession, PrintWriter responseWriter) {
        MicroarrayAnalyticsDataHeaderBuilder headerBuilder = new MicroarrayAnalyticsDataHeaderBuilder();
        headerBuilder.setExperiment(experiment);

        MicroarrayDataWriter microarrayDataWriter = new MicroarrayDataWriter(tsvReaderUtils, geneNamesProvider, designElementMappingProvider);
        microarrayDataWriter.setArrayDesignAccession(arrayDesignAccession);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentAnalyticsFileUrlTemplate);

        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter, headerBuilder);

        return microarrayDataWriter;

    }

    public ExpressionsWriter getMicroarrayRawDataWriter(DifferentialExperiment experiment, String arrayDesignAccession, PrintWriter responseWriter) {
        MicroarrayNormalizedDataHeaderBuilder headerBuilder = new MicroarrayNormalizedDataHeaderBuilder();

        MicroarrayDataWriter microarrayDataWriter = new MicroarrayDataWriter(tsvReaderUtils, geneNamesProvider, designElementMappingProvider);
        microarrayDataWriter.setArrayDesignAccession(arrayDesignAccession);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentNormalizedFileUrlTemplate);

        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter, headerBuilder);

        return microarrayDataWriter;
    }

    public ExpressionsWriter getMicroarrayLogFoldDataWriter(DifferentialExperiment experiment, String arrayDesignAccession, PrintWriter responseWriter) {
        MicroarrayNormalizedDataHeaderBuilder headerBuilder = new MicroarrayNormalizedDataHeaderBuilder();

        MicroarrayDataWriter microarrayDataWriter = new MicroarrayDataWriter(tsvReaderUtils, geneNamesProvider, designElementMappingProvider);
        microarrayDataWriter.setArrayDesignAccession(arrayDesignAccession);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentLogFoldFileUrlTemplate);

        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter, headerBuilder);

        return microarrayDataWriter;
    }


    private void initWriter(ExpressionsWriterImpl expressionsWriter, String experimentAccession,
                            PrintWriter responseWriter, HeaderBuilder analyticsDataHeaderBuilder) {

        expressionsWriter.setHeaderBuilder(analyticsDataHeaderBuilder);
        expressionsWriter.setResponseWriter(responseWriter);
        expressionsWriter.setExperimentAccession(experimentAccession);
    }


}
