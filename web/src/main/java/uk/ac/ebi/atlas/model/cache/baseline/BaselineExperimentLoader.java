/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model.cache.baseline;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.model.cache.ExperimentLoader;
import uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParser;
import uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParserBuilder;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of ExperimentBuilder every time the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one only client of this class, is not spring managed.
public abstract class BaselineExperimentLoader extends ExperimentLoader<BaselineExperiment> {

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int HEADER_LINE_INDEX = 0;

    private MageTabParserBuilder mageTabParserBuilder;

    private ConfigurationTrader configurationTrader;
    private TsvReader experimentDataTsvReader;


    @Inject
    protected BaselineExperimentLoader(TsvReaderBuilder tsvReaderBuilder,
                                    MageTabParserBuilder mageTabParserBuilder,
                                    ConfigurationTrader configurationTrader,
                                    @Value("#{configuration['experiment.magetab.path.template']}")
                                    String pathTemplate) {

        this.mageTabParserBuilder = mageTabParserBuilder;
        this.configurationTrader = configurationTrader;

        this.experimentDataTsvReader = tsvReaderBuilder.forTsvFilePathTemplate(pathTemplate).build();
    }

    @Override
    protected BaselineExperiment load(String experimentAccession, String experimentDescription, boolean hasExtraInfoFile) throws ParseException, IOException {

        BaselineExperimentConfiguration factorsConfig = configurationTrader.getFactorsConfiguration(experimentAccession);

        String[] experimentRunHeaders = experimentDataTsvReader.readLine(experimentAccession, HEADER_LINE_INDEX);

        String[] columnHeaders = ArrayUtils.remove(experimentRunHeaders, GENE_ID_COLUMN_INDEX);

        Set<String> processedRunAccessions = extractProcessedRunAccessions(columnHeaders);

        BaselineExperimentBuilder experimentBuilder = createExperimentBuilder();

        String defaultQueryFactorType = factorsConfig.getDefaultQueryFactorType();
        Set<Factor> defaultFilterFactors = factorsConfig.getDefaultFilterFactors();
        Map<String, String> speciesMapping = factorsConfig.getSpeciesMapping();

        Set<String> requiredFactorTypes = getRequiredFactorTypes(defaultQueryFactorType, defaultFilterFactors);

        MageTabParser mageTabParser = mageTabParserBuilder
                .forExperimentAccession(experimentAccession)
                .withRequiredFactorTypes(requiredFactorTypes)
                .withProcessedRunAccessions(processedRunAccessions)
                .build();

        Map<String, ExperimentRun> processedExperimentRuns = mageTabParser.getProcessedExperimentRuns();

        List<FactorGroup> orderedFactorGroups = extractOrderedFactorGroups(columnHeaders, processedExperimentRuns);

        return experimentBuilder.forSpecies(getSpecies(mageTabParser))
                .withAccession(experimentAccession)
                .withDescription(experimentDescription)
                .withDefaultQueryType(factorsConfig.getDefaultQueryFactorType())
                .withDefaultFilterFactors(defaultFilterFactors)
                .withMenuFilterFactorTypes(factorsConfig.getMenuFilterFactorTypes())
                .withExperimentRuns(processedExperimentRuns)
                .withOrderedFactorGroups(orderedFactorGroups)
                .withExtraInfo(hasExtraInfoFile)
                .withFactorNamesByType(mageTabParser.getFactorNamesByType())
                .withDisplayName(factorsConfig.getExperimentDisplayName())
                .withSpeciesMapping(speciesMapping)
                .create();

    }

    private Set<String> getSpecies(MageTabParser mageTabParser) {

        return mageTabParser.extractSpecies();
    }

    private Set<String> getRequiredFactorTypes(String defaultQueryFactorType, Set<Factor> defaultFilterFactors) {
        Set<String> requiredFactorTypes = Sets.newHashSet(defaultQueryFactorType);

        for (Factor defaultFilterFactor : defaultFilterFactors) {
            requiredFactorTypes.add(defaultFilterFactor.getType());
        }
        return requiredFactorTypes;
    }

    Set<String> extractProcessedRunAccessions(String[] columnHeaders) {

        Set<String> processedRunAccessions = Sets.newHashSet();

        for (String columnHeader : columnHeaders) {
            String[] columnRuns = columnHeader.split(",");

            for (String columnRun : columnRuns) {
                columnRun = columnRun.trim();

                processedRunAccessions.add(columnRun);

            }
        }
        return processedRunAccessions;
    }

    List<FactorGroup> extractOrderedFactorGroups(String[] columnHeaders, final Map<String, ExperimentRun> experimentRuns) {

        List<FactorGroup> factorGroups = Lists.newArrayList();

        for (String columnHeader : columnHeaders){
            String firstRunAccessionOfTheGroup = StringUtils.substringBefore(columnHeader, ",").trim();
            ExperimentRun firstExperimentRunOfTheGroup = experimentRuns.get(firstRunAccessionOfTheGroup);
            factorGroups.add(firstExperimentRunOfTheGroup.getFactorGroup());
        }
        return factorGroups;

    }

    protected abstract BaselineExperimentBuilder createExperimentBuilder();

}
