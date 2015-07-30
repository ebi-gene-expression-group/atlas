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

import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;

@Named
public class DifferentialExperimentsCacheLoader extends ExperimentsCacheLoader<DifferentialExperiment> {

    private ConfigurationTrader configurationTrader;

    private SpeciesKingdomTrader speciesKingdomTrader;

    @Inject
    public DifferentialExperimentsCacheLoader(ConfigurationTrader configurationTrader, SpeciesKingdomTrader speciesKingdomTrader) {
        this.configurationTrader = configurationTrader;
        this.speciesKingdomTrader = speciesKingdomTrader;
    }

    @Override
    protected DifferentialExperiment load(ExperimentDTO experimentDTO, String experimentDescription,
                                          boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        ExperimentConfiguration experimentConfiguration = configurationTrader.getExperimentConfiguration(experimentAccession);
        Set<Contrast> contrasts = experimentConfiguration.getContrasts();

        String kingdom = speciesKingdomTrader.getKingdom(experimentDTO.getSpecies());

        return new DifferentialExperiment(experimentAccession, experimentDTO.getLastUpdate(), contrasts,
                experimentDescription, hasExtraInfoFile, experimentDTO.getSpecies(), kingdom, experimentDTO.getPubmedIds(), experimentDesign);

    }
}
