
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

@Controller
@Scope("request")
public class BaselineBarChartController {

    private static final String GENE_DISTRIBUTION_URL = "/json/experiments/{experimentAccession}/genedistribution";

    public static final String geneDistributionUrl(HttpServletRequest request, String experimentAccession, String accessKey){
        return ApplicationProperties.buildServerURL(request)+GENE_DISTRIBUTION_URL.replace("{experimentAccession}", experimentAccession)+ (
                org.apache.commons.lang.StringUtils.isNotEmpty(accessKey) ? "?accessKey="+accessKey : ""
        );
    }

    private HistogramTrader histogramTrader;

    @Inject
    public BaselineBarChartController(HistogramTrader histogramTrader) {
        this.histogramTrader = histogramTrader;
    }

    @RequestMapping(value = GENE_DISTRIBUTION_URL, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getMap(@PathVariable String experimentAccession,
                         @RequestParam(required = false) String accesskey) {

        HistogramAcrossGenes barchartTrader = histogramTrader.get(experimentAccession, accesskey);

        return new Gson().toJson(barchartTrader.asJson());
    }

}
