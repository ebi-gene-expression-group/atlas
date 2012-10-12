package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentRun;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Named("experimentsCache")
@Scope("singleton")
public class ExperimentsCache{

    private static final Logger logger = Logger.getLogger(ExperimentsCache.class);

    private LoadingCache<String, List<ExperimentRun>> experiments;

    @Inject
    public ExperimentsCache(LoadingCache<String, List<ExperimentRun>> experiments){
        this.experiments = experiments;
    }

    public List<ExperimentRun> getExperimentRuns(String experimentAccession){
        try{

            return experiments.get(experimentAccession);

        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading MAGE TAB file: " + e.getMessage(), e.getCause());
        }
    }

}
