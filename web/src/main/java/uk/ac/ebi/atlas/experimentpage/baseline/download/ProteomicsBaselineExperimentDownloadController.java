package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class ProteomicsBaselineExperimentDownloadController extends BaselineExperimentDownloadController {

    private final String TYPE_PROTEOMICS_BASELINE = "type=PROTEOMICS_BASELINE";

    @Inject
    public ProteomicsBaselineExperimentDownloadController(ProteomicsBaselineProfileInputStreamFactory inputStreamFactory,
                                                          BaselineProfilesWriterServiceFactory
                                                                  baselineProfilesWriterServiceFactory,ExperimentTrader experimentTrader) {
        super(inputStreamFactory, baselineProfilesWriterServiceFactory, experimentTrader);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = TYPE_PROTEOMICS_BASELINE)
    public void downloadGeneProfiles(HttpServletRequest request, @PathVariable String experimentAccession
            ,@RequestParam(value = "accessKey",required = false) String accessKey

            , @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        geneProfilesHandler(experimentAccession,request, preferences, response, accessKey);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccession}-atlasExperimentSummary.Rdata", params = TYPE_PROTEOMICS_BASELINE)
    public String downloadRdataURL(@PathVariable String experimentAccession) throws IOException {
        throw new ResourceNotFoundException("");
    }
}
