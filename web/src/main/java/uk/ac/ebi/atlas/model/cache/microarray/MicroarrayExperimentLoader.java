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

package uk.ac.ebi.atlas.model.cache.microarray;

import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabSpeciesParser;
import uk.ac.ebi.atlas.commons.magetab.MageTabSpeciesParserBuilder;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.cache.ExperimentLoader;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

@Named
public class MicroarrayExperimentLoader extends ExperimentLoader<MicroarrayExperiment> {

    private MageTabSpeciesParserBuilder mageTabSpeciesParserBuilder;

    private ConfigurationTrader configurationTrader;

    @Inject
    public MicroarrayExperimentLoader(MageTabSpeciesParserBuilder mageTabSpeciesParserBuilder, ConfigurationTrader configurationTrader) {
        this.mageTabSpeciesParserBuilder = mageTabSpeciesParserBuilder;
        this.configurationTrader = configurationTrader;
    }

    @Override
    protected MicroarrayExperiment load(String accession, String experimentDescription, boolean hasExtraInfoFile) throws ParseException, IOException {

        MicroarrayExperimentConfiguration microarrayExperimentConfiguration = configurationTrader.getMicroarrayExperimentConfiguration(accession);
        Set<Contrast> contrasts = microarrayExperimentConfiguration.getContrasts();

        MageTabSpeciesParser mageTabSpeciesParser = mageTabSpeciesParserBuilder.forExperimentAccession(accession).build();
        Set<String> species = mageTabSpeciesParser.extractSpecies();

        SortedSet<String> arrayDesignNames = microarrayExperimentConfiguration.getArrayDesignNames();

        return new MicroarrayExperiment(accession, contrasts, experimentDescription, hasExtraInfoFile, species, arrayDesignNames);

    }
}
