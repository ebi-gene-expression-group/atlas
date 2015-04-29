package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import au.com.bytecode.opencsv.CSVReader;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.baseline.Quartiles;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 01/04/15.
 */
@Named
public class RnaSeqBaselineExpressionSerializer implements ExpressionSerializer {

    private static final Logger LOGGER = Logger.getLogger(RnaSeqBaselineExpressionSerializer.class);

    // TODO Refactor constants here and in BaselineAnalyticsInputStream to something like ExperimentTsvFileFormat and maybe some parsing utilities (?)
    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int FIRST_EXPRESSION_LEVEL_INDEX = 2;

    private String serializedFileTemplate;
    private String tsvFileTemplate;
    private CsvReaderFactory csvReaderFactory;


    @Inject
    public RnaSeqBaselineExpressionSerializer(@Value("#{configuration['experiment.serialized_expression.path.template']}") String serializedFileTemplate,
                                              @Value("#{configuration['experiment.magetab.path.template']}") String tsvFileTemplate,
                                              CsvReaderFactory csvReaderFactory) {
        this.serializedFileTemplate = serializedFileTemplate;
        this.tsvFileTemplate = tsvFileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }

    // This method will automatically block if the serialization is requested concurrently on the same experiment: it will wait
    // until the current serialization is finished. It can concurrently serialize different experiments, and each call needs its own
    // Kryo instance, as Kryo isn’t thread safe.
    public void serializeExpressionData(final String experimentAccession) {
        Kryo kryo = new Kryo();

        String tsvFilePath = MessageFormat.format(tsvFileTemplate, experimentAccession);
        String serializedFileURL = MessageFormat.format(serializedFileTemplate, experimentAccession);

        CSVReader tsvReader = csvReaderFactory.createTsvReader(tsvFilePath);

        try (FileOutputStream fos = new FileOutputStream(serializedFileURL);
             Output output = new Output(fos)
        ) {
            LOGGER.debug("Parsing " + tsvFilePath);
            LOGGER.debug("Writing to " + serializedFileURL);

            int assayCount = tsvReader.readNext().length - FIRST_EXPRESSION_LEVEL_INDEX; // Header is consumed here

            LOGGER.debug("Processing " + assayCount + " assays");

            long start = System.currentTimeMillis();
            String[] tsvLine;
            while ((tsvLine = tsvReader.readNext()) != null) {
                String geneId = tsvLine[GENE_ID_COLUMN_INDEX];
                double[][] expressionLevels = parseExpressionLevels((String[]) ArrayUtils.subarray(tsvLine, FIRST_EXPRESSION_LEVEL_INDEX, tsvLine.length));
                kryo.writeObject(output, geneId);
                kryo.writeObject(output, expressionLevels);
            }
            LOGGER.info("File successfully written in " + NumberFormat.getInstance().format((System.currentTimeMillis() - start)) + " ms");
        }
        catch (IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new IllegalStateException("Cannot write serialised TSV file", exception);
        }
    }


    private double[][] parseExpressionLevels(String[] tsvLine) {
        double[][] expressionLevels = new double[tsvLine.length][];

        for (int i = 0; i < tsvLine.length; i++) {
            String expressionLevelString = tsvLine[i];

            if ("FAIL".equalsIgnoreCase(expressionLevelString) || "LOWDATA".equalsIgnoreCase(expressionLevelString) || "NA".equalsIgnoreCase(expressionLevelString)) {
                expressionLevels[i] = null;
            }
            else {
                if (expressionLevelString.contains(",")) {
                    Quartiles quartiles = Quartiles.createFromCsvString(expressionLevelString);
                    expressionLevels[i] = new double[] {quartiles.min(), quartiles.lower(), quartiles.median(), quartiles.upper(), quartiles.max()};
                }
                else {
                    expressionLevels[i] = new double[] {Double.parseDouble(expressionLevelString)};
                }
            }
        }

        return expressionLevels;
    }

}
