package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.cache.CacheLoader;

import javax.annotation.ParametersAreNonnullByDefault;

// Be aware that this is a Spring-managed singleton object and uses the lookup-method injection to get a new instance
// of BarChartTraderBuilder every time the load method is invoked
// The reason to do so is that Guava CacheBuilder, that is the one using this class, is not Spring-managed.
public abstract class BarChartTradersCacheLoader extends CacheLoader<String, BarChartTrader> {

    @Override
    @ParametersAreNonnullByDefault
    public BarChartTrader load(String experimentAccession) {
        return createBarChartTraderBuilder().forExperiment(experimentAccession).create();
    }

    public abstract BarChartTraderBuilder createBarChartTraderBuilder();

}
