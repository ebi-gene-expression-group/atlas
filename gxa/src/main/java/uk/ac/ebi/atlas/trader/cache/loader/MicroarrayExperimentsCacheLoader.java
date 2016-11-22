package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

@Named
public class MicroarrayExperimentsCacheLoader extends ExperimentsCacheLoader<MicroarrayExperiment> {

    private ConfigurationTrader configurationTrader;

    private SpeciesFactory speciesFactory;

    private final ArrayDesignTrader arrayDesignTrader;

    @Inject
    public MicroarrayExperimentsCacheLoader(ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory, ArrayDesignTrader arrayDesignTrader) {
        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
        this.arrayDesignTrader = arrayDesignTrader;
    }

    @Override
    protected MicroarrayExperiment load(ExperimentDTO experimentDTO, String experimentDescription,
                                        ExperimentDesign experimentDesign) throws IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        MicroarrayExperimentConfiguration microarrayExperimentConfiguration = configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);
        Set<Contrast> contrasts = microarrayExperimentConfiguration.getContrasts();

        SortedSet<String> arrayDesignAccessions = microarrayExperimentConfiguration.getArrayDesignAccessions();

        boolean hasRData = microarrayExperimentConfiguration.hasRData();

        return new MicroarrayExperiment(experimentDTO.getExperimentType(), experimentAccession, experimentDTO.getLastUpdate(),
                                        contrasts, experimentDescription, hasRData,
                speciesFactory.create(experimentDTO),arrayDesignAccessions,
                arrayDesignTrader.getArrayDesignNames(arrayDesignAccessions), experimentDesign, experimentDTO.getPubmedIds());

    }
}
