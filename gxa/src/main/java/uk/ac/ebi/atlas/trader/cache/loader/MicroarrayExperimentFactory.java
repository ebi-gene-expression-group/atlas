package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.stream.Collectors;

@Named
public class MicroarrayExperimentFactory implements ExperimentFactory<MicroarrayExperiment> {

    private final ConfigurationTrader configurationTrader;
    private final SpeciesFactory speciesFactory;
    private final ArrayDesignDAO arrayDesignDAO;

    @Inject
    public MicroarrayExperimentFactory(ConfigurationTrader configurationTrader,
                                       SpeciesFactory speciesFactory,
                                       ArrayDesignDAO arrayDesignDAO) {

        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
        this.arrayDesignDAO = arrayDesignDAO;

    }

    @Override
    public MicroarrayExperiment create(ExperimentDTO experimentDTO,
                                       String experimentDescription,
                                       ExperimentDesign experimentDesign){

        String experimentAccession = experimentDTO.getExperimentAccession();

        MicroarrayExperimentConfiguration experimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);

        return new MicroarrayExperiment(
                experimentDTO.getExperimentType(),
                experimentAccession,
                experimentDTO.getLastUpdate(),
                experimentConfiguration.getContrastAndAnnotationPairs(),
                experimentDescription,
                speciesFactory.create(experimentDTO.getSpecies()),
                experimentDesign,
                experimentDTO.getPubmedIds(),
                experimentConfiguration
                        .getArrayDesignAccessions()
                        .stream()
                        .map(a -> arrayDesignDAO.getArrayDesign(a))
                        .collect(Collectors.toList())
        );

    }

}
