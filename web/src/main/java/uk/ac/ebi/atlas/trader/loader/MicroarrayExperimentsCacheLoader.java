
package uk.ac.ebi.atlas.trader.loader;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Set;
import java.util.SortedSet;

@Named
public class MicroarrayExperimentsCacheLoader extends ExperimentsCacheLoader<MicroarrayExperiment> {

    private ConfigurationTrader configurationTrader;

    private SpeciesKingdomTrader speciesKingdomTrader;

    private String logFoldChangePathTemplate;

    @Inject
    public MicroarrayExperimentsCacheLoader(ConfigurationTrader configurationTrader, SpeciesKingdomTrader speciesKingdomTrader,
                                            @Value("#{configuration['microarray.log-fold-changes.data.path.template']}") String logFoldChangePathTemplate) {
        this.configurationTrader = configurationTrader;
        this.speciesKingdomTrader = speciesKingdomTrader;
        this.logFoldChangePathTemplate = logFoldChangePathTemplate;
    }

    @Override
    protected MicroarrayExperiment load(ExperimentDTO experimentDTO, String experimentDescription,
                                        boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        MicroarrayExperimentConfiguration microarrayExperimentConfiguration = configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);
        Set<Contrast> contrasts = microarrayExperimentConfiguration.getContrasts();

        SortedSet<String> arrayDesignAccessions = microarrayExperimentConfiguration.getArrayDesignAccessions();

        boolean hasRData = microarrayExperimentConfiguration.hasRData();

        String logFoldChangeFileLocation = MessageFormat.format(logFoldChangePathTemplate, experimentAccession, arrayDesignAccessions.first());

        boolean hasLogFoldChangeFile = Files.exists(Paths.get(logFoldChangeFileLocation));

        String kingdom = speciesKingdomTrader.getKingdom(experimentDTO.getSpecies());
        String ensemblDB = speciesKingdomTrader.getEnsemblDB(experimentDTO.getSpecies());

        return new MicroarrayExperiment(experimentDTO.getExperimentType(), experimentAccession, experimentDTO.getLastUpdate(),
                                        contrasts, experimentDescription, hasExtraInfoFile, hasRData, experimentDTO.getSpecies(), kingdom, ensemblDB, arrayDesignAccessions,
                                        hasLogFoldChangeFile, experimentDTO.getPubmedIds(), experimentDesign);

    }
}
