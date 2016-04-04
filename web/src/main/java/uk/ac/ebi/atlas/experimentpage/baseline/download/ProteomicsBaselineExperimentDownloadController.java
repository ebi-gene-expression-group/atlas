package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.baseline.PreferencesForBaselineExperiments;
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
    public ProteomicsBaselineExperimentDownloadController(PreferencesForBaselineExperiments preferencesForBaselineExperiments,
                                                          ProteomicsBaselineProfilesWriter proteomicsBaselineProfilesWriter) {
        super(preferencesForBaselineExperiments, proteomicsBaselineProfilesWriter);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = TYPE_PROTEOMICS_BASELINE)
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        geneProfilesHandler(request, preferences, response);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccession}-atlasExperimentSummary.Rdata", params = TYPE_PROTEOMICS_BASELINE)
    public String downloadRdataURL(HttpServletRequest request) throws IOException {
        throw new ResourceNotFoundException("");
    }
}
