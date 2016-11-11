package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class RnaSeqBaselineExperimentsCache extends ExperimentsCache<BaselineExperiment> {

    @Inject
    public RnaSeqBaselineExperimentsCache(@Qualifier("rnaSeqBaselineExperimentsLoadingCache") LoadingCache<String, BaselineExperiment> experiments) {
        super(experiments);
    }
}
