package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalytics;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by barrera on 12/05/2017.
 *
 * Reads SingleCellBaseline data from TSV file
 *
 */
public class SingleCellBaselineInputStream implements ObjectInputStream<SingleCellBaseline>{

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCellBaselineInputStream.class);

    private final CSVReader csvReader;
    private final String name;
    private int lineNumber = 0;
    private final Queue<SingleCellBaseline> queue = new LinkedList<>();
    private final ImmutableList<String> cellsIds;


    public SingleCellBaselineInputStream(Reader reader, String name) throws IOException {
        this.name = name;
        this.csvReader = new CSVReader(reader, '\t');
        String[] headers = readCsvLine();
        String[] cellsHeaders = ArrayUtils.subarray(headers, 1, headers.length);

        ImmutableList.Builder<String> builder = ImmutableList.builder();
        this.cellsIds = builder.add(cellsHeaders).build();
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
    public SingleCellBaseline readNext() {
        if (queue.isEmpty()) {
            ImmutableList<SingleCellBaseline> singleCellBaselines = readNextExpressionsLevels();

            if (singleCellBaselines == null) {
                //EOF
                return null;
            }

            queue.addAll(singleCellBaselines);
        }

        return queue.remove();

    }

    private ImmutableList<SingleCellBaseline> readNextExpressionsLevels() {
        ImmutableList<SingleCellBaseline> singleCellBaselines;

        do {
            String[] line = readCsvLine();
            if (line == null) {
                // EOF
                return null;
            }

            String geneId = line[0];
            String[] expressionLevelsArray = ArrayUtils.subarray(line, 1, line.length);
            ImmutableList<String> expressionLevelsList = ImmutableList.<String>builder().add(expressionLevelsArray).build();
            UnmodifiableIterator<String> expressionLevels = expressionLevelsList.iterator();

            ImmutableList.Builder<SingleCellBaseline> builder = ImmutableList.builder();

            for (String cellId: cellsIds) {
                checkState(expressionLevels.hasNext(), String.format("%s line %s: missing expression level for gene %s, cell %s",
                        name, lineNumber, geneId, cellId));
                String expressionLevelString = expressionLevels.next();
                double expressionLevel = Double.parseDouble(expressionLevelString);

                SingleCellBaseline scb = new SingleCellBaseline(geneId, cellId, expressionLevel);

                builder.add(scb);
            }

            singleCellBaselines = builder.build();

        } while (singleCellBaselines.isEmpty());

        return singleCellBaselines;
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }

}
