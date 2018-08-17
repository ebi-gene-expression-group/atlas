package uk.ac.ebi.atlas.experimentpage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static uk.ac.ebi.atlas.experimentpage.ExperimentDispatcherUtils.alreadyForwardedButNoOtherControllerHandledTheRequest;
import static uk.ac.ebi.atlas.experimentpage.ExperimentDispatcherUtils.buildForwardURL;

@Controller
public class ExperimentDownloadController {

    private final ExperimentTrader experimentTrader;
    private final ExperimentDownloadSupplier.Proteomics proteomicsExperimentDownloadSupplier;
    private final ExperimentDownloadSupplier.RnaSeqBaseline rnaSeqBaselineExperimentDownloadSupplier;
    private final ExperimentDownloadSupplier.RnaSeqDifferential rnaSeqDifferentialExperimentDownloadSupplier;
    private final ExperimentDownloadSupplier.Microarray microarrayExperimentDownloadSupplier;

    @Inject
    public ExperimentDownloadController(ExperimentTrader experimentTrader,
                                        ExperimentDownloadSupplier.Proteomics
                                                proteomicsExperimentDownloadSupplier,
                                        ExperimentDownloadSupplier.RnaSeqBaseline
                                                    rnaSeqBaselineExperimentDownloadSupplier,
                                        ExperimentDownloadSupplier.RnaSeqDifferential
                                                    rnaSeqDifferentialExperimentDownloadSupplier,
                                        ExperimentDownloadSupplier.Microarray
                                                    microarrayExperimentDownloadSupplier) {
        this.experimentTrader = experimentTrader;
        this.proteomicsExperimentDownloadSupplier = proteomicsExperimentDownloadSupplier;
        this.rnaSeqBaselineExperimentDownloadSupplier = rnaSeqBaselineExperimentDownloadSupplier;
        this.rnaSeqDifferentialExperimentDownloadSupplier = rnaSeqDifferentialExperimentDownloadSupplier;
        this.microarrayExperimentDownloadSupplier = microarrayExperimentDownloadSupplier;
    }

    /*
    Wojtek : I think I wanted this to go somewhere - get rid of dispatching or so - rather than needed {experimentType}
    in the parameter I ended up needing the dispatcher anyway - so that different ExperimentPageRequestPreferences get
    wired in.
    We're not trying to keep these URLs stable and people shouldn't bookmark them. So, you could just remove the
    parameter, and also the dispatcher because the links will have the parameter `type` - see ExperimentPageService.
    Be nice and set up redirects here if you do, with a note telling the future us to remove it after a while.
     */
    public static final String DOWNLOAD_URL_TEMPLATE =
            "experiments-content/{experimentAccession}/download/{experimentType}";

    @RequestMapping(value = DOWNLOAD_URL_TEMPLATE)
    public String dispatch(HttpServletRequest request,
                           @PathVariable String experimentAccession,
                           @RequestParam(defaultValue = "") String accessKey) {
        if (alreadyForwardedButNoOtherControllerHandledTheRequest(request)) {
            // prevent an infinite loop
            throw new NoExperimentSubResourceException();
        }

        return "forward:" + buildForwardURL(request, experimentTrader.getExperiment(experimentAccession, accessKey));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class NoExperimentSubResourceException extends RuntimeException {
    }

    @RequestMapping(value = DOWNLOAD_URL_TEMPLATE, params = "type=PROTEOMICS_BASELINE")
    public void
    proteomicsExperimentDownload(
            @PathVariable String experimentAccession,
            @RequestParam(value = "accessKey", required = false) String accessKey,
            @ModelAttribute("preferences") @Valid ProteomicsBaselineRequestPreferences preferences,
            HttpServletResponse response) throws IOException {
        BaselineExperiment experiment =
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        proteomicsExperimentDownloadSupplier.write(response, preferences, experiment, "tsv");
    }

    @RequestMapping(value = DOWNLOAD_URL_TEMPLATE, params = "type=RNASEQ_MRNA_BASELINE")
    public void
    rnaSeqBaselineExperimentDownload(
            @PathVariable String experimentAccession,
            @RequestParam(value = "accessKey", required = false) String accessKey,
            @ModelAttribute("preferences") @Valid RnaSeqBaselineRequestPreferences preferences,
            HttpServletResponse response) throws IOException {
        BaselineExperiment experiment =
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        rnaSeqBaselineExperimentDownloadSupplier.write(response, preferences, experiment, "tsv");
    }


    @RequestMapping(value = DOWNLOAD_URL_TEMPLATE, params = "type=RNASEQ_MRNA_DIFFERENTIAL")
    public void
    rnaSeqDifferentialExperimentDownload(
            @PathVariable String experimentAccession,
            @RequestParam(value = "accessKey", required = false) String accessKey,
            @ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
            HttpServletResponse response) throws IOException {
        DifferentialExperiment experiment =
                (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        rnaSeqDifferentialExperimentDownloadSupplier.write(response, preferences, experiment, "tsv");

    }

    @RequestMapping(value = DOWNLOAD_URL_TEMPLATE, params = "type=MICROARRAY_ANY")
    public void
    microarrayExperimentDownload(
            @PathVariable String experimentAccession,
            @RequestParam(value = "accessKey", required = false) String accessKey,
            @ModelAttribute("preferences")
            @Valid MicroarrayRequestPreferences preferences,
            HttpServletResponse response) {
        MicroarrayExperiment experiment =
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        microarrayExperimentDownloadSupplier.write(response, preferences, experiment, "tsv");
    }
}
