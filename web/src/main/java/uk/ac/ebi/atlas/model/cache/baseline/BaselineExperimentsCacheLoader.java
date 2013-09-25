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

package uk.ac.ebi.atlas.model.cache.baseline;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.model.cache.ExperimentsCacheLoader;
import uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParser;
import uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParserBuilder;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of ExperimentBuilder every time the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one only client of this class, is not spring managed.
public abstract class BaselineExperimentsCacheLoader extends ExperimentsCacheLoader<BaselineExperiment> {

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int HEADER_LINE_INDEX = 0;
    private final TsvReaderBuilder tsvReaderBuilder;

    private MageTabParserBuilder mageTabParserBuilder;

    private ConfigurationTrader configurationTrader;

    @Inject
    protected BaselineExperimentsCacheLoader(TsvReaderBuilder tsvReaderBuilder,
                                             MageTabParserBuilder mageTabParserBuilder,
                                             ConfigurationTrader configurationTrader,
                                             @Value("#{configuration['experiment.magetab.path.template']}")
                                             String experimentDataFilePathTemplate) {

        this.mageTabParserBuilder = mageTabParserBuilder;
        this.configurationTrader = configurationTrader;
        this.tsvReaderBuilder = tsvReaderBuilder.forTsvFilePathTemplate(experimentDataFilePathTemplate);
    }

    @Override
    protected BaselineExperiment load(ExperimentDTO experimentDTO, String experimentDescription, Set<String> species,
                                      List<String> pubMedIds, boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws ParseException, IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        BaselineExperimentConfiguration factorsConfig = configurationTrader.getFactorsConfiguration(experimentAccession);

        TsvReader experimentDataTsvReader = tsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        String[] experimentRunHeaders = experimentDataTsvReader.readLine(HEADER_LINE_INDEX);

        String[] columnHeaders = ArrayUtils.remove(experimentRunHeaders, GENE_ID_COLUMN_INDEX);

        AssayGroups assayGroups = configurationTrader.getExperimentConfiguration(experimentAccession).getAssayGroups();

        Set<String> processedRunAccessions = assayGroups.getAssayAccessions();

        BaselineExperimentBuilder baselineExperimentBuilder = createExperimentBuilder();

        String defaultQueryFactorType = factorsConfig.getDefaultQueryFactorType();
        Set<Factor> defaultFilterFactors = factorsConfig.getDefaultFilterFactors();
        Map<String, String> speciesMapping = factorsConfig.getSpeciesMapping();

        Set<String> requiredFactorTypes = getRequiredFactorTypes(defaultQueryFactorType, defaultFilterFactors);

        MageTabParser mageTabParser = mageTabParserBuilder
                .forExperimentAccession(experimentAccession)
                .withRequiredFactorTypes(requiredFactorTypes)
                .withProcessedRunAccessions(processedRunAccessions)
                .build();

        List<FactorGroup> orderedFactorGroups = extractOrderedFactorGroups(columnHeaders, assayGroups, experimentDesign);


        return baselineExperimentBuilder.forSpecies(getSpecies(mageTabParser))
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(experimentDescription)
                .withDefaultQueryType(factorsConfig.getDefaultQueryFactorType())
                .withDefaultFilterFactors(defaultFilterFactors)
                .withMenuFilterFactorTypes(factorsConfig.getMenuFilterFactorTypes())
                .withOrderedFactorGroups(orderedFactorGroups)
                .withExtraInfo(hasExtraInfoFile)
                .withFactorNamesByType(mageTabParser.getFactorNamesByType())
                .withDisplayName(factorsConfig.getExperimentDisplayName())
                .withSpeciesMapping(speciesMapping)
                .withPubMedIds(pubMedIds)
                .withExperimentDesign(experimentDesign)
                .withAssayGroups(assayGroups)
                .create();

    }

    Set<String> getSpecies(MageTabParser mageTabParser) {

        return mageTabParser.extractSpecies();
    }

    Set<String> getRequiredFactorTypes(String defaultQueryFactorType, Set<Factor> defaultFilterFactors) {
        Set<String> requiredFactorTypes = Sets.newHashSet(defaultQueryFactorType);

        for (Factor defaultFilterFactor : defaultFilterFactors) {
            requiredFactorTypes.add(defaultFilterFactor.getType());
        }
        return requiredFactorTypes;
    }

    List<FactorGroup> extractOrderedFactorGroups(String[] columnHeaders, final AssayGroups assayGroups, ExperimentDesign experimentDesign) {

            List<FactorGroup> factorGroups = Lists.newArrayList();

            for (String groupId : columnHeaders) {
                AssayGroup assayGroup = assayGroups.getAssayGroup(groupId);
                String firstExperimentRun = assayGroup.iterator().next();

                Map<String, String> factors = experimentDesign.getFactors(firstExperimentRun);
                factorGroups.add(FactorSet.create(factors));

            }
            return factorGroups;

        }

    protected abstract BaselineExperimentBuilder createExperimentBuilder();

}
