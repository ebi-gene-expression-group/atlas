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

package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.CacheLoader;
import com.google.common.collect.Sets;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.configuration.ExperimentFactorsConfiguration;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentBuilder;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.caches.magetab.MageTabLoader;
import uk.ac.ebi.atlas.model.caches.magetab.MageTabLoaderBuilder;
import uk.ac.ebi.atlas.model.readers.ExperimentDataTsvReader;
import uk.ac.ebi.atlas.model.readers.TsvReader;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of ExperimentBuilder everytime the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one only client of this class, is not spring managed.
public abstract class ExperimentMetadataLoader extends CacheLoader<String, Experiment> {

    private static final Logger LOGGER = Logger.getLogger(ExperimentMetadataLoader.class);

    public static final int GENE_ID_COLUMN = 0;

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    private String extraInfoPathTemplate;

    private MageTabLoaderBuilder mageTabLoaderBuilder;
    private ExperimentFactorsConfiguration experimentFactorsConfiguration;

    private ArrayExpressClient arrayExpressClient;

    private TsvReader experimentDataTsvReader;


    @Inject
    public ExperimentMetadataLoader(MageTabLoaderBuilder mageTabLoaderBuilder, ExperimentFactorsConfiguration experimentFactorsConfiguration
            , ArrayExpressClient arrayExpressClient, ExperimentDataTsvReader experimentDataTsvReader) {
        this.mageTabLoaderBuilder = mageTabLoaderBuilder;

        this.experimentFactorsConfiguration = experimentFactorsConfiguration;
        this.arrayExpressClient = arrayExpressClient;
        this.experimentDataTsvReader = experimentDataTsvReader;
    }

    @Override
    public Experiment load(String experimentAccession) throws ParseException, IOException {

        XMLConfiguration factorsConfig = experimentFactorsConfiguration.forExperiment(experimentAccession);

        String defaultQueryFactorType = parseDefaultQueryFactorType(factorsConfig);

        Set<Factor> defaultFilterFactors = parseDefaultFilterFactors(factorsConfig);

        Set<String> menuFilterFactorTypes = parseMenuFilterFactorTypes(factorsConfig);

        Set<String> requiredFactorTypes = getRequiredFactorTypes(defaultQueryFactorType, defaultFilterFactors);

        String experimentName = fetchExperimentName(experimentAccession);

        String extraInfoFileLocation = MessageFormat.format(extraInfoPathTemplate, experimentAccession);

        boolean hasExtraInfoFile = new File(extraInfoFileLocation).exists();

        Set<String> processedRunAccessions = extractProcessedRunAccessions(experimentAccession);

        ExperimentBuilder experimentBuilder = createExperimentBuilder();

        MageTabLoader mageTabLoader = mageTabLoaderBuilder
                .forExperimentAccession(experimentAccession)
                .withRequiredFactorTypes(requiredFactorTypes)
                .withProcessedRunAccessions(processedRunAccessions)
                .build();

        Collection<ExperimentRun> experimentRuns = mageTabLoader.getProcessedExperimentRuns();

        return experimentBuilder.forSpecies(getSpecies(mageTabLoader, experimentRuns))
                .withDescription(experimentName)
                .withDefaultQueryType(defaultQueryFactorType)
                .withDefaultFilterFactors(defaultFilterFactors)
                .withMenuFilterFactorTypes(menuFilterFactorTypes)
                .withExperimentRuns(experimentRuns)
                .withExtraInfo(hasExtraInfoFile)
                .withFactorNamesByType(mageTabLoader.getFactorNamesByType())
                .create();

    }

    private Set<String> getSpecies(MageTabLoader mageTabLoader, Collection<ExperimentRun> experimentRuns) {

        return mageTabLoader.extractSpecies();
    }

    private Set<String> getRequiredFactorTypes(String defaultQueryFactorType, Set<Factor> defaultFilterFactors) {
        Set<String> requiredFactorTypes = Sets.newHashSet(defaultQueryFactorType);

        for (Factor defaultFilterFactor : defaultFilterFactors) {
            requiredFactorTypes.add(defaultFilterFactor.getType());
        }
        return requiredFactorTypes;
    }

    private Set<Factor> parseDefaultFilterFactors(XMLConfiguration config) {

        Set<Factor> defaultFilterFactors = new HashSet<>();
        List<HierarchicalConfiguration> fields =
                config.configurationsAt("defaultFilterFactors.filterFactor");
        for (HierarchicalConfiguration sub : fields) {
            String factorType = sub.getString("type");
            String factorValue = sub.getString("value");
            defaultFilterFactors.add(new Factor(factorType, factorValue));
        }

        return defaultFilterFactors;
    }

    private String parseDefaultQueryFactorType(XMLConfiguration config) {

        String defaultQueryFactorType = config.getString("defaultQueryFactorType");
        if (defaultQueryFactorType == null || defaultQueryFactorType.trim().length() == 0) {
            throw new IllegalStateException("No defaultQueryFactorType found in factors file.");
        }

        return defaultQueryFactorType;
    }

    private Set<String> parseMenuFilterFactorTypes(XMLConfiguration config) {

        Set<String> results = new HashSet<>();
        List<Object> menuFilterFactorTypes = config.getList("menuFilterFactorTypes");
        for (Object o : menuFilterFactorTypes) {
            String filterFactorType = (String) o;
            if (filterFactorType.trim().length() > 0) {
                results.add(filterFactorType);
            }
        }

        return results;
    }

    private String fetchExperimentName(String experimentAccession) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "Error connecting to ArrayExpress!";
        }
    }

    protected Set<String> extractProcessedRunAccessions(String experimentAccession) {

        String[] experimentRunHeaders = experimentDataTsvReader.readLine(experimentAccession, 0);

        List<String> columnHeaders = Arrays.asList(ArrayUtils.remove(experimentRunHeaders, GENE_ID_COLUMN));

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

    protected abstract ExperimentBuilder createExperimentBuilder();


}
