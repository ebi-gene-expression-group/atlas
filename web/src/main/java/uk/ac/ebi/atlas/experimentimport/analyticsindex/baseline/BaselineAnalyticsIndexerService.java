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

package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.EFOParentsLookupService;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.ProteomicsBaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDao;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.SpeciesGrouper;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("singleton")
public class BaselineAnalyticsIndexerService {

    private static final Logger LOGGER = Logger.getLogger(BaselineAnalyticsIndexerService.class);

    private final BaselineAnalyticsDocumentStreamFactory streamFactory;
    private final EFOParentsLookupService efoParentsLookupService;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private final ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory;
    private final AnalyticsIndexDao analyticsIndexDao;
    private final BaselineConditionsBuilder baselineConditionsBuilder;

    @Inject
    public BaselineAnalyticsIndexerService(BaselineAnalyticsDocumentStreamFactory streamFactory, EFOParentsLookupService efoParentsLookupService, BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory, ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory, AnalyticsIndexDao analyticsIndexDao,  BaselineConditionsBuilder baselineConditionsBuilder) {
        this.streamFactory = streamFactory;
        this.efoParentsLookupService = efoParentsLookupService;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.proteomicsBaselineAnalyticsInputStreamFactory = proteomicsBaselineAnalyticsInputStreamFactory;
        this.analyticsIndexDao = analyticsIndexDao;
        this.baselineConditionsBuilder = baselineConditionsBuilder;
    }

    public int index(BaselineExperiment experiment, int batchSize) {
        String experimentAccession = experiment.getAccession();

        LOGGER.info("Preparing " + experiment);

        ExperimentType experimentType = experiment.getType();

        String defaultQueryFactorType = experiment.getExperimentalFactors().getDefaultQueryFactorType();
        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        ImmutableMap<String, String> ensemblSpeciesGroupedByAssayGroupId = SpeciesGrouper.buildEnsemblSpeciesGroupedByAssayGroupId(experiment);

        ImmutableSetMultimap<String, String> ontologyTermIdsByAssayAccession = expandOntologyTerms(experimentDesign.getAllOntologyTermIdsByAssayAccession());

        ImmutableSetMultimap<String, String> conditionSearchTermsByAssayGroupId = buildConditionSearchTermsByAssayGroupId(experiment, ontologyTermIdsByAssayAccession);

        checkArgument(StringUtils.isNotBlank(defaultQueryFactorType));

        LOGGER.info("Start indexing " + experimentAccession);
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        //TODO: move this to another class
        ObjectInputStream<BaselineAnalytics> inputStream = (experimentType == ExperimentType.PROTEOMICS_BASELINE) ?
                proteomicsBaselineAnalyticsInputStreamFactory.create(experimentAccession) : baselineAnalyticsInputStreamFactory.create(experimentAccession);

        int count = indexRnaSeqBaselineExperimentAnalytics(experimentAccession, experimentType,
                defaultQueryFactorType, conditionSearchTermsByAssayGroupId, ensemblSpeciesGroupedByAssayGroupId, inputStream, batchSize);

        stopWatch.stop();
        LOGGER.info(String.format("Done indexing %s, indexed %,d documents in %s seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds()));

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

    ImmutableSetMultimap<String, String> buildConditionSearchTermsByAssayGroupId(BaselineExperiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<Condition> conditions = baselineConditionsBuilder.buildProperties(experiment, ontologyTermIdsByAssayAccession);

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (Condition condition : conditions) {
            builder.putAll(condition.getAssayGroupId(), condition.getValues());
        }

        return builder.build();

    }

    public int indexRnaSeqBaselineExperimentAnalytics(String experimentAccession, ExperimentType experimentType,
                                                      String defaultQueryFactorType,
                                                      SetMultimap<String, String> conditionSearchTermsByAssayGroupId,
                                                      ImmutableMap<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                                      ObjectInputStream<BaselineAnalytics> inputStream,
                                                      int batchSize) {

        try (ObjectInputStream<BaselineAnalytics> closeableInputStream = inputStream) {

            IterableObjectInputStream<BaselineAnalytics> iterableInputStream = new IterableObjectInputStream<>(closeableInputStream);

            BaselineAnalyticsDocumentStream analyticsDocuments = streamFactory.create(experimentAccession, experimentType, ensemblSpeciesGroupedByAssayGroupId,
                    defaultQueryFactorType,
                    iterableInputStream, conditionSearchTermsByAssayGroupId);

            return analyticsIndexDao.addDocuments(analyticsDocuments, batchSize);

        } catch (IOException e) {
            throw new AnalyticsIndexerServiceException(e);
        }
    }

    private class AnalyticsIndexerServiceException extends RuntimeException {
        public AnalyticsIndexerServiceException(Exception e) {
            super(e);
        }
    }

}