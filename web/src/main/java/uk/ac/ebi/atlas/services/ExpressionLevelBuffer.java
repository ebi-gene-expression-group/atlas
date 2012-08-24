package uk.ac.ebi.atlas.services;

import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.FactorValue;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class ExpressionLevelBuffer {

    private static final String DATA_FILE_RECORD_VALIDATION_MESSAGE = "Data file record length is smaller then expected." +
            " It should contain transcript id and rpkm values for all experiment runs";
    private String transcriptId;
    private Queue<String> rpkmValues = new LinkedList<>();
    private Map<Integer, ExperimentRun> indexedRuns;

    private int bufferIndex;


    public ExpressionLevelBuffer(Map<Integer, ExperimentRun> indexedRuns) {
        this.indexedRuns = indexedRuns;
    }

    public ExpressionLevel poll() {
        String rpkmStringValue = rpkmValues.poll();

        if (rpkmStringValue == null) {
            return null;
        }
        double rpkmValue = Double.parseDouble(rpkmStringValue);
        Set<FactorValue> factorValues = indexedRuns.get(bufferIndex).getFactorValues();

        bufferIndex++;
        return new ExpressionLevel(transcriptId, rpkmValue, factorValues);
    }

    public ExpressionLevelBuffer reload(String... values) {

        checkArgument(values.length == indexedRuns.size() + 1, DATA_FILE_RECORD_VALIDATION_MESSAGE);

        bufferIndex = 0;

        rpkmValues.clear();

        Collections.addAll(this.rpkmValues, values);

        transcriptId = rpkmValues.poll();
        bufferIndex++;

        return this;
    }
}
