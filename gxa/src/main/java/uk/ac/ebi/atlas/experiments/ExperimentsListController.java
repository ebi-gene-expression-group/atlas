package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.PROTEOMICS_BASELINE;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_BASELINE;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;


@Controller
@Scope("request")
public class ExperimentsListController {

    private Gson gson = new Gson();
    private ExperimentInfoListService experimentInfoListService;

    @Inject
    public ExperimentsListController(ExpressionAtlasExperimentTrader expressionAtlasExperimentTrader) {
        this.experimentInfoListService =
                new ExperimentInfoListService(expressionAtlasExperimentTrader, ImmutableList.of(
                        RNASEQ_MRNA_BASELINE,
                        PROTEOMICS_BASELINE,
                        RNASEQ_MRNA_DIFFERENTIAL,
                        MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                        MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                        MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL));
    }

    //Used by experiments table page
    @RequestMapping(value = "/json/experiments",
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getExperimentsList() {
        return gson.toJson(experimentInfoListService.getExperimentsJson());
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/info",
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getExperimentInfo(@PathVariable String experimentAccession,
                                    @RequestParam(defaultValue = "") String accessKey) {
        return experimentInfoListService.getExperimentJson(experimentAccession, accessKey);
    }

}
