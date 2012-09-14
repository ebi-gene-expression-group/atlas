package utils;

import uk.ac.ebi.atlas.model.ExperimentRun;

public class ExperimentRunsBuilder {

    private ExperimentRun experimentRun;

    public static ExperimentRunsBuilder forRunAccession(String runAccession) {
        return new ExperimentRunsBuilder(runAccession);

    }

    private ExperimentRunsBuilder(String runAccession){
        this.experimentRun = new ExperimentRun(runAccession);
    }

    public ExperimentRun create() {

        experimentRun.addFactorValue("factor1", "value1")
                .addFactorValue("factor2", "value2");

        return experimentRun;
    }


}
