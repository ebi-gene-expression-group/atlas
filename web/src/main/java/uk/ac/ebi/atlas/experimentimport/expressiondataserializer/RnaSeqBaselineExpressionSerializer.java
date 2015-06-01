package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import au.com.bytecode.opencsv.CSVReader;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.serializers.ImmutableSetSerializer;
import uk.ac.ebi.atlas.commons.serializers.OntologyTermSerializer;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.QuartilesArrayBuilder;
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
    private static final int GENE_NAME_COLUMN_INDEX = 0;
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
    public void serializeExpressionData(final String experimentAccession, ExperimentalFactors experimentalFactors) {
        Kryo kryo = new Kryo();
        ImmutableSetSerializer.registerSerializers(kryo);
        OntologyTermSerializer.registerSerializers(kryo);

        String tsvFilePath = MessageFormat.format(tsvFileTemplate, experimentAccession);
        String serializedFileURL = MessageFormat.format(serializedFileTemplate, experimentAccession);

        try (FileOutputStream fos = new FileOutputStream(serializedFileURL);
             Output output = new Output(fos);
             CSVReader tsvReaderForLineCount = csvReaderFactory.createTsvReader(tsvFilePath);
             CSVReader tsvReader = csvReaderFactory.createTsvReader(tsvFilePath)) {

            LOGGER.debug("Parsing " + tsvFilePath);
            LOGGER.info("Writing to " + serializedFileURL);


            int geneCount = 0;
            // Count number of lines (gene expressions)
            while (tsvReaderForLineCount.readNext() != null) {
                geneCount++;
            }
            geneCount--;
            kryo.writeObject(output, geneCount);

            // First line contains an array {"Gene ID", "Gene Name", "g1", "g2", "g3",  ...}
            String[] assays = tsvReader.readNext();
            assays = (String[]) ArrayUtils.subarray(assays, FIRST_EXPRESSION_LEVEL_INDEX, assays.length);
            kryo.writeObject(output, assays);

            FactorGroup[] factorGroups = new FactorGroup[assays.length];
            for (int i = 0 ; i < assays.length ; i++) {
                factorGroups[i] = experimentalFactors.getFactorGroup(assays[i]);
            }
            kryo.writeObject(output, factorGroups);

            LOGGER.debug("Processing " + assays.length + " assays and " + geneCount + " genes");

            long start = System.currentTimeMillis();

            String[] tsvLine;
            while ((tsvLine = tsvReader.readNext()) != null) {
                String geneId = tsvLine[GENE_ID_COLUMN_INDEX];
                String geneName = tsvLine[GENE_NAME_COLUMN_INDEX];
                BaselineExpression[] baselineExpressions = parseBaselineExpressions((String[]) ArrayUtils.subarray(tsvLine, FIRST_EXPRESSION_LEVEL_INDEX, tsvLine.length));
                // Second and subsequent lines contain two strings and an array of arrays (null is interpreted as {0, 0, 0, 0, 0}
                // e.g. "ENSG00000000003", "TSPAN6", {{0.1, 2.3, 3.4, 5.6, 7.8}, {8, 8, 8, 8, 8}, null, ...}
                kryo.writeObject(output, geneId);
                kryo.writeObject(output, geneName);
                kryo.writeObject(output, baselineExpressions);
            }
            LOGGER.info("File successfully written in " + NumberFormat.getInstance().format((System.currentTimeMillis() - start)) + " ms");

        }
        catch (IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new IllegalStateException("Cannot write serialised TSV file", exception);
        }
    }


    private BaselineExpression[] parseBaselineExpressions(String[] tsvLine) {
        BaselineExpression[] baselineExpressions = new BaselineExpression[tsvLine.length];

        for (int i = 0; i < tsvLine.length; i++) {
            String expressionLevelString = tsvLine[i];

            if (expressionLevelString.contains(",")) {
                double[] quartiles = QuartilesArrayBuilder.create(expressionLevelString);
                baselineExpressions[i] = new BaselineExpression(quartiles, null);
            }
            else {
                baselineExpressions[i] = new BaselineExpression(expressionLevelString, null);
            }
        }

        return baselineExpressions;
    }

}
