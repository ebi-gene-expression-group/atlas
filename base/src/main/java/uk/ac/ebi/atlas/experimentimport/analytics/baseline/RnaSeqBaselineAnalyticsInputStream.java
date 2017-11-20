package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;

/*
 * Reads tsv input of:
 *
 * Gene ID Gene Name g1 g2 g3 g4 g5
 * mus1    musName    1  2  3  0  5
 *
 * and returns BaselineAnalytics of:
 *
 * mus1, g1, 1
 * mus1, g2, 2
 * mus1, g3, 3
 * mus1, g5, 5
 *
 * NB: the following expression levels are skipped: 0, LOWDATA, FAIL, NA
 */
public class RnaSeqBaselineAnalyticsInputStream implements ObjectInputStream<BaselineAnalytics> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqBaselineAnalyticsInputStream.class);

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int FIRST_EXPRESSION_LEVEL_INDEX = 2;

    private final CSVReader csvReader;
    private final Queue<BaselineAnalytics> queue = new LinkedList<>();
    private final String[] assayGroupIds;
    private final String name;
    private int lineNumber = 0;

    public RnaSeqBaselineAnalyticsInputStream(Reader reader, String name) throws IOException {
        this.name = name;
        this.csvReader = new CSVReader(reader, '\t');
        String[] headers = readCsvLine();
        this.assayGroupIds = ArrayUtils.subarray(headers, FIRST_EXPRESSION_LEVEL_INDEX, headers.length);
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
        String[] expressionLevels = ArrayUtils.subarray(line, FIRST_EXPRESSION_LEVEL_INDEX, line.length);
        ImmutableList<BaselineAnalytics> baselineAnalytics = createList(geneId, assayGroupIds, expressionLevels);

        if (baselineAnalytics.isEmpty()) {
            return readNextNonZeroLine();
        }

        return baselineAnalytics;
    }

    private ImmutableList<BaselineAnalytics> createList(String geneId, String[] assayGroupIds, String[] expressionLevels) {
        checkArgument(StringUtils.isNotBlank(geneId), "Cannot load baseline analytics - gene id is blank");
        checkArgument(assayGroupIds.length ==
                expressionLevels.length, String.format(
                        "Cannot load baseline analytics - expecting [%s] expressions but got [%s] instead.",
                        Joiner.on(", ").join(assayGroupIds), Joiner.on(", ").join(expressionLevels)));

        ImmutableList.Builder<BaselineAnalytics> builder = ImmutableList.builder();

        for (int i = 0; i < expressionLevels.length; i++) {

            BaselineExpression baselineExpression = BaselineExpression.create(expressionLevels[i]);
            builder.add(new BaselineAnalytics(
                    geneId,
                    assayGroupIds[i],
                    baselineExpression.getLevel()
            ));
        }

        return builder.build();
    }

}
