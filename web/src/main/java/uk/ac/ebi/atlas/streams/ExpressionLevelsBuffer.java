package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.TranscriptExpressionLevel;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

class ExpressionLevelsBuffer {

    private static final String DATA_FILE_RECORD_VALIDATION_MESSAGE = "Data file record should contain transcript id and rpkm values for all experiment runs";
    private String transcriptId;
    private Queue<String> rpkmValuesBuffer = new LinkedList<>();

    private Iterator<ExperimentRun> runsCircularQueue;

    private int expectedNumberOfValues;

    public ExpressionLevelsBuffer(List<ExperimentRun> orderedRuns) {
        expectedNumberOfValues = orderedRuns.size();
        this.runsCircularQueue = Iterables.cycle(orderedRuns).iterator();
    }


    public TranscriptExpressionLevel poll() {
        String rpkmStringValue = rpkmValuesBuffer.poll();

        if (rpkmStringValue == null) {
            return null;
        }
        double rpkmValue = Double.parseDouble(rpkmStringValue);

        return new TranscriptExpressionLevel(transcriptId, rpkmValue, runsCircularQueue.next());
    }


    public ExpressionLevelsBuffer reload(String... values) {
        checkState(this.rpkmValuesBuffer.isEmpty(), "Reload must be invoked only when readNext returns null");

        checkArgument(values.length == expectedNumberOfValues + 1, DATA_FILE_RECORD_VALIDATION_MESSAGE);

        rpkmValuesBuffer.clear();

        Collections.addAll(this.rpkmValuesBuffer, values);

        transcriptId = rpkmValuesBuffer.poll();

        return this;
    }
}
