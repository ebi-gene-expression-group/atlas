package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.model.experiment.ExperimentType.*;

@Controller
@Scope("request")
public class ExperimentsListController {

    private Gson gson = new Gson();
    private ExperimentInfoListService experimentInfoListService;

    @Inject
    public ExperimentsListController(ScxaExperimentTrader scxaExperimentTrader) {
        this.experimentInfoListService =
                new ExperimentInfoListService(scxaExperimentTrader, ImmutableList.of(
                        SINGLE_CELL_RNASEQ_MRNA_BASELINE));
    }

    //Used by experiments table page
    @RequestMapping(value = "/json/experiments",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getExperimentsList() {
        return gson.toJson(experimentInfoListService.getExperimentsJson());
    }

}
