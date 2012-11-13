package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.model.Experiment;

import javax.inject.Inject;

@Configuration
public class CacheConfiguration {

    private static final int EXPERIMENTS_CACHE_MAX_SIZE = 10;

    @Bean
    @Inject
    public LoadingCache<String, Experiment> experimentRunsCache(MageTabInvestigationLoader cacheLoader){

        return CacheBuilder.newBuilder().maximumSize(EXPERIMENTS_CACHE_MAX_SIZE)
                                        .build(cacheLoader);

    }


}