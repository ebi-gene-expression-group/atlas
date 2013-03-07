package uk.ac.ebi.atlas.model.cache.differential;

import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.configuration.ConfigurationTrader;
import uk.ac.ebi.atlas.commons.configuration.ContrastsConfiguration;
import uk.ac.ebi.atlas.model.cache.ExperimentLoader;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

public abstract class DifferentialExperimentLoader extends ExperimentLoader<DifferentialExperiment> {


    private ConfigurationTrader configurationTrader;

    @Inject
    public DifferentialExperimentLoader(ConfigurationTrader configurationTrader){
        this.configurationTrader = configurationTrader;
    }


    @Override
    public DifferentialExperiment load(String accession, String experimentDescription, boolean hasExtraInfoFile) throws ParseException, IOException {

        ContrastsConfiguration contrastsConfiguration = configurationTrader.getContrastsConfiguration(accession);
        Set<Contrast> contrasts = contrastsConfiguration.getContrasts();

        return new DifferentialExperiment(accession, contrasts, experimentDescription, hasExtraInfoFile);

    }
}
