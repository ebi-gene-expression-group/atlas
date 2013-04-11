package uk.ac.ebi.atlas.commands.download;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AllDataWriterFactory {

    @Value("#{configuration['diff.experiment.raw-counts.path.template']}")
    private String differentialExperimentRawCountsFileUrlTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialExperimentAnalyticsFileUrlTemplate;


    private CsvReaderBuilder csvReaderBuilder;
    private GeneNamesProvider geneNamesProvider;
    private DesignElementMappingProvider designElementMappingProvider;


    @Inject
    public AllDataWriterFactory(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider, DesignElementMappingProvider designElementMappingProvider) {
        this.csvReaderBuilder = csvReaderBuilder;
        this.geneNamesProvider = geneNamesProvider;
        this.designElementMappingProvider = designElementMappingProvider;
    }


    public ExpressionsWriter getRnaSeqAnalyticsDataWriter(DifferentialExperiment experiment) {

        final AnalyticsDataHeaderBuilder analyticsDataHeaderBuilder = new AnalyticsDataHeaderBuilder();
        analyticsDataHeaderBuilder.setExperiment(experiment);

        ExpressionsWriterImpl expressionsWriter = new ExpressionsWriterImpl(csvReaderBuilder, geneNamesProvider);
        expressionsWriter.setFileUrlTemplate(differentialExperimentAnalyticsFileUrlTemplate);
        expressionsWriter.setHeaderBuilder(analyticsDataHeaderBuilder);

        return expressionsWriter;
    }

    public ExpressionsWriter getRnaSeqRawDataWriter() {

        RnaSeqRawDataHeaderBuilder headerBuilder = new RnaSeqRawDataHeaderBuilder();

        ExpressionsWriterImpl expressionsWriter = new ExpressionsWriterImpl(csvReaderBuilder, geneNamesProvider);
        expressionsWriter.setHeaderBuilder(headerBuilder);
        expressionsWriter.setFileUrlTemplate(differentialExperimentRawCountsFileUrlTemplate);

        return expressionsWriter;
    }


    public ExpressionsWriter getMicroarrayAnalyticsDataWriter() {
        return null;
    }

    public ExpressionsWriter getMicroarrayRawDataWriter() {
        return null;
    }
}
