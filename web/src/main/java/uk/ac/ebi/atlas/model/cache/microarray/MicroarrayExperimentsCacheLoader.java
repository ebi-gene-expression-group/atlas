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

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.cache.ExperimentsCacheLoader;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Named
public class MicroarrayExperimentsCacheLoader extends ExperimentsCacheLoader<MicroarrayExperiment> {

    private ConfigurationTrader configurationTrader;

    private String logFoldChangePathTemplate;

    @Inject
    public MicroarrayExperimentsCacheLoader(ConfigurationTrader configurationTrader,
                                            @Value("#{configuration['microarray.log-fold-changes.data.path.template']}") String logFoldChangePathTemplate) {
        this.configurationTrader = configurationTrader;
        this.logFoldChangePathTemplate = logFoldChangePathTemplate;
    }

    @Override
    protected MicroarrayExperiment load(ExperimentDTO experimentDTO, String experimentDescription, boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws ParseException, IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        MicroarrayExperimentConfiguration microarrayExperimentConfiguration = configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);
        Set<Contrast> contrasts = microarrayExperimentConfiguration.getContrasts();

        Set<String> species = extractSpecies(experimentAccession);
        List<String> pubMedIds = extractPubMedIds(experimentAccession);

        SortedSet<String> arrayDesignNames = microarrayExperimentConfiguration.getArrayDesignNames();

        String logFoldChangeFileLocation = MessageFormat.format(logFoldChangePathTemplate, experimentAccession, arrayDesignNames.first());

        boolean hasLogFoldChangeFile = Files.exists(Paths.get(logFoldChangeFileLocation));

        return new MicroarrayExperiment(experimentDTO.getExperimentType(), experimentAccession, experimentDTO.getLastUpdate(), contrasts, experimentDescription, hasExtraInfoFile, species, arrayDesignNames, hasLogFoldChangeFile, pubMedIds, experimentDesign);

    }
}
