package uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.utils.ArraySortByIndices;
import uk.ac.ebi.atlas.utils.BufferedCSVReader;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/*
 * Reads tsv input of:
 *
 * Gene ID	Gene Name	Transcript ID	g1	g2	g3	g4	g5
 * ENSMODG00000013804	ARF3	ENSMODT00000017575	0	0	0	0	5
 *
 * and returns RnaSeqBaselineTranscript for each row
 *
 * NB:
 * a value of FAIL is converted to 0
 * the values are re-ordered to match the provided orderedAssayGroupIds
 */
public class RnaSeqBaselineTranscriptReader implements Iterable<RnaSeqBaselineTranscript>, Iterator<RnaSeqBaselineTranscript>, Closeable {

    private static final Logger LOGGER = Logger.getLogger(RnaSeqBaselineTranscriptReader.class);

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int TRANSCRIPT_ID_COLUMN_INDEX = 2;
    private static final int FIRST_TRANSCRIPT_LEVEL_INDEX = 3;

    private final BufferedCSVReader csvReader;
    private final String name;
    private final int[] orderedAssayGroupIdIndices;
    private int lineNumber = 0;

    public RnaSeqBaselineTranscriptReader(CSVReader csvReader, String name, String[] orderedAssayGroupIds) {
        this.name = name;
        this.csvReader = new BufferedCSVReader(csvReader);

        String[] headers = readCsvLine();
        String[] transcriptHeaderAssayGroupIds = (String[]) ArrayUtils.subarray(headers, FIRST_TRANSCRIPT_LEVEL_INDEX, headers.length);

        orderedAssayGroupIdIndices = ArraySortByIndices.match(transcriptHeaderAssayGroupIds, orderedAssayGroupIds);
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
    public void close() throws IOException {
        csvReader.close();
    }

    @Override
    public Iterator<RnaSeqBaselineTranscript> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return csvReader.hasNext();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RnaSeqBaselineTranscript next() {
        String[] line = readCsvLine();
        if (line == null) {
            // EOF
            return null;
        }

        String geneId = line[GENE_ID_COLUMN_INDEX];
        String transcriptId = line[TRANSCRIPT_ID_COLUMN_INDEX];
        String[] expressionStrings = (String[]) ArrayUtils.subarray(line, FIRST_TRANSCRIPT_LEVEL_INDEX, line.length);
        String[] orderedStrings = ArraySortByIndices.sort(expressionStrings, orderedAssayGroupIdIndices);
        Double[] levels = parseLevels(orderedStrings);
        return RnaSeqBaselineTranscript.create(geneId, transcriptId, levels);
    }

    private static Double[] parseLevels(String[] levelStrings) {
        Double[] levels = new Double[levelStrings.length];

        for (int i = 0; i < levelStrings.length; i++) {
            String levelString = levelStrings[i];
            levels[i] = parseLevel(levelString);
        }

        return levels;
    }

    private static Double parseLevel(String levelString) {
        return "FAIL".equals(levelString) ? 0 : Double.parseDouble(levelString);
    }

}