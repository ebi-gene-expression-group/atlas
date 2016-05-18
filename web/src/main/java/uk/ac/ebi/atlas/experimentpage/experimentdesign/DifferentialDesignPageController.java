
package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.DifferentialDesignRequestPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public class DifferentialDesignPageController extends ExperimentDesignPageRequestHandler<DifferentialExperiment> {

    private String contrastId;
    private static final String QC_ARRAY_DESIGNS_ATTRIBUTE = "qcArrayDesigns";

    @RequestMapping(method = RequestMethod.GET, value = "/experiments/{experimentAccession}/experiment-design", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String showRnaSeqExperimentDesign(@ModelAttribute("preferences") @Valid DifferentialDesignRequestPreferences preferences
            , Model model, HttpServletRequest request) throws IOException {

        contrastId = preferences.getSelectedContrast();
        return handleRequest(model, request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/experiments/{experimentAccession}/experiment-design", params = {"type=MICROARRAY_ANY"})
    public String showMicroarrayExperimentDesign(@ModelAttribute("preferences") @Valid DifferentialDesignRequestPreferences preferences
            , Model model, HttpServletRequest request) throws IOException {

        contrastId = preferences.getSelectedContrast();
        return handleRequest(model, request);
    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(DifferentialExperiment experiment) {
        return experiment.getAssayAccessions();
    }

    @Override
    protected void extendModel(Model model, DifferentialExperiment experiment, String experimentAccession) {

        model.addAttribute("contrasts", experiment.getContrasts());

        if (StringUtils.isBlank(contrastId)) {
            contrastId = experiment.getContrasts().iterator().next().getId();
        }

        Gson gson = new Gson();

        Contrast contrast = experiment.getContrast(contrastId);
        model.addAttribute("referenceAssays", gson.toJson(Sets.newHashSet(contrast.getReferenceAssayGroup())));
        model.addAttribute("testAssays", gson.toJson(Sets.newHashSet(contrast.getTestAssayGroup())));

        //For showing the QC REPORTS button in the header
        if(experiment instanceof MicroarrayExperiment){
            model.addAttribute(QC_ARRAY_DESIGNS_ATTRIBUTE, ((MicroarrayExperiment)experiment).getArrayDesignAccessions());

            SortedSet<String> arrayDesignNames = arrayDesignTrader.getArrayDesignNames(((MicroarrayExperiment)experiment).getArrayDesignAccessions());
            model.addAttribute(ALL_ARRAY_DESIGNS_ATTRIBUTE, arrayDesignNames);
        }

    }

}