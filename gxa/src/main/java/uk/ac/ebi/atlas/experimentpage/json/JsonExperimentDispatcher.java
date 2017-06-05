package uk.ac.ebi.atlas.experimentpage.json;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static uk.ac.ebi.atlas.experimentpage.ExperimentDispatcherUtils.alreadyForwardedButNoOtherControllerHandledTheRequest;
import static uk.ac.ebi.atlas.experimentpage.ExperimentDispatcherUtils.buildForwardURL;

@Controller
@Scope("request")
public class JsonExperimentDispatcher extends JsonExperimentController {

    @Inject
    public JsonExperimentDispatcher(ExperimentTrader experimentTrader) {
        super(experimentTrader);
    }

    @RequestMapping(value = {"/json/experiments/{experimentAccession}", "/json/experiments/{experimentAccession}/*"})
    public String dispatchData(HttpServletRequest request,
                               @PathVariable String experimentAccession,
                               @RequestParam(defaultValue = "") String accessKey) {
        if (alreadyForwardedButNoOtherControllerHandledTheRequest(request)) {
            // prevent an infinite loop
            // throw new ExperimentDispatcher.NoExperimentSubResourceException();
            throw new RuntimeException();
        }

        return "forward:" + buildForwardURL(request, experimentTrader.getExperiment(experimentAccession, accessKey));
    }
}
