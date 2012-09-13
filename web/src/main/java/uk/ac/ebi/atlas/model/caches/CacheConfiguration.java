package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.model.ExperimentRun;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class CacheConfiguration {

    private static final int EXPERIMENTS_CACHE_MAX_SIZE = 10;

    @Bean
    @Inject
    public LoadingCache<String, List<ExperimentRun>> experimentRunsCache(MageTabInvestigationLoader cacheLoader){

        return CacheBuilder.newBuilder().maximumSize(EXPERIMENTS_CACHE_MAX_SIZE)
                                        .build(cacheLoader);

    }


}