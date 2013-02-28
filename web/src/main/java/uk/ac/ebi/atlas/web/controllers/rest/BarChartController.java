package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.GeneExpressionPrecondition;
import uk.ac.ebi.atlas.model.barcharts.BarChartTrader;
import uk.ac.ebi.atlas.model.caches.BarChartTradersCache;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.FactorsConverter;

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

    private FactorsConverter factorsConverter;

    @Inject
    public BarChartController(BarChartTradersCache barChartTradersCache, GeneExpressionPrecondition geneExpressionPrecondition,
                              ExperimentsCache experimentsCache, FactorsConverter factorsConverter) {
        this.barChartTradersCache = barChartTradersCache;
        this.geneExpressionPrecondition = geneExpressionPrecondition;
        this.experimentsCache = experimentsCache;
        this.factorsConverter = factorsConverter;
    }

    @RequestMapping(value = "/json/barchart/{experimentAccession}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap(HttpServletRequest request, @PathVariable String experimentAccession,
                         @RequestParam(value = "queryFactorValues[]", required = false) Set<String> queryFactorValues,
                         @RequestParam String queryFactorType, @RequestParam(required = false) String serializedFilterFactors) {

        BarChartTrader barchartTrader = barChartTradersCache.getBarchartTrader(experimentAccession);

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        if (StringUtils.isBlank(queryFactorType)) {
            queryFactorType = experiment.getDefaultQueryFactorType();
        }

        Set<Factor> queryFactors = new HashSet<>();
        if (queryFactorValues != null) {
            for (String queryFactorValue : queryFactorValues) {
                queryFactors.add(new Factor(queryFactorType, queryFactorValue));
            }
        }

        Set<Factor> filterFactors = Sets.newHashSet();

        if (StringUtils.isBlank(serializedFilterFactors)){
            filterFactors = experiment.getDefaultFilterFactors();
        } else {
            filterFactors = factorsConverter.deserialize(serializedFilterFactors);
        }
        NavigableMap<Double, Integer> chartData = barchartTrader.getChart(filterFactors, queryFactors);

        Gson gson = new Gson();

        // changed to more generic Map interface as per Sonar recommendation
        return gson.toJson(chartData, Map.class);

    }

}
