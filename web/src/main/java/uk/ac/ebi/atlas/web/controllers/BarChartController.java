package uk.ac.ebi.atlas.web.controllers;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.barcharts.BarChartTrader;
import uk.ac.ebi.atlas.model.caches.BarChartTradersCache;

import javax.inject.Inject;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

@Controller
@Scope("request")
public class BarChartController {

    private BarChartTradersCache barChartTradersCache;

    @Inject
    public BarChartController(BarChartTradersCache barChartTradersCache) {
        this.barChartTradersCache = barChartTradersCache;
    }

    @RequestMapping(value = "/json/barchart/{experimentAccession}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap(@PathVariable String experimentAccession,
                         @RequestParam(required = false) Set<String> organismParts) {


        BarChartTrader barchartTrader = barChartTradersCache.getBarchartTrader(experimentAccession);

        NavigableMap<Double,Integer> chartData = barchartTrader.getChart(organismParts);

        Gson gson = new Gson();

        return gson.toJson(chartData, TreeMap.class);

    }

}
