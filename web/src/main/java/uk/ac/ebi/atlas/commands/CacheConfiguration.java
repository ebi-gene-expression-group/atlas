package uk.ac.ebi.atlas.commands;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.MageTabInvestigationLoader;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class CacheConfiguration {

    private static final String EXPERIMENTS_RUN_CACHE_SPEC = "maximumSize=10";

    @Bean
    @Inject
    public LoadingCache<String, List<ExperimentRun>> experimentRunsCache(MageTabInvestigationLoader cacheLoader){

        return CacheBuilder.from(EXPERIMENTS_RUN_CACHE_SPEC).build(cacheLoader);

    }


}