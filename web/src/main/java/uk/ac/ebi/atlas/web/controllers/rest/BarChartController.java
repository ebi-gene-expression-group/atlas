package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.GeneExpressionPrecondition;
import uk.ac.ebi.atlas.model.barcharts.BarChartTrader;
import uk.ac.ebi.atlas.model.caches.BarChartTradersCache;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.FilterFactorsConverterBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

@Controller
@Scope("request")
public class BarChartController {

    private BarChartTradersCache barChartTradersCache;

    private ExperimentsCache experimentsCache;

    private GeneExpressionPrecondition geneExpressionPrecondition;

    private FilterFactorsConverterBuilder filterFilterFactorsConverterBuilder;

    @Inject
    public BarChartController(BarChartTradersCache barChartTradersCache, FilterFactorsConverterBuilder filterFilterFactorsConverterBuilder) {
        this.barChartTradersCache = barChartTradersCache;
        this.filterFilterFactorsConverterBuilder = filterFilterFactorsConverterBuilder;
    }

    @RequestMapping(value = "/json/barchart/{experimentAccession}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap(HttpServletRequest request, @PathVariable String experimentAccession,
                         @RequestParam(value = "queryFactorValues[]", required = false) Set<String> queryFactorValues,
                         @RequestParam String queryFactorType, @RequestParam(required = false) String serializedFilterFactors) {

        BarChartTrader barchartTrader = barChartTradersCache.getBarchartTrader(experimentAccession);

        Set<Factor> queryFactors = new HashSet<>();
        if (queryFactorValues != null) {
            for (String queryFactorValue : queryFactorValues) {
                queryFactors.add(new Factor(queryFactorType, queryFactorValue));
            }
        }

        FilterFactorsConverter filterFactorsConverter = filterFilterFactorsConverterBuilder.forExperimentAccession(experimentAccession).build();

        Set<Factor> filterFactors = filterFactorsConverter.deserialize(serializedFilterFactors);

        NavigableMap<Double, Integer> chartData = barchartTrader.getChart(filterFactors, queryFactors);

        Gson gson = new Gson();

        // changed to more generic Map interface as per Sonar recommendation
        return gson.toJson(chartData, Map.class);

    }

}
