package uk.ac.ebi.atlas.model.differential.cache;

import com.google.common.cache.CacheLoader;
import uk.ac.ebi.atlas.commons.configuration.ConfigurationTrader;
import uk.ac.ebi.atlas.commons.configuration.ContrastsConfiguration;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;

public class DifferentialExperimentLoader extends CacheLoader<String, DifferentialExperiment> {


    private ConfigurationTrader configurationTrader;

    @Inject
    public DifferentialExperimentLoader(ConfigurationTrader configurationTrader){
        this.configurationTrader = configurationTrader;
    }


    @Override
    public DifferentialExperiment load(String accession) throws Exception {
        ContrastsConfiguration contrastsConfiguration = configurationTrader.getContrastsConfiguration(accession);
        contrastsConfiguration.getContrasts();
  //      DifferentialExperiment differentialExperiment = new DifferentialExperiment();

        return null;
    }
}
