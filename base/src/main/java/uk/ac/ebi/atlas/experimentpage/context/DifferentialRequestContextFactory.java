package uk.ac.ebi.atlas.experimentpage.context;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

public abstract class DifferentialRequestContextFactory<E extends DifferentialExperiment,
                                                        K extends DifferentialRequestPreferences,
                                                        R extends DifferentialRequestContext<E, K>> {

    //abstract factory pattern - you can't generically use a constructor :(
    //future Java 8 using programmer, do refactor with Supplier<R>
    //or change API of DifferentialExperimentPageService.populateModelWithHeatmapData
    public abstract R create(E experiment, K preferences);

    public static class RnaSeq extends DifferentialRequestContextFactory<DifferentialExperiment,
                                                                         DifferentialRequestPreferences,
                                                                         RnaSeqRequestContext> {
        @Override
        public RnaSeqRequestContext create(DifferentialExperiment experiment, DifferentialRequestPreferences preferences) {
            return new RnaSeqRequestContext(preferences, experiment);
        }
    }

    public static class Microarray extends DifferentialRequestContextFactory<MicroarrayExperiment,
                                                                             MicroarrayRequestPreferences,
                                                                             MicroarrayRequestContext> {
        @Override
        public MicroarrayRequestContext create(MicroarrayExperiment experiment, MicroarrayRequestPreferences preferences) {
            return new MicroarrayRequestContext(preferences,experiment);
        }
    }

}
