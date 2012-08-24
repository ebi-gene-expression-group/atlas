package utils;

import uk.ac.ebi.atlas.model.ExperimentRun;

import java.util.HashMap;
import java.util.Map;

public class ExperimentRunsBuilder {

    int factorValueIndex;

    public Map<String, ExperimentRun> buildExperimentRuns(String... runAccessions) {

        Map<String, ExperimentRun> runs = new HashMap<>();

        for (String runAccession : runAccessions) {
            ExperimentRun run = buildExperimentRun(runAccession);

            runs.put(runAccession, run);
        }

        return runs;

    }

    public Map<Integer, ExperimentRun> buildIndexedExperimentRuns(String... runAccessions) {

        Map<Integer, ExperimentRun> runs = new HashMap<>();

        for (String runAccession : runAccessions) {
            ExperimentRun run = buildExperimentRun(runAccession);

            runs.put(factorValueIndex, run);
        }

        return runs;

    }


    private ExperimentRun buildExperimentRun(String runAccession) {


        ExperimentRun run = new ExperimentRun(runAccession);

        run.addFactorValue("factor1", "value1_" + factorValueIndex)
                .addFactorValue("factor2", "value2_" + factorValueIndex);

        factorValueIndex++;

        return run;
    }
}
