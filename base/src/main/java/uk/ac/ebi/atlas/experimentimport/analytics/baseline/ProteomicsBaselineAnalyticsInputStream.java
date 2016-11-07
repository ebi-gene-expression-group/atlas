package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.utils.StringArrayUtil;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

/*
 * Reads tsv input of:
 *
 * Gene ID Gene Name g1.SpectralCount g2.SpectralCount g3.SpectralCount g1.WithInSampleAbundance g2.WithInSampleAbundance g3.WithInSampleAbundance
 * ENSG00000000003  TSPAN6  0   7   8   5010000 0   0.0000079
 *
 * and returns BaselineAnalytics of:
 *
 * TSPAN6, g1, 5010000
 * TSPAN6, g3, 0.0000079
 *
 * NB: the following expression levels are skipped: 0, LOWDATA, FAIL, NA
 */
public class ProteomicsBaselineAnalyticsInputStream implements ObjectInputStream<BaselineAnalytics> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProteomicsBaselineAnalyticsInputStream.class);

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int FIRST_EXPRESSION_LEVEL_INDEX = 2;

    private static final String SAMPLE_ABUNDANCE_QUALIFIER = ".WithInSampleAbundance";

    private final CSVReader csvReader;
    private final Queue<BaselineAnalytics> queue = new LinkedList<>();
    private final Map<Integer, String> sampleAbundanceAssayGroupIds;
    private final String name;
    private int lineNumber = 0;

    public ProteomicsBaselineAnalyticsInputStream(CSVReader csvReader, String name) {
        this.name = name;
        this.csvReader = csvReader;
        String[] headers = readCsvLine();

        sampleAbundanceAssayGroupIds = new HashMap<>();

        String[] assayGroupIds = (String[]) ArrayUtils.subarray(headers, FIRST_EXPRESSION_LEVEL_INDEX, headers.length);

        int[] sampleAbundanceIndices = StringArrayUtil.indicesOf(assayGroupIds, SAMPLE_ABUNDANCE_QUALIFIER);
        for (int sampleAbundanceIndex : sampleAbundanceIndices) {
            sampleAbundanceAssayGroupIds.put(sampleAbundanceIndex, assayGroupIds[sampleAbundanceIndex]);
        }
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }

    private String[] readCsvLine() {
        lineNumber++;
        try {
            return csvReader.readNext();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(String.format("%s exception thrown while reading line %s", name, lineNumber), e);
        }
    }

    @Override
    public BaselineAnalytics readNext() {
        if (queue.isEmpty()) {
            ImmutableList<BaselineAnalytics> baselineAnalytics = readNextNonZeroLine();

            if (baselineAnalytics == null) {
                //EOF
                return null;
            }

            queue.addAll(baselineAnalytics);
        }

        return queue.remove();
    }

    private ImmutableList<BaselineAnalytics> readNextNonZeroLine() {

        String[] line = readCsvLine();
        if (line == null) {
            // EOF
            return null;
        }

        String geneId = line[GENE_ID_COLUMN_INDEX];
        String[] expressionLevels = (String[]) ArrayUtils.subarray(line, FIRST_EXPRESSION_LEVEL_INDEX, line.length);
        ImmutableList<BaselineAnalytics> baselineAnalytics = createList(geneId, sampleAbundanceAssayGroupIds, expressionLevels);

        if (baselineAnalytics.isEmpty()) {
            return readNextNonZeroLine();
        }

        return baselineAnalytics;
    }

    private ImmutableList<BaselineAnalytics> createList(String geneId, Map<Integer, String> sampleAbundanceAssayGroupIds, String[] expressionLevels) {
        checkArgument(StringUtils.isNotBlank(geneId), "Cannot load proteomics baseline analytics - gene id is blank");
        // TODO: @rpetry - commented the E-PROT-1-specific test below in order to test loading of a new type of proteomics experiment, E-PROT-3
        // checkArgument(sampleAbundanceAssayGroupIds.size() * 2 == expressionLevels.length,
        //              String.format("Cannot load proteomics baseline analytics - expecting [%s]->[%s] expressions but got [%s] instead.", Joiner.on(", ").join(sampleAbundanceAssayGroupIds.keySet()),  Joiner.on(", ").join(sampleAbundanceAssayGroupIds.entrySet()), Joiner.on(", ").join(expressionLevels)));

        ImmutableList.Builder<BaselineAnalytics> builder = ImmutableList.builder();

        for (int i : sampleAbundanceAssayGroupIds.keySet()) {
            String assayGroupId = sampleAbundanceAssayGroupIds.get(i);
            String expressionLevelString = expressionLevels[i];

            if (!"NA".equalsIgnoreCase(expressionLevelString)) {
                Double expressionLevel = Double.parseDouble(expressionLevels[i]);
                if (expressionLevel != 0.0) {
                    builder.add(new BaselineAnalytics(geneId, StringUtils.removeEnd(assayGroupId, SAMPLE_ABUNDANCE_QUALIFIER), expressionLevel));
                }
            }
        }

        return builder.build();
    }
}
