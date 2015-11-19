/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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

    private FilterFactorsConverter filterFactorsConverter;

    private BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader;

    @Inject
    public BaselineBarChartController(BarChartTradersCache barChartTradersCache, FilterFactorsConverter filterFilterFactorsConverter,
                                      BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader) {
        this.barChartTradersCache = barChartTradersCache;
        this.filterFactorsConverter = filterFilterFactorsConverter;
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

        Set<Factor> filterFactors = filterFactorsConverter.deserialize(serializedFilterFactors);

        NavigableMap<Double, Integer> chartData = barchartTrader.getChart(filterFactors, queryFactors);

        return new Gson().toJson(chartData, Map.class);
    }

}
