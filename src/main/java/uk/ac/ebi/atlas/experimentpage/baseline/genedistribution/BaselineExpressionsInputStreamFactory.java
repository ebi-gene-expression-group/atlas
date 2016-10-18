package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowDeserializerBaselineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowDeserializerProteomicsBaselineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowRawDeserializerBaselineBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import uk.ac.ebi.atlas.utils.KryoReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class BaselineExpressionsInputStreamFactory {

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineExperimentDataFileUrlTemplate;

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    private String baselineExperimentSerializedDataFileUrlTemplate;

    private ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder;
    private ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder;
    private ExpressionsRowDeserializerProteomicsBaselineBuilder expressionsRowDeserializerProteomicsBaselineBuilder;
    private CsvReaderFactory csvReaderFactory;
    private KryoReaderFactory kryoReaderFactory;
    private ExperimentTrader experimentTrader;

    private BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader;

    @Inject
    public BaselineExpressionsInputStreamFactory(ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder,
                                                 ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder,
                                                 ExpressionsRowDeserializerProteomicsBaselineBuilder expressionsRowDeserializerProteomicsBaselineBuilder,
                                                 CsvReaderFactory csvReaderFactory,
                                                 KryoReaderFactory kryoReaderFactory,
                                                 ExperimentTrader experimentTrader,
                                                 BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader) {
        this.expressionsRowRawDeserializerBaselineBuilder = expressionsRowRawDeserializerBaselineBuilder;
        this.expressionsRowDeserializerBaselineBuilder = expressionsRowDeserializerBaselineBuilder;
        this.expressionsRowDeserializerProteomicsBaselineBuilder = expressionsRowDeserializerProteomicsBaselineBuilder;
        this.csvReaderFactory = csvReaderFactory;
        this.kryoReaderFactory = kryoReaderFactory;
        this.experimentTrader = experimentTrader;
        this.barChartExperimentAccessKeyTrader = barChartExperimentAccessKeyTrader;
    }

    public ObjectInputStream<BaselineExpressions> createGeneExpressionsInputStream(String experimentAccession) {

        String accessKey = barChartExperimentAccessKeyTrader.getAccessKey(experimentAccession);

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        if(experiment.getType().isProteomicsBaseline()) {
            String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
            CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
            return new BaselineExpressionsTsvInputStream(csvReader, experimentAccession, expressionsRowDeserializerProteomicsBaselineBuilder);
        }
        else {
            String serializedFileURL = MessageFormat.format(baselineExperimentSerializedDataFileUrlTemplate, experimentAccession);
            try {
                BaselineExpressionsKryoReader kryoReader = kryoReaderFactory.createBaselineExpressionsKryoReader(serializedFileURL);
                return new BaselineExpressionsKryoInputStream(kryoReader, experimentAccession, expressionsRowRawDeserializerBaselineBuilder);
            } catch (IllegalArgumentException e) {
                String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
                CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
                return new BaselineExpressionsTsvInputStream(csvReader, experimentAccession, expressionsRowDeserializerBaselineBuilder);
            }
        }
    }

}
