package uk.ac.ebi.atlas.tracks;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
public class GenomeBrowserController extends HtmlExceptionHandlingController {
    // Remember: contextPath always starts with "/"
    private static final String ATLAS_URL_TEMPLATE = "%s://%s%s";

    private static final MessageFormat BASELINE_RNASEQ_TRACK_URL_TEMPLATE =
            new MessageFormat("{0}/experiments-content/{1}/tracks/{1}.{2}.genes.expressions.bedGraph");
    private static final MessageFormat DIFFERENTIAL_LOG2_FOLD_CHANGE_TRACK_URL_TEMPLATE =
            new MessageFormat("{0}/experiments-content/{1}/tracks/{1}.{2}.genes.log2foldchange.bedGraph");
    private static final MessageFormat DIFFERENTIAL_PVALUE_TRACK_URL_TEMPLATE =
            new MessageFormat("{0}/experiments-content/{1}/tracks/{1}.{2}.genes.pval.bedGraph");

    private static final String BASELINE_PROTEOMICS_TRACK_URL_PART_TEMPLATE = "/Location/View?g=%s";

    private static final String BASELINE_RNASEQ_TRACK_URL_PART_TEMPLATE =
            "/Location/View?g=%s;contigviewbottom=url:%s;format=BEDGRAPH";

    private static final String DIFFERENTIAL_TRACK_URL_PART_TEMPLATE =
            "/Location/View?g=%s;contigviewbottom=url:%s=tiling,url:%s=pvalue;format=BEDGRAPH";

    private final ExperimentTrader experimentTrader;

    private static final String REDIRECT_URL_TEMPLATE = "experiments/{experimentAccession}/redirect/genome-browsers";

    public static String redirectUrl(String experimentAccession, String accessKey){
        return REDIRECT_URL_TEMPLATE
                .replace("{experimentAccession}", experimentAccession)
                + (isNotEmpty(accessKey) ? "?accessKey="+accessKey : "");
    }

    @Inject
    public GenomeBrowserController(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }


    //Deprecated - remove after deploying experiment page with expression-atlas-heatmap-highcharts 3.3
    @RequestMapping(value = "/external-services/genome-browser/{genomeBrowserName}", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String redirectToGenomeBrowserPreviousUrl(@PathVariable String genomeBrowserName,
                                                     @RequestParam String experimentAccession,
                                                     @RequestParam String geneId,
                                                     @RequestParam(required = false, defaultValue = "") String trackId,
                                                     @RequestParam(required = false, defaultValue = "") String accessKey,
                                                     HttpServletRequest request) throws IOException {
        return redirectToGenomeBrowser(genomeBrowserName, experimentAccession, geneId, trackId, accessKey, request);
    }


    @RequestMapping(value = REDIRECT_URL_TEMPLATE,
                    method = {RequestMethod.GET, RequestMethod.HEAD})
    public String redirectToGenomeBrowser(@RequestParam String name,
                                          @RequestParam String experimentAccession,
                                          @RequestParam String geneId,
                                          @RequestParam(required = false, defaultValue = "") String trackId,
                                          @RequestParam(required = false, defaultValue = "") String accessKey,
                                          HttpServletRequest request)
            throws IOException {

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        String genomeBrowserUrl = null;
        ImmutableCollection<ImmutableMap<String, String>> genomeBrowsers = experiment.getGenomeBrowsers();

        for (ImmutableMap<String, String> genomeBrowserO : genomeBrowsers) {
            if (name.equalsIgnoreCase(genomeBrowserO.get("name").replaceAll("\\s+", ""))) {
                genomeBrowserUrl = genomeBrowserO.get("url");
                break;
            }
        }
        if (isBlank(genomeBrowserUrl)) {
            throw new RuntimeException(String.format(
                    "The requested genome browser %s is not compatible with experiment %s (%s)",
                    name, experiment.getAccession(), experiment.getSpecies().getName()));
        }

        String atlasServer = String.format(
                ATLAS_URL_TEMPLATE, request.getScheme(), request.getServerName(), request.getContextPath());

        if (experiment.getType().isProteomicsBaseline()) {

            return "redirect:" + genomeBrowserUrl + String.format(BASELINE_PROTEOMICS_TRACK_URL_PART_TEMPLATE, geneId);

        } else if (experiment.getType().isRnaSeqBaseline()) {

            String trackUrl =
                    BASELINE_RNASEQ_TRACK_URL_TEMPLATE.format(new Object[]{atlasServer, experimentAccession, trackId})
                    + (isBlank(accessKey) ? "" : "?accessKey=" + accessKey);
            return "redirect:" + genomeBrowserUrl +
                    String.format(BASELINE_RNASEQ_TRACK_URL_PART_TEMPLATE, geneId, trackUrl);

        } else { // if (experiment.getType().isDifferential()) {

            String trackUrl1 =
                    DIFFERENTIAL_LOG2_FOLD_CHANGE_TRACK_URL_TEMPLATE.format(
                            new Object[]{atlasServer, experimentAccession, trackId})
                    + (isBlank(accessKey) ? "" : "?accessKey=" + accessKey);
            String trackUrl2 =
                    DIFFERENTIAL_PVALUE_TRACK_URL_TEMPLATE.format(
                            new Object[]{atlasServer, experimentAccession, trackId})
                    + (isBlank(accessKey) ? "" : "?accessKey=" + accessKey);

            return "redirect:" + genomeBrowserUrl +
                    String.format(DIFFERENTIAL_TRACK_URL_PART_TEMPLATE, geneId, trackUrl1, trackUrl2);

        }
    }

}
