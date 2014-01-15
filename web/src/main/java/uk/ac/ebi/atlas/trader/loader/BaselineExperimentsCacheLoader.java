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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.velocity.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of ExperimentBuilder every time the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one only client of this class, is not spring managed.
public abstract class BaselineExperimentsCacheLoader extends ExperimentsCacheLoader<BaselineExperiment> {

    private static final int HEADER_LINE_INDEX = 0;
    private final TsvReaderBuilder tsvReaderBuilder;

    private ConfigurationTrader configurationTrader;

    @Inject
    protected BaselineExperimentsCacheLoader(TsvReaderBuilder tsvReaderBuilder,
                                             ConfigurationTrader configurationTrader,
                                             @Value("#{configuration['experiment.magetab.path.template']}")
                                             String experimentDataFilePathTemplate) {

        this.configurationTrader = configurationTrader;
        this.tsvReaderBuilder = tsvReaderBuilder.forTsvFilePathTemplate(experimentDataFilePathTemplate);
    }

    @Override
    protected BaselineExperiment load(ExperimentDTO experimentDTO, String experimentDescription,
                                      boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws ParseException, IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        BaselineExperimentConfiguration factorsConfig = configurationTrader.getFactorsConfiguration(experimentAccession);

        AssayGroups assayGroups = configurationTrader.getExperimentConfiguration(experimentAccession).getAssayGroups();

        String defaultQueryFactorType = factorsConfig.getDefaultQueryFactorType();
        Set<Factor> defaultFilterFactors = factorsConfig.getDefaultFilterFactors();
        Set<String> requiredFactorTypes = getRequiredFactorTypes(defaultQueryFactorType, defaultFilterFactors);
        Map<String, String> factorNamesByType = getFactorNamesByType(experimentDesign, requiredFactorTypes);

        String[] orderedAssayGroupIds = readOrderedAssayGroupIdsFromExpressionLevelsFile(experimentAccession);
        List<FactorGroup> orderedFactorGroups = extractOrderedFactorGroups(orderedAssayGroupIds, assayGroups, experimentDesign);
        Map<String, FactorGroup> orderedFactorGroupsByAssayGroup = extractOrderedFactorGroupsByAssayGroup(orderedAssayGroupIds, assayGroups, experimentDesign);

        ExperimentalFactorsBuilder experimentalFactorsBuilder = createExperimentalFactorsBuilder();

        ExperimentalFactors experimentalFactors = experimentalFactorsBuilder
                .withOrderedFactorGroups(orderedFactorGroups)
                .withOrderedFactorGroupsByAssayGroup(orderedFactorGroupsByAssayGroup)
                .withMenuFilterFactorTypes(factorsConfig.getMenuFilterFactorTypes())
                .withFactorNamesByType(factorNamesByType)
                .create();


        BaselineExperimentBuilder baselineExperimentBuilder = createExperimentBuilder();
        return baselineExperimentBuilder.forSpecies(experimentDTO.getSpecies())
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(experimentDescription)
                .withDefaultQueryType(factorsConfig.getDefaultQueryFactorType())
                .withDefaultFilterFactors(defaultFilterFactors)
                .withExtraInfo(hasExtraInfoFile)
                .withDisplayName(factorsConfig.getExperimentDisplayName())
                .withSpeciesMapping(factorsConfig.getSpeciesMapping())
                .withPubMedIds(experimentDTO.getPubmedIds())
                .withAssayGroups(assayGroups)
                .withExperimentDesign(experimentDesign)
                .withExperimentalFactors(experimentalFactors)
                .create();

    }

    private String[] readOrderedAssayGroupIdsFromExpressionLevelsFile(String experimentAccession) {
        TsvReader experimentDataTsvReader = tsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        String[] experimentRunHeaders = experimentDataTsvReader.readLine(HEADER_LINE_INDEX);

        return ArrayUtils.subarray(experimentRunHeaders, 2, experimentRunHeaders.length);
    }

    Set<String> getRequiredFactorTypes(String defaultQueryFactorType, Set<Factor> defaultFilterFactors) {
        Set<String> requiredFactorTypes = Sets.newHashSet(defaultQueryFactorType);

        for (Factor defaultFilterFactor : defaultFilterFactors) {
            requiredFactorTypes.add(defaultFilterFactor.getType());
        }
        return requiredFactorTypes;
    }

    List<FactorGroup> extractOrderedFactorGroups(String[] orderedAssayGroupIds, final AssayGroups assayGroups, ExperimentDesign experimentDesign) {

        List<FactorGroup> factorGroups = Lists.newArrayList();

        for (String groupId : orderedAssayGroupIds) {
            AssayGroup assayGroup = assayGroups.getAssayGroup(groupId);
            String firstExperimentRun = assayGroup.iterator().next();

            Map<String, String> factors = experimentDesign.getFactorValuesByHeader(firstExperimentRun);
            factorGroups.add(FactorSet.create(factors));

        }
        return factorGroups;

    }

    Map<String, FactorGroup> extractOrderedFactorGroupsByAssayGroup(String[] orderedAssayGroupIds, final AssayGroups assayGroups, ExperimentDesign experimentDesign) {

        Map<String, FactorGroup> factorGroups = Maps.newLinkedHashMap();

        for (String groupId : orderedAssayGroupIds) {
            AssayGroup assayGroup = assayGroups.getAssayGroup(groupId);

            Map<String, String> factors = experimentDesign.getFactorValuesByHeader(assayGroup.getFirstAssayAccession());
            factorGroups.put(groupId, FactorSet.create(factors));

        }
        return factorGroups;

    }

    protected Map<String, String> getFactorNamesByType(ExperimentDesign experimentDesign, Set<String> requiredFactorTypes) {
        Map<String, String> factorNamesByType = Maps.newHashMap();

        SortedSet<String> factorTypes = experimentDesign.getFactorHeaders();
        for (String factorType : factorTypes) {
            String normalizedFactorType = Factor.normalize(factorType);
            if (requiredFactorTypes.contains(normalizedFactorType)) {
                factorNamesByType.put(normalizedFactorType, prettifyFactorType(factorType));
            }
        }
        return factorNamesByType;
    }

    protected String prettifyFactorType(String factorType) {
        StringBuilder result = new StringBuilder();
        String[] split = factorType.replaceAll("_", " ").split(" ");
        for (String token : split) {
            int nbUpperCase = countUpperCaseLetters(token);
            if (nbUpperCase > 1) {
                result.append(token);
            } else {
                token = token.toLowerCase();
                token = StringUtils.capitalizeFirstLetter(token);
                result.append(token);
            }
            result.append(" ");
        }

        return result.toString().trim();
    }

    protected int countUpperCaseLetters(String token) {
        int nbUpperCase = 0;
        for (int i = 0; i < token.length(); i++) {
            if (Character.isUpperCase(token.charAt(i))) {
                nbUpperCase++;
            }
        }
        return nbUpperCase;
    }

    protected abstract BaselineExperimentBuilder createExperimentBuilder();

    protected abstract ExperimentalFactorsBuilder createExperimentalFactorsBuilder();

}
