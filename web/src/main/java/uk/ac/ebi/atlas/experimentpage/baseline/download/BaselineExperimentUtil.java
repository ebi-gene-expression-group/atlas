package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class BaselineExperimentUtil {

    private final ConfigurationTrader configurationTrader;

    @Inject
    public BaselineExperimentUtil(ConfigurationTrader configuration) {
        this.configurationTrader = configuration;
    }

    public boolean hasCTTV(String experimentAccession) {

        BaselineExperimentConfiguration factorsConfig = configurationTrader.getFactorsConfiguration(experimentAccession);

        return factorsConfig.isCTTVExperiment();
    }
}
