package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.CacheLoader;
import uk.ac.ebi.atlas.model.barcharts.BarChartTrader;
import uk.ac.ebi.atlas.model.barcharts.BarChartTraderBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named("barChartTraderLoader")
public class BarChartTraderLoader extends CacheLoader<String, BarChartTrader> {

    private BarChartTraderBuilderFactory barChartTraderBuilderFactory;

    @Inject
    public BarChartTraderLoader(BarChartTraderBuilderFactory barChartTraderBuilderFactory) {
        this.barChartTraderBuilderFactory = barChartTraderBuilderFactory;
    }

    @Override
    public BarChartTrader load(String experimentAccession) {

        return barChartTraderBuilderFactory.with(experimentAccession).create();

    }

}
