package uk.ac.ebi.atlas.widget;

import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.validation.Valid;

import java.text.MessageFormat;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Controller
@Scope("request")
public class JsonBaselineRefExperiment extends WidgetController {

    private SpeciesInferrer speciesInferrer;

    @Inject
    public JsonBaselineRefExperiment(SpeciesInferrer speciesInferrer) {
        this.speciesInferrer = speciesInferrer;
    }

    @RequestMapping(value = "/widgets/heatmap/referenceExperiment", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Deprecated
    public String oldReferenceExperiment() {
        return "forward:/json/baseline_refexperiment";
    }

    @RequestMapping(value = "/json/baseline_refexperiment", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String jsonBaselineRefExperiment(@RequestParam(value = "geneQuery") SemanticQuery geneQuery,
                                            @RequestParam(value = "species", required = false) String speciesString,
                                            @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences) {

        Species species = speciesInferrer.inferSpeciesForGeneQuery(geneQuery, speciesString);
        String experimentAccession = ApplicationProperties.getBaselineReferenceExperimentAccession(species);

        if (isBlank(experimentAccession)) {
            throw new ResourceNotFoundException("No baseline experiment for species " + speciesString);
        }

        return MessageFormat.format("forward:/json/experiments/{0}", experimentAccession);
    }

}
