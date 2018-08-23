package uk.ac.ebi.atlas.trader.cache.loader;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

@Component
public class DifferentialExperimentFactory implements ExperimentFactory<DifferentialExperiment> {
    private final ConfigurationTrader configurationTrader;
    private final SpeciesFactory speciesFactory;

    public DifferentialExperimentFactory(ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory) {
        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
    }

    @Override
    public DifferentialExperiment create(ExperimentDTO experimentDTO,
                                         ExperimentDesign experimentDesign,
                                         IdfParserOutput idfParserOutput) {

        String experimentAccession = experimentDTO.getExperimentAccession();

        final ExperimentConfiguration experimentConfiguration =
                configurationTrader.getExperimentConfiguration(experimentAccession);

        return new DifferentialExperiment(
                experimentAccession,
                experimentDTO.getLastUpdate(),
                experimentConfiguration.getContrastAndAnnotationPairs(),
                idfParserOutput.getTitle(),
                speciesFactory.create(experimentDTO.getSpecies()),
                experimentDTO.getPubmedIds(),
                experimentDTO.getDois(),
                experimentDesign);
    }
}
