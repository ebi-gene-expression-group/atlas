package uk.ac.ebi.atlas.experimentpage.baseline.download;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import org.springframework.context.annotation.Scope;

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

    public boolean hasFortLauderdale(String experimentAccession) {

        BaselineExperimentConfiguration factorsConfig = configurationTrader.getBaselineFactorsConfiguration(experimentAccession);

        return factorsConfig.isFortLauderdale();
    }
}
