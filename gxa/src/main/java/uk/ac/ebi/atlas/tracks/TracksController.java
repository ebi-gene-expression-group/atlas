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
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Controller
public class TracksController extends HtmlExceptionHandlingController {

    private static final MessageFormat ATLAS_URL_TEMPLATE = new MessageFormat("{0}://{1}{2}");

    private static final MessageFormat BASELINE_PROTEOMICS_TRACK_URL_PART_TEMPLATE =
            new MessageFormat("/Location/View?g={0}");

    private static final MessageFormat BASELINE_RNASEQ_TRACK_URL_PART_TEMPLATE =
            new MessageFormat(
                "/Location/View" +
                "?g={0};" +
                "contigviewbottom=url:{1}/experiments-content/{2}/tracks/{2}.{3}.genes.expressions.bedGraph;" +
                "format=BEDGRAPH");

    private static final MessageFormat DIFFERENTIAL_TRACK_URL_PART_TEMPLATE =
            new MessageFormat(
                "/Location/View" +
                "?g={0};" +
                "contigviewbottom=" +
                        "url:{1}/experiments-content/{2}/tracks/{2}.{3}.genes.log2foldchange.bedGraph=tiling," +
                        "url:{1}/experiments-content/{2}/tracks/{2}.{3}.genes.pval.bedGraph=pvalue;" +
                "format=BEDGRAPH");


    private final ExperimentTrader experimentTrader;

    @Inject
    public TracksController(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments-content/{experimentAccession}/tracks/{trackFileName:.*}",
                    method = {RequestMethod.GET})
    public String forwardToQcResource(@PathVariable String experimentAccession,@PathVariable String trackFileName)
    throws IOException {
        String path = String.format("/expdata/%s/%s", experimentAccession, trackFileName);
        return "forward:" + path;
    }


    @RequestMapping(value = "/external-services/genome-browser/{genomeBrowserName}",
                    method = {RequestMethod.GET, RequestMethod.HEAD})

    public String redirectToGenomeBrowser(@PathVariable String genomeBrowserName,
                                          @RequestParam String experimentAccession,
                                          @RequestParam String geneId,
                                          @RequestParam(required = false, defaultValue = "") String trackId,
                                          @RequestParam(required = false, defaultValue = "") String accessKey,
                                          HttpServletRequest request)
            throws IOException {

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        String genomeBrowserUrl = null;
        ImmutableCollection<ImmutableMap<String, String>> genomeBrowsers = experiment.getGenomeBrowsers();

        for (ImmutableMap<String, String> genomeBrowser : genomeBrowsers) {
            if (genomeBrowserName.equalsIgnoreCase(genomeBrowser.get("name").replaceAll("\\s+", ""))) {
                genomeBrowserUrl = genomeBrowser.get("url");
                break;
            }
        }
        if (isBlank(genomeBrowserUrl)) {
            throw new RuntimeException(MessageFormat.format(
                    "The requested genome browser {0} is not compatible with experiment {1} ({2})",
                    genomeBrowserName, experiment.getAccession(), experiment.getSpecies().getName()));
        }

        String atlasServer =
                ATLAS_URL_TEMPLATE.format(
                        new Object[]{request.getScheme(), request.getServerName(), request.getContextPath()});

        MessageFormat template =
                experiment.getType().isProteomicsBaseline() ? BASELINE_PROTEOMICS_TRACK_URL_PART_TEMPLATE :
                experiment.getType().isBaseline() ? BASELINE_RNASEQ_TRACK_URL_PART_TEMPLATE :
                        DIFFERENTIAL_TRACK_URL_PART_TEMPLATE;

        return "redirect:" + genomeBrowserUrl +
                template.format(new Object[]{geneId, atlasServer, experimentAccession, trackId});

    }

}
