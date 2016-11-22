package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named("barChartTraders")
public class BarChartTradersCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarChartTradersCache.class);

    private LoadingCache<Pair<String,String>, BarChartTrader> barchartTraders;

    @Inject
    // this is the name of the implementation being injected, required because LoadingCache is an interface
    public BarChartTradersCache(@Qualifier("barChartTradersLoadingCache") LoadingCache<Pair<String,String>,
            BarChartTrader>
                                            barchartTraders) {
        this.barchartTraders = barchartTraders;
    }

    public BarChartTrader getBarchartTrader(String experimentAccession, String accessKey) {
        try {
            return barchartTraders.get(Pair.of(experimentAccession,accessKey));
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading histogram data from file: " + e.getMessage(), e.getCause());
        }
    }

}
