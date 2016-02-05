package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.MessageFormat;

@Controller
@Scope("request")
public class ProteomicsBaselineExperimentDownloadController extends BaselineExperimentDownloadController {

    @Inject
    public ProteomicsBaselineExperimentDownloadController(BaselineRequestContextBuilder requestContextBuilder,
                                                          FilterFactorsConverter filterFactorsConverter,
                                                          ProteomicsBaselineProfilesWriter proteomicsBaselineProfilesWriter) {
        super(requestContextBuilder, filterFactorsConverter, proteomicsBaselineProfilesWriter);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = "type=PROTEOMICS_BASELINE")
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        geneProfilesHandler(request, preferences, response);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccession}-atlasExperimentSummary.Rdata", params = "type=PROTEOMICS_BASELINE")
    public String downloadRdataURL(HttpServletRequest request) throws IOException {
        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        String path = MessageFormat.format("/expdata/{0}/{0}-atlasExperimentSummary.Rdata", experiment.getAccession());

        return "forward:" + path;
    }
}
