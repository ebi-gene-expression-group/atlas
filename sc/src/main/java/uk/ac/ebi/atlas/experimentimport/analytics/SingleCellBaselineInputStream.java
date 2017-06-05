package uk.ac.ebi.atlas.experimentimport.analytics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

public class SingleCellBaselineInputStream implements ObjectInputStream<SingleCellBaseline>{

    private final Queue<SingleCellBaseline> queue = new LinkedList<>();
    private final ImmutableList<String> cellsIds;
    private final ObjectInputStream<String[]> lines;


    public SingleCellBaselineInputStream(ObjectInputStream<String[]> lines) throws IOException {
        String[] headers = lines.readNext();
        String[] cellsHeaders = ArrayUtils.subarray(headers, 1, headers.length);

        ImmutableList.Builder<String> builder = ImmutableList.builder();
        this.cellsIds = builder.add(cellsHeaders).build();
        this.lines = lines;
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
            String[] line = lines.readNext();
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
                checkState(expressionLevels.hasNext(), String.format("missing expression level for gene %s, cell %s", geneId, cellId));
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
        lines.close();
    }

}
