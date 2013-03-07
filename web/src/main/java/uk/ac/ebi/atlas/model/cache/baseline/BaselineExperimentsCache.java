package uk.ac.ebi.atlas.model.cache.baseline;

import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
@Scope("singleton")
public class BaselineExperimentsCache {

    private static final Logger logger = Logger.getLogger(BaselineExperimentsCache.class);

    private LoadingCache<String, BaselineExperiment> experiments;

    @Inject
    @Named("experimentsLoadingCache")//this is the name of the implementation being injected, required because LoadingCache is an interface
    public BaselineExperimentsCache(LoadingCache<String, BaselineExperiment> experiments) {
        this.experiments = experiments;
    }

    public BaselineExperiment getExperiment(String experimentAccession) {
        try {

            return experiments.get(experimentAccession);

        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading MAGE TAB file: " + e.getMessage(), e.getCause());
        }
    }

}
