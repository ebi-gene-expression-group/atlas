
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.cache.CacheLoader;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of BarChartTraderBuilder everytime the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one using this class, is not spring managed.
public abstract class BarChartTradersCacheLoader extends CacheLoader<String, BarChartTrader> {

    @Override
    public BarChartTrader load(String experimentAccession) {

        return createBarChartTraderBuilder().forExperiment(experimentAccession).create();

    }

    public abstract BarChartTraderBuilder createBarChartTraderBuilder();

}
