package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.ProteomicsBaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.SpeciesGrouper;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Named
public class BaselineAnalyticsIndexerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineAnalyticsIndexerService.class);

    private final BaselineAnalyticsDocumentStreamFactory streamFactory;
    private final EFOLookupService efoParentsLookupService;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private final ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory;
    private final AnalyticsIndexDAO analyticsIndexDAO;
    private final BaselineConditionsBuilder baselineConditionsBuilder;

    @Inject
    public BaselineAnalyticsIndexerService(BaselineAnalyticsDocumentStreamFactory streamFactory, EFOLookupService efoParentsLookupService, BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory, ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory, AnalyticsIndexDAO analyticsIndexDAO, BaselineConditionsBuilder baselineConditionsBuilder) {
        this.streamFactory = streamFactory;
        this.efoParentsLookupService = efoParentsLookupService;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.proteomicsBaselineAnalyticsInputStreamFactory = proteomicsBaselineAnalyticsInputStreamFactory;
        this.analyticsIndexDAO = analyticsIndexDAO;
        this.baselineConditionsBuilder = baselineConditionsBuilder;
    }

    public int index(BaselineExperiment experiment, Map<String, String> bioentityIdToIdentifierSearch, int batchSize) {
        String experimentAccession = experiment.getAccession();
        ExperimentType experimentType = experiment.getType();
        String defaultQueryFactorType = experiment.getExperimentalFactors().getDefaultQueryFactorType();
        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        ImmutableMap<String, String> ensemblSpeciesGroupedByAssayGroupId = SpeciesGrouper.buildEnsemblSpeciesGroupedByAssayGroupId(experiment);
        ImmutableSetMultimap<String, String> ontologyTermIdsByAssayAccession = expandOntologyTerms(experimentDesign.getAllOntologyTermIdsByAssayAccession());
        ImmutableSetMultimap<String, String> conditionSearchTermsByAssayGroupId = buildAssayGroupIdToConditionsSearchTerms(experiment, ontologyTermIdsByAssayAccession);

        checkArgument(StringUtils.isNotBlank(defaultQueryFactorType));

        LOGGER.info("Start indexing {}", experimentAccession);
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        //TODO: move this to another class
        ObjectInputStream<BaselineAnalytics> inputStream = (experimentType == ExperimentType.PROTEOMICS_BASELINE) ?
                proteomicsBaselineAnalyticsInputStreamFactory.create(experimentAccession) : baselineAnalyticsInputStreamFactory.create(experimentAccession);

        int count =
                indexBaselineExperimentAnalytics(
                        experimentAccession, experimentType, defaultQueryFactorType, conditionSearchTermsByAssayGroupId,
                        ensemblSpeciesGroupedByAssayGroupId, inputStream, bioentityIdToIdentifierSearch, batchSize);

        stopWatch.stop();
        LOGGER.info("Done indexing {}, indexed {} documents in {} seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds());

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

    private ImmutableSetMultimap<String, String> buildAssayGroupIdToConditionsSearchTerms(BaselineExperiment experiment, SetMultimap<String, String> AssayIdToOntologyAccessions) {
        Collection<Condition> conditions = baselineConditionsBuilder.buildProperties(experiment, AssayIdToOntologyAccessions);
        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (Condition condition : conditions) {
            builder.putAll(condition.getAssayGroupId(), condition.getValues());
        }

        return builder.build();
    }

    private int indexBaselineExperimentAnalytics(String experimentAccession, ExperimentType experimentType,
                                                String defaultQueryFactorType,
                                                SetMultimap<String, String> conditionSearchTermsByAssayGroupId,
                                                ImmutableMap<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                                ObjectInputStream<BaselineAnalytics> inputStream,
                                                Map<String, String> bioentityIdToIdentifierSearch,
                                                int batchSize) {

        try (ObjectInputStream<BaselineAnalytics> closeableInputStream = inputStream) {

            IterableObjectInputStream<BaselineAnalytics> iterableInputStream = new IterableObjectInputStream<>(closeableInputStream);

            BaselineAnalyticsDocumentStream analyticsDocuments =
                    streamFactory.create(
                            experimentAccession, experimentType, ensemblSpeciesGroupedByAssayGroupId,
                            defaultQueryFactorType, iterableInputStream, conditionSearchTermsByAssayGroupId,
                            bioentityIdToIdentifierSearch);

            return analyticsIndexDAO.addDocuments(analyticsDocuments, batchSize);

        } catch (IOException e) {
            throw new AnalyticsIndexerServiceException(e);
        }
    }

    private class AnalyticsIndexerServiceException extends RuntimeException {
        AnalyticsIndexerServiceException(Exception e) {
            super(e);
        }
    }

}