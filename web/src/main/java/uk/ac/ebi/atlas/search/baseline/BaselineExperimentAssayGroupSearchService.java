package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.solr.query.conditions.BaselineConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("request")
//TODO: merge this class with BaselineExperimentProfileSearchService, by adding the ability to search conditions to BaselineExperimentProfileSearchService
public class BaselineExperimentAssayGroupSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentAssayGroupSearchService.class);

    private final ExperimentTrader experimentTrader;
    private BaselineExperimentAssayGroupsDao baselineExperimentAssayGroupsDao;
    private BaselineConditionsSearchService baselineConditionsSearchService;

    @Inject
    public BaselineExperimentAssayGroupSearchService(BaselineExperimentAssayGroupsDao baselineExperimentAssayGroupsDao, BaselineConditionsSearchService baselineConditionsSearchService, ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
        this.baselineExperimentAssayGroupsDao = baselineExperimentAssayGroupsDao;
        this.baselineConditionsSearchService = baselineConditionsSearchService;
    }

    boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    public SortedSet<BaselineExperimentAssayGroup> queryAnySpecies(Set<String> geneIds) {
        LOGGER.info("<query> geneIds={}", Joiner.on(", ").join(geneIds));
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        String conditionString = "";
        String speciesString = "";

        ImmutableSet<IndexedAssayGroup> indexedAssayGroups = fetchAssayGroupsForCondition(conditionString);

        SetMultimap<String, String> assayGroupsWithExpressionByExperiment =
                baselineExperimentAssayGroupsDao.fetchExperimentAssayGroupsWithNonSpecificExpression(indexedAssayGroups, geneIds);

        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups =
                searchedForConditionButGotNoResults(conditionString, indexedAssayGroups)
                        ? new TreeSet<BaselineExperimentAssayGroup>()
                        : buildResults(assayGroupsWithExpressionByExperiment, !StringUtils.isBlank(conditionString), speciesString);

        stopWatch.stop();
        LOGGER.info("<query> {} results, took {} seconds", baselineExperimentAssayGroups.size(), stopWatch.getTotalTimeSeconds());

        return baselineExperimentAssayGroups;
    }

    private boolean searchedForConditionButGotNoResults(String condition, ImmutableSet<IndexedAssayGroup> indexedAssayGroups) {
        return (!StringUtils.isBlank(condition) && indexedAssayGroups.isEmpty());
    }

    public SortedSet<BaselineExperimentAssayGroup> query(String geneQuery, String condition, String species, Set<String> geneIds) {
        LOGGER.info("<query> geneQuery={}, condition={}", geneQuery, condition);
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        ImmutableSet<IndexedAssayGroup> indexedAssayGroups = fetchAssayGroupsForCondition(condition);

        SetMultimap<String, String> assayGroupsWithExpressionByExperiment = baselineExperimentAssayGroupsDao.fetchExperimentAssayGroupsWithNonSpecificExpression(indexedAssayGroups, geneIds);

        boolean conditionSearch = !indexedAssayGroups.isEmpty();

        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = Sets.newTreeSet();
        if (conditionSearch || StringUtils.isNotEmpty(geneQuery) && StringUtils.isEmpty(condition)) {
            baselineExperimentAssayGroups = buildResults(assayGroupsWithExpressionByExperiment, conditionSearch, species);
        }

        stopWatch.stop();
        LOGGER.info("<query> {} results, took {} seconds", baselineExperimentAssayGroups.size(), stopWatch.getTotalTimeSeconds());

        return baselineExperimentAssayGroups;
    }

    SortedSet<BaselineExperimentAssayGroup> buildResults(SetMultimap<String, String> assayGroupsWithExpressionByExperiment, boolean conditionSearch, String searchSpecies) {
        SortedSet<BaselineExperimentAssayGroup> results = Sets.newTreeSet();

        for (Map.Entry<String, Collection<String>> exprAssayGroups : assayGroupsWithExpressionByExperiment.asMap().entrySet()) {

            String experimentAccession = exprAssayGroups.getKey();
            Collection<String> assayGroupIds = exprAssayGroups.getValue();

            BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

            Multimap<FactorGroup, String> assayGroupIdsByFilterFactors = experiment.getExperimentalFactors().getAssayGroupIdsGroupedByNonDefaultFactors(assayGroupIds);

            for (Map.Entry<FactorGroup, Collection<String>> filterFactorAssayGroupIds : assayGroupIdsByFilterFactors.asMap().entrySet()) {
                FactorGroup filterFactor = filterFactorAssayGroupIds.getKey();
                Collection<String> assayGroupIdsForFilterFactor = filterFactorAssayGroupIds.getValue();

                String experimentSpecies = experiment.getSpecies();

                if (StringUtils.isBlank(searchSpecies) || Species.sameSpecies(experimentSpecies, searchSpecies)) {
                    BaselineExperimentAssayGroup result = new BaselineExperimentAssayGroup(experiment.getAccession(), experiment.getDisplayName(),
                            experimentSpecies, experiment.getExperimentalFactors().getDefaultQueryFactorType(), experiment.isTissueExperiment());
                    result.setFilterFactors(filterFactor);
                    result.setSerializedFilterFactors(filterFactor);
                    if (conditionSearch) {
                        result.setAssayGroupsWithCondition(ImmutableSet.copyOf(assayGroupIdsForFilterFactor), experiment);
                    }
                    results.add(result);
                }
            }

        }
        return results;
    }

    private ImmutableSet<IndexedAssayGroup> fetchAssayGroupsForCondition(String condition) {
        if (StringUtils.isBlank(condition)) {
            return ImmutableSet.of();
        }

        SetMultimap<String, String> assayGroupsPerExperiment = baselineConditionsSearchService.findAssayGroupsPerExperiment(condition);

        ImmutableSet<IndexedAssayGroup> indexedAssayGroups = createSetOfIndexedAssayGroups(assayGroupsPerExperiment);

        return indexedAssayGroups;
    }

    static ImmutableSet<IndexedAssayGroup> createSetOfIndexedAssayGroups(SetMultimap<String, String> assayGroupsPerExperiment) {
        ImmutableSet.Builder<IndexedAssayGroup> builder = ImmutableSet.builder();

        for (Map.Entry<String, String> entry : assayGroupsPerExperiment.entries()) {
            builder.add(new IndexedAssayGroup(entry.getKey(), entry.getValue()));
        }

        return builder.build();
    }

}
