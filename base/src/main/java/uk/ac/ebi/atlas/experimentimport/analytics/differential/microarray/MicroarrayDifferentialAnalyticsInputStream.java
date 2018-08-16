package uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialTsvFileParsingUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

/*
 * Reads tsv input of (NB: there may be multiple contrasts defined by the header):
 *
 * Gene ID              Gene Name   Design Element  g2_g1.p-value   g2_g1.t-statistic   g2_g1.log2foldchange
 * ENSMUSG00000041538   H2-Ob       1422201_at      0.1             0.2                 0.3
 *
 * and returns MicroarrayDifferentialAnalytics of:
 *
 * 1422201_at, g2_g1, 0.1, 0.2, 0.3
 *
 * If any of p-value, t-statistic, or log2foldchange contain NA then skip to next set
 * "inf" in any value is interpreted as Double.POSITIVE_INFINITY
 * "-inf" in any value is interpreted as Double.NEAGTIVE_INFINITY
 */
public class MicroarrayDifferentialAnalyticsInputStream implements ObjectInputStream<MicroarrayDifferentialAnalytics> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroarrayDifferentialAnalyticsInputStream.class);

    private static final int GENE_ID_INDEX = 0;
    private static final int DESIGN_ELEMENT_INDEX = 2;
    private static final int FIRST_CONTRAST_INDEX = 3;

    private final CSVReader csvReader;
    private final Queue<MicroarrayDifferentialAnalytics> queue = new LinkedList<>();
    private final ImmutableList<String> contrastIds;
    private final String name;
    private int lineNumber = 0;

    public MicroarrayDifferentialAnalyticsInputStream(Reader reader, String name) {
        this.name = name;
        this.csvReader = new CSVReader(reader, '\t');
        String[] headers = readCsvLine();
        String[] contrastHeaders = ArrayUtils.subarray(headers, FIRST_CONTRAST_INDEX, headers.length);
        this.contrastIds = DifferentialTsvFileParsingUtil.parseHeaderIntoContrastIds(contrastHeaders);
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
            throw new UncheckedIOException(
                    String.format("%s exception thrown while reading line %s", name, lineNumber), e);
        }
    }

    @Override
    public MicroarrayDifferentialAnalytics readNext() {
        if (queue.isEmpty()) {
            ImmutableList<MicroarrayDifferentialAnalytics> analytics = readNextContrasts();

            if (analytics == null) {
                //EOF
                return null;
            }

            queue.addAll(analytics);
        }

        return queue.remove();
    }

    private ImmutableList<MicroarrayDifferentialAnalytics> readNextContrasts() {
        ImmutableList<MicroarrayDifferentialAnalytics> analytics;
        do {

            String[] line = readCsvLine();
            if (line == null) {
                // EOF
                return null;
            }

            String geneId = line[GENE_ID_INDEX];
            String designElement = line[DESIGN_ELEMENT_INDEX];
            String[] contrastAnalyticsArray = ArrayUtils.subarray(line, FIRST_CONTRAST_INDEX, line.length);
            ImmutableList<String> contrastAnalyticsList =
                    ImmutableList.<String>builder().add(contrastAnalyticsArray).build();
            UnmodifiableIterator<String> contrastAnalytics = contrastAnalyticsList.iterator();

            ImmutableList.Builder<MicroarrayDifferentialAnalytics> builder = ImmutableList.builder();

            for (String contrastId : contrastIds) {

                checkState(contrastAnalytics.hasNext(), String.format(
                        "%s line %s: missing p-value for design element %s, contrast %s",
                        name, lineNumber, designElement, contrastId));
                String pValueString = contrastAnalytics.next();

                checkState(contrastAnalytics.hasNext(), String.format(
                        "%s line %s: missing t-statistic for design element %s, contrast %s",
                        name, lineNumber, designElement, contrastId));
                String tStatisticString = contrastAnalytics.next();

                checkState(contrastAnalytics.hasNext(), String.format(
                        "%s line %s: missing log2foldchange for design element %s, contrast %s",
                        name, lineNumber, designElement, contrastId));
                String foldChangeString = contrastAnalytics.next();

                if (!("NA".equalsIgnoreCase(pValueString) ||
                      "NA".equalsIgnoreCase(tStatisticString) ||
                      "NA".equalsIgnoreCase(foldChangeString))) {
                    double pValue = DifferentialTsvFileParsingUtil.parseDouble(pValueString);
                    double tStatistic = DifferentialTsvFileParsingUtil.parseDouble(tStatisticString);
                    double foldChange = DifferentialTsvFileParsingUtil.parseDouble(foldChangeString);
                    if (foldChange != 0.0) {
                        MicroarrayDifferentialAnalytics dto =
                                new MicroarrayDifferentialAnalytics(
                                        geneId, designElement, contrastId, pValue, foldChange, tStatistic);
                        builder.add(dto);
                    }
                }

            }

            analytics = builder.build();

        // loop while no analytics extracted from the current line
        } while (analytics.isEmpty());

        return analytics;
    }
}
