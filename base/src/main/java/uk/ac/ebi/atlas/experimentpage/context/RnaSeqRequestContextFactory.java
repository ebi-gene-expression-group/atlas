package uk.ac.ebi.atlas.experimentpage.context;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

public class RnaSeqRequestContextFactory extends DifferentialRequestContextFactory<DifferentialExperiment,
        DifferentialRequestPreferences, RnaSeqRequestContext> {
    @Override
    public RnaSeqRequestContext create(DifferentialExperiment experiment, DifferentialRequestPreferences preferences) {
        return populateCommonThings(new RnaSeqRequestContext(), experiment, preferences);
    }
}
