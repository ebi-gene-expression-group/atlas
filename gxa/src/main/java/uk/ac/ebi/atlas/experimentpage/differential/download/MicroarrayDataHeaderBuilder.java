package uk.ac.ebi.atlas.experimentpage.differential.download;

import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

public class MicroarrayDataHeaderBuilder extends AnalyticsDataHeaderBuilder {
    public MicroarrayDataHeaderBuilder(DifferentialExperiment experiment) {
        super(experiment);
    }

    @Override
    protected int getFixedColumnNumber() {
        return 3;
    }
}
