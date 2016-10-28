package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Named //rename BaselineExperimentDataPointStreamFactory
@Deprecated
public class BaselineAnalyticsIndexerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineAnalyticsIndexerService.class);

    private final EFOLookupService efoParentsLookupService;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private final AnalyticsIndexDAO analyticsIndexDAO;
    private final BaselineConditionsBuilder baselineConditionsBuilder;

    @Inject
    public BaselineAnalyticsIndexerService(EFOLookupService efoParentsLookupService, BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory, AnalyticsIndexDAO analyticsIndexDAO, BaselineConditionsBuilder baselineConditionsBuilder) {
        this.efoParentsLookupService = efoParentsLookupService;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.analyticsIndexDAO = analyticsIndexDAO;
        this.baselineConditionsBuilder = baselineConditionsBuilder;
    }

    public int index(BaselineExperiment experiment, Map<String, String> bioentityIdToIdentifierSearch, int batchSize) {

        ImmutableSetMultimap<String, String> ontologyTermIdsByAssayAccession = efoParentsLookupService
                .expandOntologyTerms(experiment.getExperimentDesign().getAllOntologyTermIdsByAssayAccession());
        ImmutableSetMultimap<String, String> conditionSearchTermsByAssayGroupId = buildAssayGroupIdToConditionsSearchTerms(experiment, ontologyTermIdsByAssayAccession);

        LOGGER.info("Start indexing {}", experiment.getAccession());
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();



        int count =
                indexBaselineExperimentAnalytics( experiment,
                        conditionSearchTermsByAssayGroupId,
                        baselineAnalyticsInputStreamFactory.create(experiment.getAccession(), experiment.getType()),
                        bioentityIdToIdentifierSearch, batchSize);

        stopWatch.stop();
        LOGGER.info("Done indexing {}, indexed {} documents in {} seconds", experiment.getAccession(), count, stopWatch.getTotalTimeSeconds());

        return count;
    }

    private ImmutableSetMultimap<String, String> buildAssayGroupIdToConditionsSearchTerms(BaselineExperiment experiment, SetMultimap<String, String> AssayIdToOntologyAccessions) {
        Collection<Condition> conditions = baselineConditionsBuilder.buildProperties(experiment, AssayIdToOntologyAccessions);
        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (Condition condition : conditions) {
            builder.putAll(condition.getAssayGroupId(), condition.getValues());
        }

        return builder.build();
    }

    private int indexBaselineExperimentAnalytics(BaselineExperiment experiment,
                                                 SetMultimap<String, String> conditionSearchTermsByAssayGroupId,
                                                 ObjectInputStream<BaselineAnalytics> inputStream,
                                                 Map<String, String> bioentityIdToIdentifierSearch,
                                                 int batchSize) {

        try (ObjectInputStream<BaselineAnalytics> closeableInputStream = inputStream) {
            return analyticsIndexDAO.addDocuments(
                    new BaselineAnalyticsDocumentStream(
                            experiment, new IterableObjectInputStream<>(closeableInputStream),
                            conditionSearchTermsByAssayGroupId, bioentityIdToIdentifierSearch),
                    batchSize);

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