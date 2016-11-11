package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class RnaSeqDiffExperimentsCache extends ExperimentsCache<DifferentialExperiment> {

    @Inject
    public RnaSeqDiffExperimentsCache(@Qualifier("differentialExperimentsLoadingCache") LoadingCache<String, DifferentialExperiment> experiments) {
        super(experiments);
    }
}
