package utils;

import uk.ac.ebi.atlas.model.ExperimentRun;

import java.util.*;

public class ExperimentRunsBuilder {

    private int factorValueIndex;

    public List<ExperimentRun> buildExperimentRuns(String... runAccessions) {

        List<ExperimentRun> runs = new ArrayList<ExperimentRun>();

        for (String runAccession: runAccessions) {
            ExperimentRun run = buildExperimentRun(runAccession);

            runs.add(run);
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
