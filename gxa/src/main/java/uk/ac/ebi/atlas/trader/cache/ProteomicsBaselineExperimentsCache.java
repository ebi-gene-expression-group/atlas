package uk.ac.ebi.atlas.trader.cache;

import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ProteomicsBaselineExperimentsCache extends ExperimentsCache<BaselineExperiment> {


    @Inject
    public ProteomicsBaselineExperimentsCache(@Qualifier("proteomicsBaselineExperimentsLoadingCache") LoadingCache<String, BaselineExperiment> experiments) {
        super(experiments);
    }
}
