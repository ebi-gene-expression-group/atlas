package uk.ac.ebi.atlas.experimentpage.json;

import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

public abstract class JsonExperimentController extends JsonExceptionHandlingController {

    protected final ExperimentTrader experimentTrader;

    public JsonExperimentController(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

}
