package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import au.com.bytecode.opencsv.CSVReader;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.commons.serializers.ImmutableSetKryoSerializer;
import uk.ac.ebi.atlas.commons.serializers.OntologyTermKryoSerializer;
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
public class RnaSeqBaselineExpressionKryoSerializer implements ExpressionSerializer {

    private static final Logger LOGGER = Logger.getLogger(RnaSeqBaselineExpressionKryoSerializer.class);

    // TODO Refactor constants here and in BaselineAnalyticsInputStream to something like ExperimentTsvFileFormat and maybe some parsing utilities (?)
    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int GENE_NAME_COLUMN_INDEX = 1;
    private static final int FIRST_EXPRESSION_LEVEL_INDEX = 2;

    private String serializedExpressionsFileTemplate;
    private String serializedExpressionLevelsFileTemplate;
    private String tsvFileTemplate;
    private CsvReaderFactory csvReaderFactory;

    @Inject
    public RnaSeqBaselineExpressionKryoSerializer(@Value("#{configuration['experiment.kryo_expressions.path.template']}") String serializedExpressionsFileTemplate,
                                                  @Value("#{configuration['experiment.kryo_expression_levels.path.template']}") String serializedExpressionLevelsFileTemplate,
                                                  @Value("#{configuration['experiment.magetab.path.template']}") String tsvFileTemplate,
                                                  CsvReaderFactory csvReaderFactory) {
        this.serializedExpressionsFileTemplate = serializedExpressionsFileTemplate;
        this.serializedExpressionLevelsFileTemplate = serializedExpressionLevelsFileTemplate;
        this.tsvFileTemplate = tsvFileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }


    // This method will automatically block if the serialization is requested concurrently on the same experiment: it will wait
    // until the current serialization is finished. It can concurrently serialize different experiments, and each call needs its own
    // Kryo instance, as Kryo isn’t thread safe.
    public void serializeExpressionData(final String experimentAccession, ExperimentalFactors experimentalFactors) {
        Kryo kryo = new Kryo();
        ImmutableSetKryoSerializer.registerSerializers(kryo);
        OntologyTermKryoSerializer.registerSerializers(kryo);

        String tsvFilePath = MessageFormat.format(tsvFileTemplate, experimentAccession);
        String serializedExpressionsFileURL = MessageFormat.format(serializedExpressionsFileTemplate, experimentAccession);
        String serializedExpressionLevelsFileURL = MessageFormat.format(serializedExpressionLevelsFileTemplate, experimentAccession);

        try (FileOutputStream expressionsOutputStream = new FileOutputStream(serializedExpressionsFileURL);
             UnsafeOutput expressionsOutput = new UnsafeOutput(expressionsOutputStream);
             FileOutputStream expressionLevelsOutputStream = new FileOutputStream(serializedExpressionLevelsFileURL);
             UnsafeOutput expressionLevelsOutput = new UnsafeOutput(expressionLevelsOutputStream);
             CSVReader tsvReaderForLineCount = csvReaderFactory.createTsvReader(tsvFilePath);
             CSVReader tsvReader = csvReaderFactory.createTsvReader(tsvFilePath)) {

            LOGGER.debug("Parsing " + tsvFilePath);
            LOGGER.debug("Writing full baseline expressions to " + serializedExpressionsFileURL);
            LOGGER.debug("Writing expression levels to " + serializedExpressionLevelsFileURL);

            // Count number of genes (lines except the header)
            int geneCount = 0;
            while (tsvReaderForLineCount.readNext() != null) {
                geneCount++;
            }
            geneCount--;
            kryo.writeObject(expressionsOutput, geneCount);
            kryo.writeObject(expressionLevelsOutput, geneCount);

            // First line contains an array {"Gene ID", "Gene Name", "g1", "g2", "g3",  ...}
            String[] assays = tsvReader.readNext();
            assays = (String[]) ArrayUtils.subarray(assays, FIRST_EXPRESSION_LEVEL_INDEX, assays.length);
            kryo.writeObject(expressionsOutput, assays);
            kryo.writeObject(expressionLevelsOutput, assays);

            FactorGroup[] factorGroups = new FactorGroup[assays.length];
            for (int i = 0 ; i < assays.length ; i++) {
                factorGroups[i] = experimentalFactors.getFactorGroup(assays[i]);
            }
            kryo.writeObject(expressionsOutput, factorGroups);
            kryo.writeObject(expressionLevelsOutput, factorGroups);

            LOGGER.debug("Writing " + geneCount + " genes with " + assays.length + " assay groups each");

            long start = System.currentTimeMillis();

            String[] tsvLine;

            while ((tsvLine = tsvReader.readNext()) != null) {
                String geneId = tsvLine[GENE_ID_COLUMN_INDEX];
                String geneName = tsvLine[GENE_NAME_COLUMN_INDEX];
                BaselineExpression[] baselineExpressions = parseBaselineExpressions((String[]) ArrayUtils.subarray(tsvLine, FIRST_EXPRESSION_LEVEL_INDEX, tsvLine.length));
                // Second and subsequent lines contain two strings and an array of arrays (null is interpreted as {0, 0, 0, 0, 0}
                // e.g. "ENSG00000000003", "TSPAN6", {{0.1, 2.3, 3.4, 5.6, 7.8}, {8, 8, 8, 8, 8}, null, ...}
                kryo.writeObject(expressionsOutput, geneId);
                kryo.writeObject(expressionsOutput, geneName);
                kryo.writeObject(expressionsOutput, baselineExpressions);

                double[] expressionLevels = new double[assays.length];
                for (int i = 0 ; i < assays.length ; i++) {
                    expressionLevels[i] = baselineExpressions[i].getLevel();
                }
                kryo.writeObject(expressionLevelsOutput, geneId);
                kryo.writeObject(expressionLevelsOutput, geneName);
                kryo.writeObject(expressionLevelsOutput, expressionLevels);
            }
            LOGGER.info("Files successfully written in " + NumberFormat.getInstance().format((System.currentTimeMillis() - start)) + " ms");

        }
        catch (IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new IllegalStateException("Cannot write serialized file", exception);
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
