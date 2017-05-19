package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import au.com.bytecode.opencsv.CSVReader;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.serializers.ImmutableSetKryoSerializer;
import uk.ac.ebi.atlas.commons.serializers.OntologyTermKryoSerializer;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.QuartilesArrayBuilder;
import uk.ac.ebi.atlas.model.resource.KryoFile;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class RnaSeqBaselineExpressionKryoSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqBaselineExpressionKryoSerializer.class);

    // TODO Refactor constants here and in RnaSeqBaselineAnalyticsInputStream to something like ExperimentTsvFileFormat and maybe some parsing utilities (?)
    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int GENE_NAME_COLUMN_INDEX = 1;
    private static final int FIRST_EXPRESSION_LEVEL_INDEX = 2;

    private final DataFileHub dataFileHub;

    @Inject
    public RnaSeqBaselineExpressionKryoSerializer(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }


    // This method will automatically block if the serialization is requested concurrently on the same experiment: it will wait
    // until the current serialization is finished. It can concurrently serialize different experiments, and each call needs its own
    // Kryo instance, as Kryo isn’t thread safe.
    public String serializeExpressionData(final String experimentAccession) {
        Kryo kryo = new Kryo();
        ImmutableSetKryoSerializer.registerSerializers(kryo);
        OntologyTermKryoSerializer.registerSerializers(kryo);

        KryoFile.Handle kryoFileHandle = dataFileHub.getKryoFile(experimentAccession).get();

        try (UnsafeOutput expressionsOutput = kryoFileHandle.write();
             CSVReader tsvReaderForLineCount =
                     new CSVReader(dataFileHub.getBaselineExperimentFiles(experimentAccession).main.getReader(), '\t');
             CSVReader tsvReader =
                     new CSVReader(dataFileHub.getBaselineExperimentFiles(experimentAccession).main.getReader(), '\t')) {

            // Count number of genes (lines except the header)
            int geneCount = 0;
            while (tsvReaderForLineCount.readNext() != null) {
                geneCount++;
            }
            geneCount--;
            kryo.writeObject(expressionsOutput, geneCount);

            // First line contains an array {"Gene ID", "Gene Name", "g1", "g2", "g3",  ...}
            String[] assays = tsvReader.readNext();
            assays = ArrayUtils.subarray(assays, FIRST_EXPRESSION_LEVEL_INDEX, assays.length);
            LOGGER.debug("Writing {} genes with {} assay groups each", geneCount, assays.length);

            long start = System.currentTimeMillis();

            String[] tsvLine;

            while ((tsvLine = tsvReader.readNext()) != null) {
                String geneId = tsvLine[GENE_ID_COLUMN_INDEX];
                String geneName = tsvLine[GENE_NAME_COLUMN_INDEX];
                BaselineExpression[] baselineExpressions = parseBaselineExpressions(ArrayUtils.subarray(tsvLine,
                        FIRST_EXPRESSION_LEVEL_INDEX, tsvLine.length), assays);
                // Second and subsequent lines contain two strings and an array of arrays (null is interpreted as {0, 0, 0, 0, 0}
                // e.g. "ENSG00000000003", "TSPAN6", {{0.1, 2.3, 3.4, 5.6, 7.8}, {8, 8, 8, 8, 8}, null, ...}
                kryo.writeObject(expressionsOutput, geneId);
                kryo.writeObject(expressionsOutput, geneName);
                kryo.writeObject(expressionsOutput, baselineExpressions);
            }
            LOGGER.info("Files successfully written in {} ms", System.currentTimeMillis() - start);

            kryoFileHandle.addPermissionsAfterWrite();
            return String.format("Serialized %s genes with %s assay groups each", geneCount, assays
                    .length);
        }
        catch (IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new IllegalStateException("Cannot write serialized file", exception);
        }
    }

    private BaselineExpression[] parseBaselineExpressions(String[] tsvLine, String[] assays) {
        BaselineExpression[] baselineExpressions = new BaselineExpression[tsvLine.length];

        for (int i = 0; i < tsvLine.length; i++) {
            String expressionLevelString = tsvLine[i];

            if (expressionLevelString.contains(",")) {
                double[] quartiles = QuartilesArrayBuilder.create(expressionLevelString);
                baselineExpressions[i] = new BaselineExpression(quartiles, assays[i]);
            }
            else {
                baselineExpressions[i] = new BaselineExpression(expressionLevelString, assays[i]);
            }
        }

        return baselineExpressions;
    }

}
