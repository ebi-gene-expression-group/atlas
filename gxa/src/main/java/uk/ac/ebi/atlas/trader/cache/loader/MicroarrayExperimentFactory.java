package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.SortedSet;

@Named
public class MicroarrayExperimentFactory implements ExperimentFactory<MicroarrayExperiment> {

    private final ConfigurationTrader configurationTrader;
    private final SpeciesFactory speciesFactory;
    private final ArrayDesignTrader arrayDesignTrader;

    @Inject
    public MicroarrayExperimentFactory(ConfigurationTrader configurationTrader,
                                       SpeciesFactory speciesFactory,
                                       ArrayDesignTrader arrayDesignTrader) {

        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
        this.arrayDesignTrader = arrayDesignTrader;

    }

    @Override
    public MicroarrayExperiment create(ExperimentDTO experimentDTO,
                                       String experimentDescription,
                                       ExperimentDesign experimentDesign){

        String experimentAccession = experimentDTO.getExperimentAccession();

        MicroarrayExperimentConfiguration microarrayExperimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);
        Set<Contrast> contrasts = microarrayExperimentConfiguration.getContrasts();

        SortedSet<String> arrayDesignAccessions = microarrayExperimentConfiguration.getArrayDesignAccessions();

        boolean hasRData = microarrayExperimentConfiguration.hasRData();

        return new MicroarrayExperiment(experimentDTO.getExperimentType(), experimentAccession,
                experimentDTO.getLastUpdate(), contrasts, experimentDescription, hasRData,
                speciesFactory.create(experimentDTO.getSpecies()),arrayDesignAccessions,
                arrayDesignTrader.getArrayDesignNames(arrayDesignAccessions), experimentDesign,
                experimentDTO.getPubmedIds());

    }
}
