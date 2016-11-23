package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MicroarrayExperimentsCache extends ExperimentsCache<MicroarrayExperiment> {

    @Inject
    public MicroarrayExperimentsCache(@Qualifier("microarrayExperimentsLoadingCache") LoadingCache<String, MicroarrayExperiment> experiments) {
        super(experiments);
    }

}
