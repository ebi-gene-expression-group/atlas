
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

@Controller
@Scope("request")
public class BaselineBarChartController {

    private BarChartTradersCache barChartTradersCache;

    private BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader;

    @Inject
    public BaselineBarChartController(BarChartTradersCache barChartTradersCache,
                                      BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader) {
        this.barChartTradersCache = barChartTradersCache;
        this.barChartExperimentAccessKeyTrader = barChartExperimentAccessKeyTrader;
    }

    @RequestMapping(value = "/json/barchart/{experimentAccession}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap(@PathVariable String experimentAccession,
                         @RequestParam(value = "queryFactorValues[]", required = false) Set<String> queryFactorValues,
                         @RequestParam String queryFactorType, @RequestParam(required = false) String serializedFilterFactors,
                         @RequestParam(required = false) String accesskey) {

        Map<String, String> experimentAccessKeyMap = Maps.newHashMap();
        experimentAccessKeyMap.put(experimentAccession, accesskey);
        barChartExperimentAccessKeyTrader.setExperimentAccessKeyMap(experimentAccessKeyMap);

        BarChartTrader barchartTrader = barChartTradersCache.getBarchartTrader(experimentAccession);

        Set<Factor> queryFactors = new HashSet<>();
        if (queryFactorValues != null) {
            for (String queryFactorValue : queryFactorValues) {
                queryFactors.add(new Factor(queryFactorType, queryFactorValue));
            }
        }

        Set<Factor> filterFactors = FilterFactorsConverter.deserialize(serializedFilterFactors);

        NavigableMap<Double, Integer> chartData = barchartTrader.getChart(filterFactors, queryFactors);

        return new Gson().toJson(chartData, Map.class);
    }

}
