package uk.ac.ebi.atlas.experimentloader.analytics.differential.microarray;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentloader.analytics.differential.DifferentialTsvFileParsingUtil;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/*
 * Reads tsv input of:
 *
 * Gene ID	            Gene Name   Design Element  g2_g1.p-value   g2_g1.t-statistic   g2_g1.log2foldchange    g2_g3.p-value   g2_g3.t-statistic   g2_g3.log2foldchange
 * ENSMUSG00000041538   H2-Ob       1422201_at      0.1             0.2                 0.3                     0.7             0.8                 0.9
 *
 * and returns MicroarrayDifferentialExpressionDtos of:
 *
 * 1422201_at, g2_g1, 0.1, 0.2, 0.3
 * 1422201_at, g2_g4, 0.7, 0.8, 0.9
 *
 * If any of p-value, t-statistic, or log2foldchange contain NA then skip to next set
 * "inf" in any value is interpreted as Double.POSITIVE_INFINITY
 * "-inf" in any value is interpreted as Double.NEAGTIVE_INFINITY
 */
public class MicroarrayDifferentialExpressionDtoInputStream implements ObjectInputStream<MicroarrayDifferentialExpressionDto> {

    private static final Logger LOGGER = Logger.getLogger(MicroarrayDifferentialExpressionDtoInputStream.class);

    private static final int DESIGN_ELEMENT_INDEX = 2;
    private static final int FIRST_CONTRAST_INDEX = 3;

    private final CSVReader csvReader;
    private final Queue<MicroarrayDifferentialExpressionDto> queue = new LinkedList<>();
    private final ImmutableList<String> contrastIds;

    public MicroarrayDifferentialExpressionDtoInputStream(CSVReader csvReader) {
        this.csvReader = csvReader;
        String[] headers = readCsvLine();
        String[] contrastHeaders = (String[]) ArrayUtils.subarray(headers, FIRST_CONTRAST_INDEX, headers.length);
        this.contrastIds = DifferentialTsvFileParsingUtil.parseHeaderIntoContrastIds(contrastHeaders);
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }

    private String[] readCsvLine() {
        try {
            return csvReader.readNext();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception thrown while reading next csv line.", e);
        }
    }

    @Override
    public MicroarrayDifferentialExpressionDto readNext() {
        if (queue.isEmpty()) {
            ImmutableList<MicroarrayDifferentialExpressionDto> dtos = readNextContrasts();

            if (dtos == null) {
                //EOF
                return null;
            }

            queue.addAll(dtos);
        }

        return queue.remove();
    }

    private ImmutableList<MicroarrayDifferentialExpressionDto> readNextContrasts() {
        String[] line = readCsvLine();
        if (line == null) {
            // EOF
            return null;
        }

        String designElement = line[DESIGN_ELEMENT_INDEX];
        String[] contrastAnalyticsArray = (String[]) ArrayUtils.subarray(line, FIRST_CONTRAST_INDEX, line.length);
        ImmutableList<String> contrastAnalyticsList = ImmutableList.<String>builder().add(contrastAnalyticsArray).build();
        UnmodifiableIterator<String> contrastAnalytics = contrastAnalyticsList.iterator();

        ImmutableList.Builder<MicroarrayDifferentialExpressionDto> builder = ImmutableList.builder();

        for (String contrastId : contrastIds) {

            checkState(contrastAnalytics.hasNext(), String.format("missing p-value for design element %s, contrast %s", designElement,contrastId));
            String pValueString = contrastAnalytics.next();

            checkState(contrastAnalytics.hasNext(), String.format("missing t-statistic for design element %s, contrast %s", designElement,contrastId));
            String tStatisticString = contrastAnalytics.next();

            checkState(contrastAnalytics.hasNext(), String.format("missing log2foldchange for design element %s, contrast %s", designElement,contrastId));
            String foldChangeString = contrastAnalytics.next();

            if (!("NA".equalsIgnoreCase(pValueString) || "NA".equalsIgnoreCase(tStatisticString) || "NA".equalsIgnoreCase(foldChangeString))) {
                double pValue = DifferentialTsvFileParsingUtil.parseDouble(pValueString);
                double tStatistic = DifferentialTsvFileParsingUtil.parseDouble(tStatisticString);
                double foldChange = DifferentialTsvFileParsingUtil.parseDouble(foldChangeString);
                MicroarrayDifferentialExpressionDto dto = new MicroarrayDifferentialExpressionDto(designElement, contrastId, pValue, foldChange, tStatistic);
                builder.add(dto);
            }

        }

        ImmutableList<MicroarrayDifferentialExpressionDto> dtos = builder.build();

        if (dtos.isEmpty()) {
            // read next line if no dtos extracted from this line
            return readNextContrasts();
        }

        return dtos;
    }

}
