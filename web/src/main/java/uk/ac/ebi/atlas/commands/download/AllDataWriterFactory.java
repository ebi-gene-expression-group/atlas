//package uk.ac.ebi.atlas.commands.download;
//
//import org.springframework.beans.factory.annotation.Value;
//import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
//import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
//import uk.ac.ebi.atlas.utils.CsvReaderBuilder;
//
//import javax.inject.Inject;
//
//public class AllDataWriterFactory {
//
//    @Value("#{configuration['diff.experiment.raw-counts.path.template']}")
//    private String differentialExperimentRawCountsFileUrlTemplate;
//
//    @Value("#{configuration['diff.experiment.data.path.template']}")
//    private String differentialExperimentAnalyticsFileUrlTemplate;
//
//
//    private CsvReaderBuilder csvReaderBuilder;
//    private GeneNamesProvider geneNamesProvider;
//
//    private AnalyticsDataHeaderBuilder analyticsDataHeaderBuilder;
//
//    @Inject
//    public AllDataWriterFactory(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider) {
//        this.csvReaderBuilder = csvReaderBuilder;
//        this.geneNamesProvider = geneNamesProvider;
//    }
//
//    @Inject
//    public void setAnalyticsDataHeaderBuilder(AnalyticsDataHeaderBuilder analyticsDataHeaderBuilder) {
//        this.analyticsDataHeaderBuilder = analyticsDataHeaderBuilder;
//    }
//
//    public ExpressionsWriter getMicroarrayAnalyticsDataWriter() {
//        return null;
//    }
//
//    public ExpressionsWriter getRnaSeqAnalyticsDataWriter(DifferentialExperiment experiment) {
//
//        final AnalyticsDataHeaderBuilder analyticsDataHeaderBuilder = new AnalyticsDataHeaderBuilder();
//        analyticsDataHeaderBuilder.setExperiment(experiment);
//
//        ExpressionsWriter expressionsWriter = new ExpressionsWriter(csvReaderBuilder, geneNamesProvider) {
//            @Override
//            protected String[] buildHeader(String[] header) {
//                return analyticsDataHeaderBuilder.buildHeader(header);
//            }
//        };
//
//        expressionsWriter.setFileUrlTemplate(differentialExperimentAnalyticsFileUrlTemplate);
//        return expressionsWriter;
//    }
//
//    public ExpressionsWriter getRnaSeqRawDataWriter() {
//
//        return null;
//    }
//
//    public ExpressionsWriter getMicroarrayRawDataWriter() {
//        return null;
//    }
//}
