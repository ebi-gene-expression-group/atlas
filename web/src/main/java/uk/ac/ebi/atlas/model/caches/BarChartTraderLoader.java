package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.CacheLoader;
import uk.ac.ebi.atlas.model.barcharts.BarChartTrader;
import uk.ac.ebi.atlas.model.barcharts.BitIndexBuilder;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of BitIndexBuilder everytime the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one using this class, is not spring managed.
public abstract class BarChartTraderLoader extends CacheLoader<String, BarChartTrader> {

    @Override
    public BarChartTrader load(String experimentAccession) {

        return createBitIndexBuilder().forExperiment(experimentAccession).create();

    }

    public abstract BitIndexBuilder createBitIndexBuilder();

}
