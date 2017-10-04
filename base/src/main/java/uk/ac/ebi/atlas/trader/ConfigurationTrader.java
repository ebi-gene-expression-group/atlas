package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;

/*
Object oriented access to the configuration files.
Easier to mock out than a configuration.xml file, but consider using a temporary file instead.
 */
@Named
public class ConfigurationTrader {

    private final DataFileHub dataFileHub;

    @Inject
    public ConfigurationTrader(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public BaselineExperimentConfiguration getBaselineFactorsConfiguration(String experimentAccession) {
        return new BaselineExperimentConfiguration(dataFileHub.getBaselineExperimentFiles(experimentAccession).factors.get());
    }

    public ExperimentConfiguration getExperimentConfiguration(String experimentAccession) {
        return new ExperimentConfiguration(dataFileHub.getExperimentFiles(experimentAccession).configuration.get());
    }

    public MicroarrayExperimentConfiguration getMicroarrayExperimentConfiguration(String experimentAccession) {
        return new MicroarrayExperimentConfiguration(dataFileHub.getExperimentFiles(experimentAccession).configuration.get());
    }
}