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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentBuilder;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.caches.magetab.MageTabLoader;
import uk.ac.ebi.atlas.model.caches.magetab.MageTabLoaderBuilder;
import uk.ac.ebi.atlas.model.readers.TsvReader;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Be aware that this is a spring managed singleton object and uses the lookup-method injection to get a new instance of ExperimentBuilder everytime the load method is invoked
//The reason to do so is that Guava CacheBuilder, that is the one only client of this class, is not spring managed.
public abstract class ExperimentMetadataLoader extends CacheLoader<String, Experiment> {

    private static final Logger LOGGER = Logger.getLogger(ExperimentMetadataLoader.class);

    public static final int GENE_ID_COLUMN = 0;

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    private String extraInfoPathTemplate;

    private MageTabLoaderBuilder mageTabLoaderBuilder;
    private TsvReader experimentFactorsTsvReader;

    private ArrayExpressClient arrayExpressClient;

    private TsvReader experimentDataTsvReader;


    @Inject
    public ExperimentMetadataLoader(MageTabLoaderBuilder mageTabLoaderBuilder, TsvReader experimentFactorsTsvReader
            , ArrayExpressClient arrayExpressClient, TsvReader experimentDataTsvReader) {
        this.mageTabLoaderBuilder = mageTabLoaderBuilder;

        this.experimentFactorsTsvReader = experimentFactorsTsvReader;
        this.arrayExpressClient = arrayExpressClient;
        this.experimentDataTsvReader = experimentDataTsvReader;
    }

    @Override
    public Experiment load(String experimentAccession) throws ParseException, IOException {

        String defaultQueryFactorType = parseDefaultQueryFactorType(experimentAccession);

        Set<Factor> defaultFilterFactors = parseDefaultFilterFactors(experimentAccession);

        Set<String> requiredFactorTypes = Sets.newHashSet(defaultQueryFactorType);

        for (Factor defaultFilterFactor : defaultFilterFactors) {
            requiredFactorTypes.add(defaultFilterFactor.getType());
        }

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

        return experimentBuilder.forSpecies(mageTabLoader.extractSpecies())
                .withDescription(experimentName)
                .withDefaultQueryType(defaultQueryFactorType)
                .withDefaultFilterFactors(defaultFilterFactors)
                .withExperimentRuns(mageTabLoader.extractProcessedExperimentRuns())
                .withExtraInfo(hasExtraInfoFile)
                .create();

    }

    private Set<Factor> parseDefaultFilterFactors(String experimentAccession) {
        Set<Factor> defaultFilterFactors = new HashSet<>();

        for (String[] line : experimentFactorsTsvReader.readAll(experimentAccession)) {
            if (line.length == 2) {
                defaultFilterFactors.add(new Factor(line[0], line[1]));
            }
        }
        return defaultFilterFactors;
    }

    private String parseDefaultQueryFactorType(String experimentAccession) {

        for (String[] line : experimentFactorsTsvReader.readAll(experimentAccession)) {
            if (line.length == 1) {
                return line[0];
            }
        }

        throw new IllegalStateException("No defaultQueryFactorType found in factors file.");
    }

    private String fetchExperimentName(String experimentAccession) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "Error connecting to ArrayExpress!";
        }
    }

    protected Set<String> extractProcessedRunAccessions(String experimentAccession){

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
