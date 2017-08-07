package uk.ac.ebi.atlas.experimentpage;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
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
                                        ExperimentDownloadSupplier.Proteomics proteomicsExperimentDownloadSupplier,
                                        ExperimentDownloadSupplier.RnaSeqBaseline rnaSeqBaselineExperimentDownloadSupplier,
                                        ExperimentDownloadSupplier.RnaSeqDifferential rnaSeqDifferentialExperimentDownloadSupplier,
                                        ExperimentDownloadSupplier.Microarray microarrayExperimentDownloadSupplier) {
        this.experimentTrader = experimentTrader;
        this.proteomicsExperimentDownloadSupplier = proteomicsExperimentDownloadSupplier;
        this.rnaSeqBaselineExperimentDownloadSupplier = rnaSeqBaselineExperimentDownloadSupplier;
        this.rnaSeqDifferentialExperimentDownloadSupplier = rnaSeqDifferentialExperimentDownloadSupplier;
        this.microarrayExperimentDownloadSupplier = microarrayExperimentDownloadSupplier;
    }

    public static final String url = "experiments-content/{experimentAccession}/download/{experimentType}";
    
    @RequestMapping(value = url)
    public String dispatch(HttpServletRequest request,
                           @PathVariable String experimentAccession,
                           @RequestParam(defaultValue = "") String accessKey) {
        if (alreadyForwardedButNoOtherControllerHandledTheRequest(request)) {
            // prevent an infinite loop
            throw new NoExperimentSubResourceException();
        }

        return "forward:" + buildForwardURL(request, experimentTrader.getExperiment(experimentAccession, accessKey));
    }

    public static String getUrl(String experimentAccession, String accessKey, ExperimentType experimentType, ExperimentPageRequestPreferences<?> experimentPageRequestPreferences){
        try {
            List<BasicNameValuePair> parameters = org.apache.commons.beanutils.BeanUtils.describe(experimentPageRequestPreferences).entrySet().stream().map(e -> new BasicNameValuePair(e.getKey(), e.getValue())).collect(Collectors.toList());
            if(isNotEmpty(accessKey)) {
                parameters.add(new BasicNameValuePair("accessKey", accessKey));
            }

            String query = URLEncodedUtils.format(parameters, Charset.defaultCharset());

            return URI.create(
                    url.replace("{experimentAccession}", experimentAccession).replace("{experimentType}", experimentType.getParent().name().toUpperCase())
                    +"?"+query).toString();
        } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class NoExperimentSubResourceException extends RuntimeException {}

    @RequestMapping(value = url, params = "type=PROTEOMICS_BASELINE")
    public void proteomicsExperimentDownload(@PathVariable String experimentAccession,
                                             @RequestParam(value = "accessKey", required = false) String accessKey,
                                             @ModelAttribute("preferences") @Valid ProteomicsBaselineRequestPreferences preferences,
                                             HttpServletResponse response)
            throws IOException {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        proteomicsExperimentDownloadSupplier.write(response, preferences, experiment, "tsv");
    }

    @RequestMapping(value = url, params = "type=RNASEQ_MRNA_BASELINE")
    public void rnaSeqBaselineExperimentDownload(@PathVariable String experimentAccession,
                                                 @RequestParam(value = "accessKey", required = false) String accessKey,
                                                 @ModelAttribute("preferences") @Valid RnaSeqBaselineRequestPreferences preferences,
                                                 HttpServletResponse response)
            throws IOException {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        rnaSeqBaselineExperimentDownloadSupplier.write(response, preferences, experiment, "tsv");
    }



    @RequestMapping(value = url, params = "type=RNASEQ_MRNA_DIFFERENTIAL")
    public void rnaSeqDifferentialExperimentDownload(@PathVariable String experimentAccession,
                                     @RequestParam(value = "accessKey", required = false) String accessKey,
                                     @ModelAttribute("preferences") @Valid
                                     DifferentialRequestPreferences preferences, HttpServletResponse response) throws IOException {

        DifferentialExperiment experiment = (DifferentialExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);

        rnaSeqDifferentialExperimentDownloadSupplier.write(response, preferences, experiment, "tsv");

    }

    @RequestMapping(value = url, params = "type=MICROARRAY_ANY")
    public void microarrayExperimentDownload(@PathVariable String experimentAccession,
                                             @RequestParam(value = "accessKey", required = false) String accessKey,
                                             @ModelAttribute("preferences")
                                             @Valid MicroarrayRequestPreferences preferences,
                                             HttpServletResponse response)
            throws IOException {

        MicroarrayExperiment experiment =
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession,accessKey);

        microarrayExperimentDownloadSupplier.write(response, preferences, experiment, "tsv");
    }
}
