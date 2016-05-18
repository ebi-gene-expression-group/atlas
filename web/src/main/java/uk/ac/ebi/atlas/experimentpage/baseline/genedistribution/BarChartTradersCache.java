
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named("barChartTraders")
@Scope("singleton")
public class BarChartTradersCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarChartTradersCache.class);

    private LoadingCache<String, BarChartTrader> barchartTraders;

    @Inject
    // this is the name of the implementation being injected, required because LoadingCache is an interface
    public BarChartTradersCache(@Qualifier("barChartTradersLoadingCache") LoadingCache<String, BarChartTrader> barchartTraders) {
        this.barchartTraders = barchartTraders;
    }

    public BarChartTrader getBarchartTrader(String experimentAccession) {
        try {
            return barchartTraders.get(experimentAccession);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading histogram data from file: " + e.getMessage(), e.getCause());
        }
    }

}
