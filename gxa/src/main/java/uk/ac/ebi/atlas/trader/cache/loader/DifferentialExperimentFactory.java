package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialExperimentFactory implements ExperimentFactory<DifferentialExperiment> {

    private final ConfigurationTrader configurationTrader;
    private final SpeciesFactory speciesFactory;

    @Inject
    public DifferentialExperimentFactory(ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory) {
        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
    }

    @Override
    public DifferentialExperiment create(ExperimentDTO experimentDTO,
                                         String experimentDescription,
                                         ExperimentDesign experimentDesign) {

        String experimentAccession = experimentDTO.getExperimentAccession();

        ExperimentConfiguration experimentConfiguration =
                configurationTrader.getExperimentConfiguration(experimentAccession);

        return new DifferentialExperiment(experimentAccession, experimentDTO.getLastUpdate(),
                experimentConfiguration.getContrasts(), experimentDescription, experimentConfiguration.hasRData(),
                speciesFactory.create(experimentDTO.getSpecies()), experimentDTO.getPubmedIds(), experimentDesign);

    }
}
