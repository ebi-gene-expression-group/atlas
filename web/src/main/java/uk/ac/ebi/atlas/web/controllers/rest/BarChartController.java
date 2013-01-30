package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.GeneExpressionPrecondition;
import uk.ac.ebi.atlas.model.barcharts.BarChartTrader;
import uk.ac.ebi.atlas.model.caches.BarChartTradersCache;

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

    private GeneExpressionPrecondition geneExpressionPrecondition;

    @Inject
    public BarChartController(BarChartTradersCache barChartTradersCache, GeneExpressionPrecondition geneExpressionPrecondition) {
        this.barChartTradersCache = barChartTradersCache;
        this.geneExpressionPrecondition = geneExpressionPrecondition;
    }

    @RequestMapping(value = "/json/barchart/{experimentAccession}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap(HttpServletRequest request, @PathVariable String experimentAccession,
                         @RequestParam(value = "queryFactorValues[]", required = false) Set<String> queryFactorValues) {

        System.out.println("geneExpressionPrecondition.getQueryFactorType() = " + geneExpressionPrecondition.getQueryFactorType());
        System.out.println("geneExpressionPrecondition = " + geneExpressionPrecondition.getLimitingFactorValues());
        BarChartTrader barchartTrader = barChartTradersCache.getBarchartTrader(experimentAccession);

        Set<FactorValue> queryFactorValueSet = new HashSet<>();
        if (queryFactorValues != null) {
            for (String queryFactorValue : queryFactorValues) {
                queryFactorValueSet.add(new FactorValue(geneExpressionPrecondition.getQueryFactorType(), queryFactorValue));
            }
        }

        NavigableMap<Double, Integer> chartData = barchartTrader.getChart(geneExpressionPrecondition.getLimitingFactorValues()
                , queryFactorValueSet);

        Gson gson = new Gson();

        // changed to more generic Map interface as per Sonar recommendation
        return gson.toJson(chartData, Map.class);

    }

}
