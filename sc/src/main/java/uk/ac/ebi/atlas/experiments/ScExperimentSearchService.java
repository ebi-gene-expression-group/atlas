package uk.ac.ebi.atlas.experiments;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

@Component
public class ScExperimentSearchService extends ExperimentSearchService {
    public ScExperimentSearchService(ScxaExperimentTrader experimentTrader) {
        super(experimentTrader);
    }
}
