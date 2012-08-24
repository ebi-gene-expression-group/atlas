package uk.ac.ebi.atlas.services;

import com.google.common.collect.Iterables;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.FactorValue;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class ExpressionLevelsBuffer {

    private static final String DATA_FILE_RECORD_VALIDATION_MESSAGE = "Data file record should contain transcript id and rpkm values for all experiment runs";
    private String transcriptId;
    private Queue<String> rpkmValues = new LinkedList<>();

    private Map<String, ExperimentRun> experimentRuns;
    private Iterator<String> orderedAccessionsCyclicIterator;


    public ExpressionLevelsBuffer(String[] orderedAccessions, Map<String, ExperimentRun> experimentRuns) {
        checkArgument(orderedAccessions.length == experimentRuns.size(), "Number of accession smaller than expected");

        this.experimentRuns = experimentRuns;

        this.orderedAccessionsCyclicIterator = Iterables.cycle(Arrays.asList(orderedAccessions)).iterator();
    }


    public ExpressionLevel poll() {
        String rpkmStringValue = rpkmValues.poll();

        if (rpkmStringValue == null) {
            return null;
        }
        double rpkmValue = Double.parseDouble(rpkmStringValue);

        String currentRunAccession = orderedAccessionsCyclicIterator.next();

        Set<FactorValue> factorValues = experimentRuns.get(currentRunAccession).getFactorValues();

        return new ExpressionLevel(transcriptId, rpkmValue, factorValues);
    }


    public ExpressionLevelsBuffer reload(String... values) {

        checkArgument(values.length == experimentRuns.size() + 1, DATA_FILE_RECORD_VALIDATION_MESSAGE);

        rpkmValues.clear();

        Collections.addAll(this.rpkmValues,values);

        transcriptId = rpkmValues.poll();

        return this;
    }
}
