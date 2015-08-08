/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.trader.loader;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
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

        String logFoldChangeFileLocation = MessageFormat.format(logFoldChangePathTemplate, experimentAccession, arrayDesignAccessions.first());

        boolean hasLogFoldChangeFile = Files.exists(Paths.get(logFoldChangeFileLocation));

        String kingdom = speciesKingdomTrader.getKingdom(experimentDTO.getSpecies());
        String ensemblDB = speciesKingdomTrader.getEnsemblDB(experimentDTO.getSpecies());

        return new MicroarrayExperiment(experimentDTO.getExperimentType(), experimentAccession, experimentDTO.getLastUpdate(),
                                        contrasts, experimentDescription, hasExtraInfoFile, experimentDTO.getSpecies(), kingdom, ensemblDB, arrayDesignAccessions,
                                        hasLogFoldChangeFile, experimentDTO.getPubmedIds(), experimentDesign);

    }
}
