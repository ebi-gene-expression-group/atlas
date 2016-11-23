package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqBaselineExperimentsCache extends ExperimentsCache<BaselineExperiment> {

    @Inject
    public RnaSeqBaselineExperimentsCache(@Qualifier("rnaSeqBaselineExperimentsLoadingCache") LoadingCache<String, BaselineExperiment> experiments) {
        super(experiments);
    }
}
