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

package uk.ac.ebi.atlas.experimentimport.analytics.index;

import com.google.common.collect.ImmutableSetMultimap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.experimentimport.EFOParentsLookupService;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.*;

@Named
@Scope("singleton")
public class ExperimentIndexerService {

    private static final Logger LOGGER = Logger.getLogger(ExperimentIndexerService.class);

    private final AnalyticsDocumentStreamFactory streamFactory;
    private final EFOParentsLookupService efoParentsLookupService;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private final AnalyticsIndexer analyticsIndexer;
    private final ExperimentTrader experimentTrader;

    @Inject
    public ExperimentIndexerService(AnalyticsDocumentStreamFactory streamFactory, EFOParentsLookupService efoParentsLookupService, BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory, AnalyticsIndexer analyticsIndexer, ExperimentTrader experimentTrader) {
        this.streamFactory = streamFactory;
        this.efoParentsLookupService = efoParentsLookupService;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.analyticsIndexer = analyticsIndexer;
        this.experimentTrader = experimentTrader;
    }

    public int indexBaselineExperimentAnalytics(String experimentAccession) {
        checkNotNull(experimentAccession);

        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        ExperimentType experimentType = experiment.getType();
        String defaultQueryFactorType = experiment.getExperimentalFactors().getDefaultQueryFactorType();
        ExperimentDesign experimentDesign = experiment.getExperimentDesign();
        Set<String> species = experiment.getOrganisms();

        checkState(species.size() == 1, "Multiple species experiments not yet supported");
        String ensemblSpecies = Species.shortenSpeciesToFirstTwoWords(species.iterator().next());

        ImmutableSetMultimap<String, String> termIdsByAssayAccessionId = experimentDesign.getAllOntologyTermIdsByAssayAccession();

        return indexRnaSeqBaselineExperimentAnalytics(experimentAccession, experimentType, ensemblSpecies,
                defaultQueryFactorType,
                termIdsByAssayAccessionId);
    }

    public int indexRnaSeqBaselineExperimentAnalytics(String experimentAccession, ExperimentType experimentType, String ensemblSpecies,
                                         String defaultQueryFactorType,
                                         ImmutableSetMultimap<String, String> conditionOntologyIdsByAssayAccessionId) {
        checkNotNull(experimentAccession);
        checkNotNull(experimentType);
        checkArgument(StringUtils.isNotBlank(ensemblSpecies));
        checkArgument(StringUtils.isNotBlank(defaultQueryFactorType));

        ImmutableSetMultimap<String, String> expandedTermsByAssayAccessionId = expandOntologyTerms(conditionOntologyIdsByAssayAccessionId);

        LOGGER.info("Begin indexing analytics for experiment " + experimentAccession);
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        int count;

        try (BaselineAnalyticsInputStream baselineAnalyticsInputStream =
                baselineAnalyticsInputStreamFactory.create(experimentAccession)) {

            IterableObjectInputStream<BaselineAnalytics> inputStream = new IterableObjectInputStream<>(baselineAnalyticsInputStream);

            AnalyticsDocumentStream analyticsDocuments = streamFactory.create(experimentAccession, experimentType, ensemblSpecies,
                    defaultQueryFactorType,
                    inputStream, expandedTermsByAssayAccessionId);

            count = analyticsIndexer.addDocuments(analyticsDocuments);

        } catch (IOException e) {
            throw new ExperimentAnalyticsIndexerServiceException(e);
        }

        stopWatch.stop();
        LOGGER.info(String.format("Done indexing analytics for experiment %s, indexed %,d documents in %s seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds()));
        return count;
    }

    private ImmutableSetMultimap<String, String> expandOntologyTerms(ImmutableSetMultimap<String, String> termIdsByAssayAccession) {

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();
        for (String assayAccession : termIdsByAssayAccession.keys()) {
            Set<String> expandedOntologyTerms = new HashSet<>();

            expandedOntologyTerms.addAll(efoParentsLookupService.getAllParents(termIdsByAssayAccession.get(assayAccession)));
            expandedOntologyTerms.addAll(termIdsByAssayAccession.get(assayAccession));

            builder.putAll(assayAccession, expandedOntologyTerms);
        }

        return builder.build();
    }

    public void deleteExperimentFromIndex(String accession) {
        analyticsIndexer.deleteDocumentsForExperiment(accession);
    }

    private class ExperimentAnalyticsIndexerServiceException extends RuntimeException {
        public ExperimentAnalyticsIndexerServiceException(Exception e) {
            super(e);
        }
    }

}