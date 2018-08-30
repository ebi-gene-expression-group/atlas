package uk.ac.ebi.atlas.trader.cache.loader;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.dao.ArrayDesignDao;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.util.stream.Collectors;

@Component
public class MicroarrayExperimentFactory implements ExperimentFactory<MicroarrayExperiment> {
    private final ConfigurationTrader configurationTrader;
    private final SpeciesFactory speciesFactory;
    private final ArrayDesignDao arrayDesignDAO;

    public MicroarrayExperimentFactory(ConfigurationTrader configurationTrader,
                                       SpeciesFactory speciesFactory,
                                       ArrayDesignDao arrayDesignDAO) {

        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
        this.arrayDesignDAO = arrayDesignDAO;

    }

    @Override
    public MicroarrayExperiment create(ExperimentDTO experimentDTO,
                                       ExperimentDesign experimentDesign,
                                       IdfParserOutput idfParserOutput) {

        String experimentAccession = experimentDTO.getExperimentAccession();

        MicroarrayExperimentConfiguration experimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);

        return new MicroarrayExperiment(
                experimentDTO.getExperimentType(),
                experimentAccession,
                experimentDTO.getLastUpdate(),
                experimentConfiguration.getContrastAndAnnotationPairs(),
                idfParserOutput.getTitle(),
                speciesFactory.create(experimentDTO.getSpecies()),
                experimentDesign,
                experimentDTO.getPubmedIds(),
                experimentDTO.getDois(),
                experimentConfiguration
                        .getArrayDesignAccessions()
                        .stream()
                        .map(arrayDesignDAO::getArrayDesign)
                        .collect(Collectors.toList())
        );
    }
}
