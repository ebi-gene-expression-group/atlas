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

package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.EFOParentsLookupService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.SpeciesGrouper;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("singleton")
public class MicroArrayDiffAnalyticsIndexerService {
    private static final Logger LOGGER = Logger.getLogger(MicroArrayDiffAnalyticsIndexerService.class);

    private final EFOParentsLookupService efoParentsLookupService;
    private final DifferentialConditionsBuilder diffConditionsBuilder;
    private final MicroArrayDiffAnalyticsDocumentStreamIndexer microArrayDiffAnalyticsDocumentStreamIndexer;

    @Inject
    public MicroArrayDiffAnalyticsIndexerService(EFOParentsLookupService efoParentsLookupService, DifferentialConditionsBuilder diffConditionsBuilder, MicroArrayDiffAnalyticsDocumentStreamIndexer microArrayDiffAnalyticsDocumentStreamIndexer) {
        this.efoParentsLookupService = efoParentsLookupService;
        this.diffConditionsBuilder = diffConditionsBuilder;
        this.microArrayDiffAnalyticsDocumentStreamIndexer = microArrayDiffAnalyticsDocumentStreamIndexer;
    }

    public int index(MicroarrayExperiment experiment, int batchSize) {
        String experimentAccession = experiment.getAccession();

        LOGGER.info("Preparing " + experimentAccession);

        ExperimentType experimentType = experiment.getType();

        SortedSet<String> arrayDesignAccessions = experiment.getArrayDesignAccessions();

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        ImmutableMap<String, String> ensemblSpeciesGroupedByContrastId = SpeciesGrouper.buildEnsemblSpeciesGroupedByContrastId(experiment);

        ImmutableSetMultimap<String, String> ontologyTermIdsByAssayAccession = expandOntologyTerms(experimentDesign.getAllOntologyTermIdsByAssayAccession());

        ImmutableSetMultimap<String, String> conditionSearchTermsByContrastId = buildConditionSearchTermsByAssayGroupId(experiment, ontologyTermIdsByAssayAccession);

        Set<String> factors = experimentDesign.getFactorHeaders();

        Map<String, Integer> numReplicatesByContrastId = buildNumReplicatesByContrastId(experiment);

        return  microArrayDiffAnalyticsDocumentStreamIndexer.index(experimentAccession, arrayDesignAccessions, experimentType, factors,
                conditionSearchTermsByContrastId, ensemblSpeciesGroupedByContrastId, numReplicatesByContrastId, batchSize);
    }

    private Map<String, Integer> buildNumReplicatesByContrastId(DifferentialExperiment experiment) {
        ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();

        for (Contrast contrast : experiment.getContrasts()) {
            int numReplicates = Math.min(contrast.getReferenceAssayGroup().getReplicates(), contrast.getTestAssayGroup().getReplicates());
            builder.put(contrast.getId(), numReplicates);
        }

        return builder.build();
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

    ImmutableSetMultimap<String, String> buildConditionSearchTermsByAssayGroupId(DifferentialExperiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = diffConditionsBuilder.buildProperties(experiment, ontologyTermIdsByAssayAccession);

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (DifferentialCondition condition : conditions) {
            builder.putAll(condition.getContrastId(), condition.getValues());
        }

        return builder.build();

    }

}