package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.commons.readers.XmlReader;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ConfigurationTrader {

    private final DataFileHub dataFileHub;

    @Inject
    public ConfigurationTrader(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public BaselineExperimentConfiguration getBaselineFactorsConfiguration(String experimentAccession) {
        XmlReader xmlReader = dataFileHub.getBaselineExperimentFiles(experimentAccession).factors.get();
        return new BaselineExperimentConfiguration(xmlReader);
    }

    public ExperimentConfiguration getExperimentConfiguration(String experimentAccession) {
        return getExperimentConfiguration(experimentAccession, false);
    }

    public MicroarrayExperimentConfiguration getMicroarrayExperimentConfiguration(String experimentAccession) {
        return (MicroarrayExperimentConfiguration) getExperimentConfiguration(experimentAccession, true);
    }

    private ExperimentConfiguration getExperimentConfiguration(String experimentAccession, boolean isMicroarray) {
        if (isMicroarray) {
            return new MicroarrayExperimentConfiguration(dataFileHub.getBaselineExperimentFiles(experimentAccession).configuration.get());
        }
        return new ExperimentConfiguration(dataFileHub.getBaselineExperimentFiles(experimentAccession).configuration.get());
    }

}